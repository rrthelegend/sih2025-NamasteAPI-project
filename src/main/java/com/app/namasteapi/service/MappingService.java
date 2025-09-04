package com.app.namasteapi.service;

import com.app.namasteapi.model.Icd11Terms;
import com.app.namasteapi.model.MappingResult;
import com.app.namasteapi.model.NamasteTerms;
import com.app.namasteapi.repository.Icd11Repository;
import com.app.namasteapi.repository.NamasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MappingService {
    @Autowired
    NamasteRepository namasteRepository;

    @Autowired
    Icd11Repository icd11Repository;

    public MappingResult getMapping(String diseaseName) {
        NamasteTerms namaste = namasteRepository.findByNamcTermIgnoreCase(diseaseName)
                .orElseThrow(() -> new RuntimeException("Not found in NAMASTE"));


        Icd11Terms icd = Icd11Service.searchTerm(namaste.getNAMC_NAME());

        return new MappingResult(namaste, icd.getId(), icd.getTitle(), icd.getDefinition());
    }

}
