package com.app.namasteapi.service;

import com.app.namasteapi.model.namasteTerms;
import com.app.namasteapi.model.icd11Terms;
import com.app.namasteapi.repository.namasteRepository;
import com.app.namasteapi.repository.icd11Repository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class terminologyService {

    private final namasteRepository namasteRepository;
    private final icd11Repository icd11Repository;

    public terminologyService(namasteRepository namasteRepository, icd11Repository icd11Repository) {
        this.namasteRepository = namasteRepository;
        this.icd11Repository = icd11Repository;
    }

    // Build CodeSystem dynamically from Namaste DB
    public Map<String, Object> buildCodeSystem() {
        List<Map<String, Object>> concepts = new ArrayList<>();

        for (namasteTerms term : namasteRepository.findAll()) {
            concepts.add(Map.of(
                    "code", term.getNamcCode(),
                    "display", term.getNamcTerm()
            ));
        }

        return Map.of(
                "resourceType", "CodeSystem",
                "id", "namaste",
                "status", "active",
                "content", "complete",
                "concept", concepts
        );
    }

    // Build ConceptMap dynamically from Namaste → ICD-11 DB
    public Map<String, Object> buildConceptMap() {
        List<Map<String, Object>> elements = new ArrayList<>();

        for (namasteTerms term : namasteRepository.findAll()) {
            Optional<icd11Terms> icdMatch =
                    icd11Repository.findByIcdTitleIgnoreCase(term.getNamcTerm());

            if (icdMatch.isPresent()) {
                elements.add(Map.of(
                        "code", term.getNamcCode(),
                        "display", term.getNamcTerm(),
                        "target", List.of(
                                Map.of(
                                        "code", icdMatch.get().getIcdCode(),
                                        "display", icdMatch.get().getIcdTitle(),
                                        "equivalence", "equivalent"
                                )
                        )
                ));
            }
        }

        return Map.of(
                "resourceType", "ConceptMap",
                "id", "namaste-icd11-map",
                "status", "active",
                "group", List.of(
                        Map.of(
                                "source", "http://example.org/fhir/CodeSystem/namaste",
                                "target", "http://id.who.int/icd/release/11/mms",
                                "element", elements
                        )
                )
        );
    }
}
