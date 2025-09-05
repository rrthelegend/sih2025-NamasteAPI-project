package com.app.namasteapi.repository;

import com.app.namasteapi.model.icd11Terms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface icd11Repository extends JpaRepository<icd11Terms, String> {
    Optional<icd11Terms> findByIcdCodeIgnoreCase(String id);
    Optional<icd11Terms> findByIcdTitleIgnoreCase(String icdTitle);
}
