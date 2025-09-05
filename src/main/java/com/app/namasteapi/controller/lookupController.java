package com.app.namasteapi.controller;

import com.app.namasteapi.model.namasteTerms;
import com.app.namasteapi.repository.namasteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/terminology")
public class lookupController {

    private final namasteRepository namasteRepository;

    public lookupController(namasteRepository namasteRepository) {
        this.namasteRepository = namasteRepository;
    }

    @GetMapping("/lookup")
    public List<namasteTerms> lookup(@RequestParam String query) {
        return namasteRepository.findByNamcTermContainingIgnoreCase(query);
    }
}
