package com.app.namasteapi.controller;

import com.app.namasteapi.model.NamasteTerms;
import com.app.namasteapi.repository.NamasteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/terminology")
public class LookupController {

    private final NamasteRepository namasteRepository;

    public LookupController(NamasteRepository namasteRepository) {
        this.namasteRepository = namasteRepository;
    }

    @GetMapping("/lookup")
    public List<NamasteTerms> lookup(@RequestParam String query) {
        return namasteRepository.findByNamcNameContainingIgnoreCase(query);
    }
}
