package com.smeem.output.web.scrap.openapi;

import com.smeem.common.exception.SmeemException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringEscapeUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static com.smeem.common.exception.ExceptionCode.SERVICE_AVAILABLE;
import static org.apache.commons.lang3.StringUtils.*;

@Component
@RequiredArgsConstructor
public class OpenGraph {

    private final HtmlCleaner htmlCleaner;

    private final Set<String> pageNamespaces = new HashSet<>();
    private final Map<String, List<MetaElement>> metaAttributes = new Hashtable<>();

    public final static String[] REQUIRED_META = new String[]{"title", "type", "image", "url"};
    private final static Hashtable<String, String[]> BASE_TYPES = new Hashtable<>();
    private static final String COLON = ":";
    private static final String HEAD_CLOSE_TAG = "</head>";
    private static final String FAKE_BODY = "<body></body></html>";
    private static final Charset CHARSET_UTF_8 = StandardCharsets.UTF_8;
    private static final String PREFIX = "prefix";
    private static final String PATTERN_REGEX = "(([A-Za-z0-9_]+):\\\\s+(http:\\\\/\\\\/ogp.me\\\\/ns(\\\\/\\\\w+)*#))\\\\s*";
    private static final String OPEN_GRAPH_NAMESPACE_PREFIX = "og";
    private static final String META = "meta";
    private static final String PROPERTY = "property";
    private static final String NAME = "name";
    private static final String CONTENT = "content";
    private static final String TYPE = "type";

    static {
        BASE_TYPES.put("activity", new String[]{"activity", "sport"});
        BASE_TYPES.put("business", new String[]{"bar", "company", "cafe", "hotel", "restaurant"});
        BASE_TYPES.put("group", new String[]{"cause", "sports_league", "sports_team"});
        BASE_TYPES.put("organization", new String[]{"band", "government", "non_profit", "school", "university"});
        BASE_TYPES.put("person", new String[]{"actor", "athlete", "author", "director", "musician", "politician", "profile", "public_figure"});
        BASE_TYPES.put("place", new String[]{"city", "country", "landmark", "state_province"});
        BASE_TYPES.put("product", new String[]{"album", "book", "drink", "food", "game", "movie", "product", "song", "tv_show"});
        BASE_TYPES.put("website", new String[]{"blog", "website", "article"});
    }

    public void loading(@NonNull String url, boolean ignoreSpecErrors) throws Exception {
        pageNamespaces.clear();
        metaAttributes.clear();

        TagNode pageData = getTagNode(url);
        TagNode headElement = pageData.findElementByName("head", true);
        boolean hasOGSpec = false;

        if (headElement.hasAttribute(PREFIX)) {
            Matcher matcher = Pattern.compile(PATTERN_REGEX).matcher(headElement.getAttributeByName(PREFIX));

            while (matcher.find()) {
                String prefix = matcher.group(2);
                pageNamespaces.add(prefix);

                if (prefix.equals(OPEN_GRAPH_NAMESPACE_PREFIX))
                    hasOGSpec = true;
            }
        }

        if (!hasOGSpec) {
            pageNamespaces.add(OPEN_GRAPH_NAMESPACE_PREFIX);
        }

        TagNode[] metaData = pageData.getElementsByName(META, true);
        setProperties(metaData);

        if (!ignoreSpecErrors) {
            for (String req : REQUIRED_META) {
                if (!metaAttributes.containsKey(req)) {
                    throw new SmeemException(SERVICE_AVAILABLE, "Does not conform to Open Graph protocol");
                }
            }
        }

        String currentType = getContent(TYPE);
        if (currentType != null) {
            for (String namespace : pageNamespaces) {
                if (currentType.startsWith(namespace + COLON)) {
                    currentType = currentType.replaceFirst(namespace + COLON, EMPTY);
                    break;
                }
            }
        }

        for (String base : BASE_TYPES.keySet()) {
            String[] baseList = BASE_TYPES.get(base);
            boolean finished = false;

            for (String expandedType : baseList) {
                if (expandedType.equals(currentType)) {
                    finished = true;
                    break;
                }
            }

            if (finished) {
                break;
            }
        }
    }

    private void setProperties(TagNode[] metaData) {
        for (TagNode metaElement : metaData) {
            String target = getTarget(metaElement);

            for (String namespace : pageNamespaces) {
                String prefix = namespace + COLON;
                String targetAttribute = metaElement.getAttributeByName(target);

                if (target == null || !targetAttribute.startsWith(prefix)) {
                    continue;
                }

                setProperty(namespace, targetAttribute, metaElement.getAttributeByName(CONTENT));
                break;
            }
        }
    }

    private String getTarget(TagNode metaElement) {
        if (metaElement.hasAttribute(PROPERTY)) {
            return PROPERTY;
        } else if (metaElement.hasAttribute(NAME)) {
            return NAME;
        }
        return null;
    }

    private TagNode getTagNode(String url) throws Exception {
        URL originUrl = new URL(url);
        URLConnection siteConnection = originUrl.openConnection();
        InputStreamReader inputStreamReader = new InputStreamReader(siteConnection.getInputStream(), CHARSET_UTF_8);
        BufferedReader br = new BufferedReader(inputStreamReader);
        String line;
        StringBuilder headContents = new StringBuilder();

        while ((line = br.readLine()) != null) {
            if (line.contains(HEAD_CLOSE_TAG)) {
                line = line.substring(0, line.indexOf(HEAD_CLOSE_TAG) + HEAD_CLOSE_TAG.length());
                line = line.concat(FAKE_BODY);
                headContents.append(line).append(CR + LF);
                break;
            }
            headContents.append(line).append(CR + LF);
        }

        return htmlCleaner.clean(headContents.toString());
    }

    private void setProperty(
            @NonNull String namespace,
            @NonNull String property,
            @NonNull String content
    ) {
        String normalizedProperty = property.replace(namespace + COLON, EMPTY);
        MetaElement element = MetaElement.builder()
                .namespace(namespace)
                .property(normalizedProperty)
                .content(content)
                .build();

        pageNamespaces.add(namespace);
        metaAttributes.computeIfAbsent(normalizedProperty, key -> new ArrayList<>()).add(element);
    }

    public String getContent(String property) {
        return Optional.ofNullable(metaAttributes.get(property))
                .map(Collection::stream)
                .flatMap(Stream::findFirst)
                .map(MetaElement::content)
                .map(StringEscapeUtils::unescapeHtml4)
                .orElse(null);
    }
}
