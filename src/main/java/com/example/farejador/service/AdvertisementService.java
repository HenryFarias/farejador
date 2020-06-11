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

    private static final String EMAIL_HENRY = "henrysjfarias@gmail.com";
    private static final String EMAIL_SIMONE = "simonew.passos@gmail.com";

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
        sendMail(region.getName(), regionType);
    }

    private void sendMail(String regionName, RegionTypeEnum regionType) {
        List<Ape> apes = apeService.findAllByVisualizedAndFavoriteSystemAndRegion_Type(false, true, regionType);
        mail.sendSimpleMessage("NOVIDADE DO FAREJADOR: " + regionName, Mail.mountBodyMail(apes), EMAIL_HENRY, EMAIL_SIMONE);
    }
}
