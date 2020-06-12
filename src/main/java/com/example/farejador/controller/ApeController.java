package com.example.farejador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.farejador.Converter;
import com.example.farejador.enums.IntentionEnum;
import com.example.farejador.enums.RegionTypeEnum;
import com.example.farejador.models.Ape;
import com.example.farejador.response.SearchResponse;
import com.example.farejador.service.AdvertisementService;
import com.example.farejador.service.ApeService;

@RestController
@RequestMapping(path = "/")
public class ApeController {

    private final AdvertisementService advertisementService;
    private final ApeService apeService;
    private final Converter converter;

    @Autowired
    public ApeController(AdvertisementService advertisementService, ApeService apeService, Converter converter) {
        this.advertisementService = advertisementService;
        this.apeService = apeService;
        this.converter = converter;
    }

    @PostMapping
    public void scraping(@RequestParam RegionTypeEnum regionType,
                         @RequestParam IntentionEnum intention) throws Exception {
        advertisementService.findApes(regionType, intention);
    }

    @GetMapping
    public List<SearchResponse> list(@RequestParam(required = false) boolean visualized,
                                     @RequestParam RegionTypeEnum regionType) {
        List<Ape> searchs = apeService.search(visualized, regionType);
        return converter.mapList(searchs, SearchResponse.class);
    }

    @GetMapping(value = "/favorite")
    public List<Ape> favorites() {
        return apeService.findAllFavorites();
    }
}
