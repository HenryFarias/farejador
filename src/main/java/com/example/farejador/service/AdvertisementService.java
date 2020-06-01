package com.example.farejador.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.farejador.Mail;
import com.example.farejador.WebDriverControl;
import com.example.farejador.models.Ape;
import com.example.farejador.scraping.AdvertisementScraping;
import com.example.farejador.scraping.ListAdvertisementScraping;

@Service
public class AdvertisementService {

    private final WebDriverControl webDriverControl;
    private final ListAdvertisementScraping listAdvertisementScraping;
    private final AdvertisementScraping advertisementScraping;
    private final Mail mail;
    private final ApeService apeService;

    @Autowired
    public AdvertisementService(WebDriverControl webDriverControl, ListAdvertisementScraping listAdvertisementScraping, AdvertisementScraping advertisementScraping, Mail mail, ApeService apeService) {
        this.webDriverControl = webDriverControl;
        this.listAdvertisementScraping = listAdvertisementScraping;
        this.advertisementScraping = advertisementScraping;
        this.mail = mail;
        this.apeService = apeService;
    }

    @Async
    public void executeScraping() throws Exception {
        System.out.println("DATA/HORA INICIO: " + new Date());
        webDriverControl.setWebDriverControl();

        listAdvertisementScraping.execute();
        advertisementScraping.execute();

        webDriverControl.getDriver().quit();
        System.out.println("DATA/HORA FIM: " + new Date());

        sendMail();
    }

    private void sendMail() {
        List<Ape> apes = apeService.findAllByVisualizedAndFavoriteSystem(false, true);
        String links = apes.stream()
                           .map(Ape::getLink)
                           .collect(Collectors.joining("\n"));
        mail.sendSimpleMessage(Mail.TO, Mail.SUBJECT, Mail.mountBodyMail(links));
    }
}
