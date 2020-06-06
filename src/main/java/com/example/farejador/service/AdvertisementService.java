package com.example.farejador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.farejador.Mail;
import com.example.farejador.RegionTypeEnum;
import com.example.farejador.models.Ape;
import com.example.farejador.models.Region;

@Service
public class AdvertisementService {

    private static final String TO = "henrysjfarias@gmail.com, simonew.passos@gmail.com";

    private final Mail mail;
    private final ApeService apeService;
    private final RegionService regionService;
    private final ScrapingService scrapingService;

    @Autowired
    public AdvertisementService(Mail mail, ApeService apeService, RegionService regionService, ScrapingService scrapingService) {
        this.mail = mail;
        this.apeService = apeService;
        this.regionService = regionService;
        this.scrapingService = scrapingService;
    }

    @Async
    public void findApes(RegionTypeEnum regionType) throws Exception {
        Region region = regionService.findByType(regionType);
        scrapingService.execute(region);
        sendMail(region.getName());
    }

    private void sendMail(String regionName) {
        List<Ape> apes = apeService.findAllByVisualizedAndFavoriteSystem(false, true);
        mail.sendSimpleMessage(TO, "NOVIDADE DO FAREJADOR: " + regionName, Mail.mountBodyMail(apes));
    }
}
