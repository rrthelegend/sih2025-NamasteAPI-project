package com.app.namasteapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AuthService {
    @Value("${icd11.client.id}")
    private String clientId;

    @Value("${icd11.client.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();
    private String token;
    private long expiryTime;

    public String getAccessToken() {
        if (token == null || System.currentTimeMillis() > expiryTime) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "client_credentials");
            body.add("client_id", clientId);
            body.add("client_secret", clientSecret);
            body.add("scope", "icdapi_access");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

            Map<String, Object> response = restTemplate.postForObject(
                    "https://icdaccessmanagement.who.int/connect/token",
                    request,
                    Map.class
            );

            token = (String) response.get("access_token");
            Integer expiresIn = (Integer) response.get("expires_in");
            expiryTime = System.currentTimeMillis() + (expiresIn - 60) * 1000;
        }
        return token;
    }
}
