package com.app.namasteapi.controller;

import com.app.namasteapi.model.mappingResult;
import com.app.namasteapi.service.mappingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/terminology")
public class translateController {

    private final mappingService mappingService;

    public translateController(mappingService mappingService) {
        this.mappingService = mappingService;
    }

    @GetMapping("/$translate")
    public Map<String, Object> translate(
            @RequestParam(required = false) String namasteCode,
            @RequestParam(required = false) String namasteTerm,
            @RequestParam(required = false) String ethnicTerm
    ) {
        if (namasteCode == null && namasteTerm == null && ethnicTerm == null) {
            throw new RuntimeException("Must provide either namasteCode, namasteTerm, or ethnicTerm");
        }

        mappingResult result = mappingService.getMapping(namasteCode, namasteTerm, ethnicTerm);

        return Map.of(
                "resourceType", "Parameters",
                "parameter", List.of(
                        Map.of("name", "source", "valueString", result.getNamasteName()),
                        Map.of("name", "target", "valueString", result.getIcd11Id() + " - " + result.getIcd11Title())
                )
        );
    }

    @GetMapping("/codesystem")
    public Map<String, Object> getCodeSystem() {
        return Map.of(
                "resourceType", "CodeSystem",
                "id", "icd11",
                "name", "ICD-11",
                "status", "active",
                "content", "complete",
                "description", "ICD-11 reference terminology"
        );
    }
}
