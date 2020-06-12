package com.example.farejador.service;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.farejador.scraping.Advertisemens;
import com.example.farejador.scraping.WebDriverControl;
import com.example.farejador.models.Region;
import com.example.farejador.scraping.AdvertisementScraping;
import com.example.farejador.scraping.ListAdvertisementScraping;

@Service
public class ScrapingService {

    private final ListAdvertisementScraping listAdvertisementScraping;
    private final AdvertisementScraping advertisementScraping;
    private final BeanFactory beanFactory;
    private final Advertisemens advertisemens;

    @Autowired
    public ScrapingService(ListAdvertisementScraping listAdvertisementScraping, AdvertisementScraping advertisementScraping, BeanFactory beanFactory, Advertisemens advertisemens) {
        this.listAdvertisementScraping = listAdvertisementScraping;
        this.advertisementScraping = advertisementScraping;
        this.beanFactory = beanFactory;
        this.advertisemens = advertisemens;
    }

    public void execute(Region region) throws Exception {
        advertisemens.setLinks(new ArrayList<>());
        System.out.println("DATA/HORA INICIO: " + new Date());

        WebDriverControl webDriverControl = beanFactory.getBean(WebDriverControl.class);

        webDriverControl.setWebDriverControl();

        listAdvertisementScraping.execute(region);
        advertisementScraping.execute(region);

        webDriverControl.getDriver().quit();
        System.out.println("DATA/HORA FIM: " + new Date());
    }
}
