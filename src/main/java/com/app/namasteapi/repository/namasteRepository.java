package com.app.namasteapi.repository;

import com.app.namasteapi.model.namasteTerms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface namasteRepository extends JpaRepository<namasteTerms, String> {
    Optional<namasteTerms> findByNamcCodeIgnoreCase(String term);
    List<namasteTerms> findByNamcTermContainingIgnoreCase(String name);
    Optional<namasteTerms> findByEthnicTermIgnoreCase(String ethnicTerm);
    Optional<namasteTerms> findByShortDefinitionIgnoreCase(String shortDefinition);
    Optional<namasteTerms> findByLongDefinitionIgnoreCase(String longDefinition);

    Optional<? extends namasteTerms> findByNamcTermIgnoreCase(String namcTerm);
}
