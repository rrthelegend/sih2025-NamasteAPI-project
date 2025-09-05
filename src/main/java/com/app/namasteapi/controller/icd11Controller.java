package com.app.namasteapi.controller;

import com.app.namasteapi.model.icd11Terms;
import com.app.namasteapi.service.icd11Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@RestController
@RequestMapping("/api/icd11")
public class icd11Controller {
    @Autowired
    private icd11Service service;

    public icd11Controller(icd11Service service) {
        this.service = service;
    }

    @GetMapping("/search")
    public Optional<icd11Terms> search(@RequestParam String x) throws UnsupportedEncodingException {
        return service.mapToICD11(x);
    }
}
