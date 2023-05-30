package com.smeme.server.service.auth;

import com.google.gson.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class AppleSignIn {

    @Value("${jwt.APPLE_URL}")
    private String APPLE_URL;


    protected JsonArray getApplePublicKeyList() {
        try {
            URL url = new URL(APPLE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            br.close();

            JsonObject keys = (JsonObject) JsonParser.parseString(result.toString());
            return  (JsonArray) keys.get("keys");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected PublicKey makePublicKey(String accessToken, JsonArray publicKeyList) {
        JsonObject selectedObject = null;

        String[] decodeArray = accessToken.split("\\.");
        String header = new String(Base64.getDecoder().decode(decodeArray[0].substring(7)));

        JsonParser parser = new JsonParser();

        JsonElement kid = ((JsonObject) parser.parse(header)).get("kid");
        JsonElement alg = ((JsonObject) parser.parse(header)).get("alg");

        for (JsonElement publicKey : publicKeyList) {
            JsonObject publicKeyObject = publicKey.getAsJsonObject();
            JsonElement publicKid = publicKeyObject.get("kid");
            JsonElement publicAlg = publicKeyObject.get("alg");

            if (Objects.equals(kid, publicKid) && Objects.equals(alg, publicAlg)) {
                selectedObject = publicKeyObject;
                break;
            }
        }

        if (selectedObject == null) throw new RuntimeException("공개키를 찾을 수 없습니다.");

        // 선택된 공개키 데이터로 공개키 생성
        return getPublicKey(selectedObject);
    }

    private PublicKey getPublicKey(JsonObject object) {
        String nStr = object.get("n").toString();
        String eStr = object.get("e").toString();

        byte[] nBytes = Base64.getUrlDecoder().decode(nStr.substring(1, nStr.length() -1));
        byte[] eBytes = Base64.getUrlDecoder().decode(eStr.substring(1, eStr.length() -1));

        BigInteger nValue = new BigInteger(1, nBytes);
        BigInteger eValue = new BigInteger(1, eBytes);

        try {
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(nValue, eValue);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    protected String getAppleData(String socialAccessToken) {
            JsonArray publicKeyList = getApplePublicKeyList();
            PublicKey publicKey = makePublicKey(socialAccessToken, publicKeyList);

            Claims userInfo = Jwts.parser()
                    .setSigningKey(publicKey)
                    .parseClaimsJws(socialAccessToken.substring(7))
                    .getBody();

            JsonObject userInfoObject = (JsonObject) JsonParser.parseString(new Gson().toJson(userInfo));
            return userInfoObject.get("sub").getAsString();
        }
}
