package com.app.namasteapi.service;

import com.app.namasteapi.model.Icd11Terms;
import com.app.namasteapi.model.MappingResult;
import com.app.namasteapi.model.NamasteTerms;
import com.app.namasteapi.repository.NamasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class MappingService {

    @Autowired
    private NamasteRepository namasteRepository;

    @Autowired
    private Icd11Service icd11Service;

    public MappingResult getMapping(String diseaseCode) throws UnsupportedEncodingException {
        NamasteTerms namaste = namasteRepository.findByNAMC_CODEIgnoreCase(diseaseCode)
                .orElseThrow(() -> new RuntimeException("Not found in NAMASTE"));

        Icd11Terms icd = icd11Service.searchTerm(namaste.getNAMC_NAME());

        return new MappingResult(
                namaste,
                icd.getId(),
                icd.getTitle(),
                icd.getDefinition()
        );
    }
}
