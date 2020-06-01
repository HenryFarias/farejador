package com.example.farejador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.farejador.models.Ape;
import com.example.farejador.service.AdvertisementService;
import com.example.farejador.service.ApeService;

@RestController
@RequestMapping(path = "/")
public class ApeController {

    private final AdvertisementService advertisementService;
    private final ApeService apeService;

    @Autowired
    public ApeController(AdvertisementService advertisementService, ApeService apeService) {
        this.advertisementService = advertisementService;
        this.apeService = apeService;
    }

    @PostMapping
    public void scraping() throws Exception {
        advertisementService.executeScraping();
    }

    @GetMapping
    public List<Ape> list(@RequestParam(required = false) boolean visualized) {
        return apeService.findAllByVisualized(visualized);
    }

    @GetMapping(value = "/favorite")
    public List<Ape> favorites() {
        return apeService.findAllFavorites();
    }
}