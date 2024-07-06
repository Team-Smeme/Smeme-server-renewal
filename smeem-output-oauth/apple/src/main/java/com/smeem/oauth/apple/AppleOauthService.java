package com.smeem.oauth.apple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import com.smeem.oauth.apple.dto.response.AppleKey;
import com.smeem.oauth.apple.dto.response.AppleKeysResponse;
import com.smeem.oauth.apple.dto.response.DecodedAppleKey;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
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

@Service
@RequiredArgsConstructor
public class AppleOauthService {
    private final ObjectMapper objectMapper;

    @Value("${jwt.APPLE_URL}")
    private String APPLE_URL;

    public String getAppleData(String appleAccessToken) {
        val publicAppleKeys = getApplePublicKeys();
        val decodedAppleKey = decodeAppleAccessToken(appleAccessToken);
        val matchedAppleKey = matchDecodedKeyWithApplePublicKeys(decodedAppleKey, publicAppleKeys);
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(generatePublicKey(matchedAppleKey))
                    .build()
                    .parseClaimsJws(appleAccessToken.substring(7))
                    .getBody()
                    .get("sub", String.class);
        } catch (RuntimeException e) {
            throw new SmeemException(ExceptionCode.SERVICE_AVAILABLE);
        }
    }

    private AppleKeysResponse getApplePublicKeys() {
        try {
            val restClient = RestClient.create();
            return restClient.get()
                    .uri(APPLE_URL)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (appleRequest, appleResponse) -> {
                        throw new SmeemException(ExceptionCode.SERVICE_AVAILABLE);
                    })
                    .body(AppleKeysResponse.class);
        } catch (RuntimeException exception) {
            throw new SmeemException(ExceptionCode.SERVICE_AVAILABLE);
        }
    }

    private AppleKey matchDecodedKeyWithApplePublicKeys(
            DecodedAppleKey decodedAppleKey,
            AppleKeysResponse appleKeyList
    ) {
        return appleKeyList.keys().stream()
                .filter(appleKey -> checkEqualAppleKey(decodedAppleKey, appleKey))
                .findFirst()
                .orElseThrow(() -> new SmeemException(ExceptionCode.SERVICE_AVAILABLE));
    }

    private boolean checkEqualAppleKey(final DecodedAppleKey decodedAppleKey, final AppleKey appleKey) {
        return Objects.equals(decodedAppleKey.alg(), appleKey.alg())
                && Objects.equals(decodedAppleKey.kid(), appleKey.kid());
    }

    private DecodedAppleKey decodeAppleAccessToken(final String accessToken) {
        try {
            val encoded = accessToken.split("\\.");
            val decoded = new String(Base64.getDecoder().decode(encoded[0].substring(7)));
            val decodedMap = objectMapper.readValue(decoded, Map.class);
            return DecodedAppleKey.of(
                    decodedMap.get("kid").toString(),
                    decodedMap.get("alg").toString());
        } catch (JsonProcessingException exception) {
            throw new SmeemException(ExceptionCode.SERVICE_AVAILABLE);
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
            throw new SmeemException(ExceptionCode.SERVICE_AVAILABLE);
        }
    }

}
