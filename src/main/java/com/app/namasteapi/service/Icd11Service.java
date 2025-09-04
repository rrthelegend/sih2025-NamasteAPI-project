package com.app.namasteapi.service;

import com.app.namasteapi.model.Icd11Terms;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class Icd11Service {

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String ICD11_API = "https://id.who.int/icd/release/11/mms/search?q=";

    public static Icd11Terms searchTerm(String query) {
        String url = ICD11_API + query;

        // Call ICD-11 API
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null || !response.containsKey("destinationEntities")) {
            return new Icd11Terms("N/A", "Not Found", "No definition available");
        }

        // Extract entities from response
        List<Map<String, Object>> entities = (List<Map<String, Object>>) response.get("destinationEntities");

        if (entities == null || entities.isEmpty()) {
            return new Icd11Terms("N/A", "Not Found", "No definition available");
        }

        // Take the first result
        Map<String, Object> first = entities.get(0);

        String id = (String) first.get("id");
        String title = (String) first.get("title");
        String definition = (String) first.getOrDefault("definition", "No definition available");

        return new Icd11Terms(id, title, definition);
    }
}
