package com.example.farejador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.farejador.Mail;
import com.example.farejador.enums.IntentionEnum;
import com.example.farejador.enums.RegionTypeEnum;
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
    public void findApes(RegionTypeEnum regionType, IntentionEnum intentionEnum) throws Exception {
        Region region = regionService.findByType(regionType);
        scrapingService.execute(region, intentionEnum);
        sendMail(region.getName(), regionType, intentionEnum);
    }

    private void sendMail(String regionName, RegionTypeEnum regionType, IntentionEnum intentionEnum) {
        List<Ape> apes = apeService.findAllByVisualizedAndFavoriteSystemAndRegion_Type(false, true, regionType);
        mail.sendSimpleMessage("NOVIDADES DO FAREJADOR(" + intentionEnum.name() + "): " + regionName, Mail.mountBodyMail(apes), EMAIL_HENRY, EMAIL_SIMONE);
    }
}
