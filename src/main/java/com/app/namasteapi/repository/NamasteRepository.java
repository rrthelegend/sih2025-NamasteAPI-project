package com.app.namasteapi.repository;

import com.app.namasteapi.model.NamasteTerms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NamasteRepository extends JpaRepository<NamasteTerms, String> {
    Optional<NamasteTerms> findByNamcCodeIgnoreCase(String term);
    List<NamasteTerms> findByNamcNameContainingIgnoreCase(String name);
}
