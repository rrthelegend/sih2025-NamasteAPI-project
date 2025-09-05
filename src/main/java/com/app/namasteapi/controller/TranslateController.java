package com.app.namasteapi.controller;

import com.app.namasteapi.model.NamasteTerms;
import com.app.namasteapi.repository.NamasteRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/terminology")
public class TranslateController {

    private final NamasteRepository namasteRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public TranslateController(NamasteRepository namasteRepository) {
        this.namasteRepository = namasteRepository;
    }

    @GetMapping("/$translate")
    public Map<String, Object> translate(@RequestParam String namasteCode) {
        NamasteTerms term = namasteRepository.findById(namasteCode)
                .orElseThrow(() -> new RuntimeException("Not found: " + namasteCode));

        String url = "https://id.who.int/icd/release/11/mms?fuzzyMatch=" + term.getNamcName();
        String icdJson = restTemplate.getForObject(url, String.class);

        return Map.of(
                "resourceType", "Parameters",
                "parameter", List.of(
                        Map.of("name", "source", "valueString", term.getNamcName()),
                        Map.of("name", "target", "valueString", icdJson)
                )
        );
    }
}
