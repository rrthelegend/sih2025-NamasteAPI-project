package com.app.namasteapi.controller;

import com.app.namasteapi.service.TerminologyService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final TerminologyService terminologyService;

    public AdminController(TerminologyService terminologyService) {
        this.terminologyService = terminologyService;
    }

    @GetMapping("/build-artifacts/codesystem")
    public Map<String, Object> buildCodeSystem() {
        return terminologyService.buildCodeSystem();
    }

    @GetMapping("/build-artifacts/conceptmap")
    public Map<String, Object> buildConceptMap() {
        return terminologyService.buildConceptMap();
    }
}
