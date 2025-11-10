package com.smeem.application.domain.bookmark;

import com.smeem.application.domain.bookmark.model.Bookmark;
import com.smeem.application.domain.bookmark.model.Expression;
import com.smeem.application.domain.member.Member;
import com.smeem.application.port.input.bookmark.BookmarkCommandUseCase;
import com.smeem.application.port.input.bookmark.dto.BookmarkFallbackRequest;
import com.smeem.application.port.input.bookmark.dto.BookmarkModifyResponse;
import com.smeem.application.port.input.bookmark.dto.BookmarkRequest;
import com.smeem.application.port.input.bookmark.dto.BookmarkResponse;
import com.smeem.application.port.input.bookmark.dto.BookmarkUpdateRequest;
import com.smeem.application.port.input.bookmark.dto.ScrapResponse;
import com.smeem.application.port.output.persistence.BookmarkPort;
import com.smeem.application.port.output.persistence.MemberPort;
import com.smeem.application.port.output.web.openai.OpenAiPort;
import com.smeem.application.port.output.web.scrap.ScrapInfo;
import com.smeem.application.port.output.web.scrap.ScrapPort;
import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static com.smeem.application.domain.bookmark.BookmarkConverter.convert;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkCommandService implements BookmarkCommandUseCase {

    private final BookmarkStore bookmarkStore;
    private final BookmarkPort bookmarkPort;
    private final MemberPort memberPort;

    private final ScrapPort scrapPort;
    private final OpenAiPort openAiPort;

    private final static int LIMIT_SCRAPED_COUNT = 10;

    @Override
    public BookmarkResponse bookmark(long memberId, BookmarkRequest request) {
        String scrapedUrl = request.url();
        Member member = memberPort.findById(memberId);
        Bookmark existedBookmark = bookmarkStore.getByScrapedUrl(scrapedUrl);

        if (existedBookmark != null) {
            return create(member, convert(member.getId(), existedBookmark));
        }

        ScrapInfo scrapInfo = scrapPort.scrap(scrapedUrl);
        Expression expression = validate(member) ? openAiPort.promptExpression(scrapInfo.description()) : null;

        if (expression == null) {
            return BookmarkResponse.fallback(scrapInfo, member.getScrapedCountPerDay());
        }

        return create(member, convert(scrapInfo, expression, member.getId()));
    }

    private BookmarkResponse create(Member member, Bookmark bookmark) {
        Bookmark savedBookmark = bookmarkPort.save(bookmark);
        Member updatedMember = memberPort.update(updateScrapedCount(member));
        return BookmarkResponse.from(savedBookmark, updatedMember);
    }

    private boolean validate(Member member) {
        LocalDate today = LocalDate.now();
        int scrapedCount = Optional.ofNullable(member.getScrapedCountPerDay()).orElse(0);
        return !today.equals(member.getLastScrapedDate()) || scrapedCount < LIMIT_SCRAPED_COUNT;
    }

    private Member updateScrapedCount(Member member) {
        LocalDate today = LocalDate.now();

        if (!today.equals(member.getLastScrapedDate())) {
            member.setLastScrapedDate(today);
            member.setScrapedCountPerDay(1);
            return member;
        }

        int scrapedCount = Optional.ofNullable(member.getScrapedCountPerDay()).orElse(0);
        member.setScrapedCountPerDay(scrapedCount + 1);

        return member;
    }

    @Override
    public BookmarkResponse bookmarkFallback(long memberId, BookmarkFallbackRequest request) {
        Member member = memberPort.findById(memberId);
        Bookmark bookmark = convert(request, memberId);
        Bookmark savedBookmark = bookmarkPort.save(bookmark);
        return BookmarkResponse.builder()
                .scrapContent(ScrapResponse.from(savedBookmark))
                .expression(savedBookmark.getExpression())
                .translatedExpression(savedBookmark.getTranslatedExpression())
                .scrapedCountPerDay(LocalDate.now().equals(member.getLastScrapedDate()) ? member.getScrapedCountPerDay() : 0)
                .build();
    }

    @Override
    public void deleteBookmark(long memberId, long bookmarkId) {
        Bookmark bookmark = bookmarkPort.getById(bookmarkId);
        validateBookmarkOwnership(bookmark.getMemberId(), memberId);
        bookmarkPort.deleteByBookmarkId(bookmarkId);
    }

    @Override
    public BookmarkModifyResponse updateBookmark(long memberId, long bookmarkId, BookmarkUpdateRequest request) {
        Bookmark bookmark = bookmarkPort.getById(bookmarkId);
        validateBookmarkOwnership(bookmark.getMemberId(), memberId);
        Bookmark updatedBookmark = bookmarkPort.update(request.toDomain(bookmark));
        return BookmarkModifyResponse.from(updatedBookmark);
    }

    private void validateBookmarkOwnership(long bookmarkWriterId, long memberId) {
        if (bookmarkWriterId != memberId) {
            throw new SmeemException(ExceptionCode.INVALID_MEMBER_AND_BOOKMARK);
        }
    }
}
