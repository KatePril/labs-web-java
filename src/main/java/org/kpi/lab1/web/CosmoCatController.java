package org.kpi.lab1.web;

import org.kpi.lab1.featuretoggle.FeatureToggles;
import org.kpi.lab1.featuretoggle.annotation.FeatureToggle;
import org.kpi.lab1.service.CosmoCatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cats")
public class CosmoCatController {

    private final CosmoCatService cosmoCatService;

    public CosmoCatController(CosmoCatService cosmoCatService) {
        this.cosmoCatService = cosmoCatService;
    }

    @GetMapping
    @FeatureToggle(FeatureToggles.COSMO_CATS)
    public ResponseEntity<List<String>> getCats() {
        List<String> cats = cosmoCatService.getCosmoCats();
        return ResponseEntity.ok(cats);
    }
}
