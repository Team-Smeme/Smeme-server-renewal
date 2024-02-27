package com.smeem.external.oauth.apple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smeem.common.config.ValueConfig;
import com.smeem.common.exception.AuthException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

import static com.smeem.common.code.failure.AuthFailureCode.FAIL_APPLE_REQUEST;

@RequiredArgsConstructor
@Component
public class AppleService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ValueConfig valueConfig;

    public String getAppleData(final String appleAccessToken) {
        val publicAppleKeys = getApplePublicKeys();
        val decodedAppleKey = decodeAppleAccessToken(appleAccessToken);
        val matchedAppleKey = matchDecodedKeyWithApplePublicKeys(decodedAppleKey, publicAppleKeys);
        try {
            val userInfo = Jwts.parserBuilder()
                    .setSigningKey(generatePublicKey(matchedAppleKey))
                    .build()
                    .parseClaimsJws(appleAccessToken.substring(7))
                    .getBody();
            return userInfo.get("sub", String.class);
        } catch (RuntimeException e) {
            throw new AuthException(FAIL_APPLE_REQUEST);
        }
    }

    private AppleKeyListResponse getApplePublicKeys() {
        try {
            val restClient = RestClient.create();
            return restClient.get()
                    .uri(valueConfig.getAPPLE_URL())
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,
                            (appleRequest, appleResponse) -> {
                                throw new AuthException(FAIL_APPLE_REQUEST);
                            })
                    .body(AppleKeyListResponse.class);
        } catch (Exception e) {
            throw new AuthException(FAIL_APPLE_REQUEST);
        }
    }

    private AppleKey matchDecodedKeyWithApplePublicKeys(final DecodedAppleKey decodedAppleKey, AppleKeyListResponse appleKeyList) {
        return appleKeyList.keys().stream()
                .filter(appleKey -> checkEqualAppleKey(decodedAppleKey, appleKey))
                .findFirst()
                .orElseThrow(() -> new AuthException(FAIL_APPLE_REQUEST));
    }

    private boolean checkEqualAppleKey(final DecodedAppleKey decodedAppleKey, final AppleKey appleKey) {
        return Objects.equals(decodedAppleKey.alg(), appleKey.alg()) &&
                Objects.equals(decodedAppleKey.kid(), appleKey.kid());
    }

    private DecodedAppleKey decodeAppleAccessToken(final String accessToken) {
        try {
            val encoded = accessToken.split("\\.");
            val decoded = new String(Base64.getDecoder().decode(encoded[0].substring(7)));
            val decodedMap = objectMapper.readValue(decoded, Map.class);
            return DecodedAppleKey.of(
                    decodedMap.get("kid").toString(),
                    decodedMap.get("alg").toString());
        } catch (JsonProcessingException e) {
            throw new AuthException(FAIL_APPLE_REQUEST);
        }
    }

    private PublicKey generatePublicKey(final AppleKey key) {
        try {
            val eBytes = Base64.getUrlDecoder().decode(key.e());
            val nBytes = Base64.getUrlDecoder().decode(key.n());
            val publicKeySpec = new RSAPublicKeySpec(
                    new BigInteger(1, nBytes),
                    new BigInteger(1, eBytes));
            val keyFactory = KeyFactory.getInstance(key.kty());
            return keyFactory.generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AuthException(FAIL_APPLE_REQUEST);
        }
    }

}