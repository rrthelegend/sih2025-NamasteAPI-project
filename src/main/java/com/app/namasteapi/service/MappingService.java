package com.app.namasteapi.service;

import com.app.namasteapi.model.Icd11Terms;
import com.app.namasteapi.model.MappingResult;
import com.app.namasteapi.model.NamasteTerms;
import com.app.namasteapi.repository.NamasteRepository;
import org.springframework.stereotype.Service;

@Service
public class MappingService {

    private final NamasteRepository namasteRepository;
    private final Icd11Service icd11Service;

    public MappingService(NamasteRepository namasteRepository, Icd11Service icd11Service) {
        this.namasteRepository = namasteRepository;
        this.icd11Service = icd11Service;
    }

    public MappingResult getMapping(String diseaseName) throws Exception {
        NamasteTerms namaste = namasteRepository.findByNamcCodeIgnoreCase(diseaseName)
                .orElseThrow(() -> new RuntimeException("Not found in NAMASTE"));

        Icd11Terms icd = icd11Service.searchTerm(namaste.getNamcName());

        return new MappingResult(namaste, icd.getId(), icd.getTitle(), icd.getDefinition());
    }
}
