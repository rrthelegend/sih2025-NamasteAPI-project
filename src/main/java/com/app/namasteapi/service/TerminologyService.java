package com.app.namasteapi.service;

import com.app.namasteapi.model.NamasteTerms;
import com.app.namasteapi.repository.NamasteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TerminologyService {

    private final NamasteRepository namasteRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    public TerminologyService(NamasteRepository namasteRepository) {
        this.namasteRepository = namasteRepository;
    }

    public Map<String, Object> buildCodeSystem() {
        List<NamasteTerms> terms = namasteRepository.findAll();

        Map<String, Object> codeSystem = new HashMap<>();
        codeSystem.put("resourceType", "CodeSystem");
        codeSystem.put("id", "namaste-codes");
        codeSystem.put("status", "active");
        codeSystem.put("content", "complete");
        codeSystem.put("name", "NAMASTE Terminology");

        List<Map<String, String>> concepts = new ArrayList<>();
        for (NamasteTerms t : terms) {
            Map<String, String> concept = new HashMap<>();
            concept.put("code", t.getNAMC_CODE());
            concept.put("display", t.getNAMC_NAME());
            concepts.add(concept);
        }
        codeSystem.put("concept", concepts);

        return codeSystem;
    }

    public Map<String, Object> buildConceptMap() {
        Map<String, Object> conceptMap = new HashMap<>();
        conceptMap.put("resourceType", "ConceptMap");
        conceptMap.put("id", "namaste-icd11-map");
        conceptMap.put("status", "active");

        conceptMap.put("group", List.of(Map.of(
                "source", "NAMASTE",
                "target", "ICD-11",
                "element", List.of(Map.of(
                        "code", "NAMC001",
                        "target", List.of(Map.of("code", "ICD11-XYZ", "equivalence", "equivalent"))
                ))
        )));
        return conceptMap;
    }
}
