package com.app.namasteapi.controller;

import com.app.namasteapi.service.terminologyService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class adminController {

    private final terminologyService terminologyService;

    public adminController(terminologyService terminologyService) {
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
