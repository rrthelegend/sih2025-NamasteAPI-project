package com.app.namasteapi.controller;

import com.app.namasteapi.model.Icd11Terms;
import com.app.namasteapi.service.Icd11Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/icd11")
public class icd11Controller {
    @Autowired
    private Icd11Service service;

    @GetMapping("/search")
    public Icd11Terms search(@RequestParam String x) {
        return service.searchTerm(x);
    }
}
