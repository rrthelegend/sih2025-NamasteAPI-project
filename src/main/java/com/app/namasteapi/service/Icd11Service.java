package com.app.namasteapi.service;

import com.app.namasteapi.model.Icd11Terms;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Service
public class Icd11Service {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${icd11.client.id}")
    private String clientId;

    @Value("${icd11.client.secret}")
    private String clientSecret;

    private String cachedToken;

    // 🔹 1. Get access token from WHO auth server
    private String getAccessToken() {
        if (cachedToken != null) {
            return cachedToken; // reuse until expiry (for demo)
        }

        String tokenUrl = "https://icdaccessmanagement.who.int/connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "grant_type=client_credentials"
                + "&client_id=" + clientId
                + "&client_secret=" + clientSecret
                + "&scope=icdapi_access";

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        Map<String, Object> response = restTemplate.postForObject(tokenUrl, request, Map.class);

        if (response != null && response.containsKey("access_token")) {
            cachedToken = (String) response.get("access_token");
            return cachedToken;
        } else {
            throw new RuntimeException("Failed to fetch ICD-11 access token");
        }
    }

    // 🔹 2. Search ICD-11 entity by term
    public Icd11Terms searchTerm(String query) throws UnsupportedEncodingException {
        String url = "https://icd.who.int/icd/api/v2/icd/entity/search?q=" + URLEncoder.encode(query, "UTF-8");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + getAccessToken());
        headers.set("API-Version", "v2");
        headers.set("Accept-Language", "en");
        headers.set("Accept", "application/json");

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            return new Icd11Terms("N/A", "Not Found", "No definition available");
        }

        Map<String, Object> body = response.getBody();
        List<Map<String, Object>> entities = (List<Map<String, Object>>) body.get("destinationEntities");

        if (entities == null || entities.isEmpty()) {
            return new Icd11Terms("N/A", "Not Found", "No definition available");
        }

        Map<String, Object> first = entities.get(0);

        String id = (String) first.get("id");

        Map<String, Object> titleObj = (Map<String, Object>) first.get("title");
        String title = titleObj != null ? (String) titleObj.get("@value") : "Unknown";

        Map<String, Object> defObj = (Map<String, Object>) first.get("definition");
        String definition = defObj != null ? (String) defObj.get("@value") : "No definition available";

        return new Icd11Terms(id, title, definition);
    }
}
