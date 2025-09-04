package com.app.namasteapi.repository;

import com.app.namasteapi.model.Icd11Terms;
import com.app.namasteapi.model.NamasteTerms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Icd11Repository extends JpaRepository<NamasteTerms, String> {
    Optional<Icd11Terms> findByNAMC_CODEIgnoreCase(String term);
}
