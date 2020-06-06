package com.example.farejador.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.farejador.Mail;
import com.example.farejador.RegionTypeEnum;
import com.example.farejador.models.Ape;
import com.example.farejador.models.Person;
import com.example.farejador.models.Region;

@Service
public class AdvertisementService {

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
        sendMail(region.getPeople(), region.getName());
    }

    private void sendMail(List<Person> people, String regionName) {
        String emails = people.stream()
                .map(Person::getEmails)
                .collect(Collectors.joining(","));

        List<Ape> apes = apeService.findAllByVisualizedAndFavoriteSystem(false, true);
        mail.sendSimpleMessage(emails, "NOVIDADE DO FAREJADOR: " + regionName, Mail.mountBodyMail(apes));
    }
}
