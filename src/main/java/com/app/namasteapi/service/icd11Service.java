package com.app.namasteapi.service;

import com.app.namasteapi.model.icd11Terms;
import com.app.namasteapi.model.namasteTerms;
import com.app.namasteapi.repository.icd11Repository;
import com.app.namasteapi.repository.namasteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class icd11Service {

    private final icd11Repository icd11Repository;
    private final namasteRepository namasteRepository;

    public icd11Service(icd11Repository icd11Repository, namasteRepository namasteRepository) {
        this.icd11Repository = icd11Repository;
        this.namasteRepository = namasteRepository;
    }

    public Optional<icd11Terms> mapToICD11(String input) {
        Optional<namasteTerms> namasteMatch =
                namasteRepository.findByNamcCodeIgnoreCase(input)
                        .or(() -> namasteRepository.findByNamcTermIgnoreCase(input))
                        .or(() -> namasteRepository.findByEthnicTermIgnoreCase(input))
                        .or(() -> namasteRepository.findByShortDefinitionIgnoreCase(input))
                        .or(() -> namasteRepository.findByLongDefinitionIgnoreCase(input));

        if (namasteMatch.isEmpty()) {
            return Optional.empty();
        }

        namasteTerms term = namasteMatch.get();

        return icd11Repository.findByIcdTitleIgnoreCase(term.getNamcTerm())
                .or(() -> icd11Repository.findByIcdCodeIgnoreCase(term.getNamcCode()));
    }

    public Iterable<icd11Terms> getCodeSystem() {
        return icd11Repository.findAll();
    }
}
