package com.app.namasteapi.service;

import com.app.namasteapi.model.icd11Terms;
import com.app.namasteapi.model.mappingResult;
import com.app.namasteapi.model.namasteTerms;
import com.app.namasteapi.repository.icd11Repository;
import com.app.namasteapi.repository.namasteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class mappingService {

    private final namasteRepository namasteRepository;
    private final icd11Repository icd11Repository;

    public mappingService(namasteRepository namasteRepository, icd11Repository icd11Repository) {
        this.namasteRepository = namasteRepository;
        this.icd11Repository = icd11Repository;
    }

    public mappingResult getMapping(String namasteCode, String namasteTerm, String ethnicTerm) {
        namasteTerms record;

        if (namasteCode != null) {
            record = namasteRepository.findById(namasteCode)
                    .orElseThrow(() -> new RuntimeException("Not found in NAMASTE: " + namasteCode));
        } else if (namasteTerm != null) {
            record = namasteRepository.findByNamcTermIgnoreCase(namasteTerm)
                    .orElseThrow(() -> new RuntimeException("Not found in NAMASTE: " + namasteTerm));
        } else {
            record = namasteRepository.findByEthnicTermIgnoreCase(ethnicTerm)
                    .orElseThrow(() -> new RuntimeException("Not found in NAMASTE: " + ethnicTerm));
        }

        Optional<icd11Terms> icd = tryIcdMatch(record);

        if (icd.isPresent()) {
            return new mappingResult(record, icd.get().getIcdCode(), icd.get().getIcdTitle());
        } else {
            return new mappingResult(record, "N/A", "No ICD-11 match found");
        }
    }

    private Optional<icd11Terms> tryIcdMatch(namasteTerms record) {
        if (record.getNamcTerm() != null) {
            Optional<icd11Terms> icd = icd11Repository.findByIcdTitleIgnoreCase(record.getNamcTerm());
            if (icd.isPresent()) return icd;
        }
        if (record.getEthnicTerm() != null) {
            Optional<icd11Terms> icd = icd11Repository.findByIcdTitleIgnoreCase(record.getEthnicTerm());
            if (icd.isPresent()) return icd;
        }
        if (record.getShortDefinition() != null) {
            Optional<icd11Terms> icd = icd11Repository.findByIcdTitleIgnoreCase(record.getShortDefinition());
            if (icd.isPresent()) return icd;
        }
        if (record.getLongDefinition() != null) {
            Optional<icd11Terms> icd = icd11Repository.findByIcdTitleIgnoreCase(record.getLongDefinition());
            if (icd.isPresent()) return icd;
        }
        return Optional.empty();
    }
}
