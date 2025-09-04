package com.app.namasteapi.service;

import com.app.namasteapi.model.NamasteTerms;
import com.app.namasteapi.repository.NamasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NamasteService {

    @Autowired
    private NamasteRepository repo;

    public List<NamasteTerms> getAllTerms() {
        return repo.findAll();
    }
}
