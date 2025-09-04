package com.app.namasteapi.controller;

import com.app.namasteapi.model.NamasteTerms;
import com.app.namasteapi.service.NamasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/namaste")
public class NamasteController {

    @Autowired
    private NamasteService service;

    @GetMapping("/all")
    public List<NamasteTerms> getAll() {
        return service.getAllTerms();
    }
}