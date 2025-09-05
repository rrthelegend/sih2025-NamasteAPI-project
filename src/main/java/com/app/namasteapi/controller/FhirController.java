package com.app.namasteapi.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/fhir")
public class FhirController {

    @PostMapping("/Bundle")
    public Map<String, Object> uploadBundle(@RequestBody Map<String, Object> bundle) {
        return Map.of(
                "status", "success",
                "message", "FHIR Bundle received",
                "receivedEntries", bundle.get("entry")
        );
    }
}
