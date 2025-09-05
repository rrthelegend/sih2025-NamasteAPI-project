package com.app.namasteapi.service;

import com.app.namasteapi.model.namasteTerms;
import com.app.namasteapi.repository.namasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class namasteService {

    @Autowired
    private namasteRepository repo;

    public List<namasteTerms> getAllTerms() {
        return repo.findAll();
    }
}
