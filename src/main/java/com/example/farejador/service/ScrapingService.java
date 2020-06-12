package com.example.farejador.service;

import java.util.Date;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.farejador.enums.IntentionEnum;
import com.example.farejador.models.Region;
import com.example.farejador.scraping.Advertisemens;
import com.example.farejador.scraping.AdvertisementScraping;
import com.example.farejador.scraping.ListAdvertisementScraping;
import com.example.farejador.scraping.WebDriverControl;

@Service
public class ScrapingService {

    private final ListAdvertisementScraping listAdvertisementScraping;
    private final AdvertisementScraping advertisementScraping;
    private final BeanFactory beanFactory;

    @Autowired
    public ScrapingService(ListAdvertisementScraping listAdvertisementScraping, AdvertisementScraping advertisementScraping, BeanFactory beanFactory) {
        this.listAdvertisementScraping = listAdvertisementScraping;
        this.advertisementScraping = advertisementScraping;
        this.beanFactory = beanFactory;
    }

    public void execute(Region region, IntentionEnum intentionEnum) throws Exception {
        Advertisemens advertisemens = new Advertisemens();
        System.out.println("DATA/HORA INICIO: " + new Date());

        WebDriverControl webDriverControl = beanFactory.getBean(WebDriverControl.class);

        webDriverControl.setWebDriverControl();

        listAdvertisementScraping.execute(region, intentionEnum, advertisemens);
        advertisementScraping.execute(region, advertisemens);

        webDriverControl.getDriver().quit();
        System.out.println("DATA/HORA FIM: " + new Date());
    }
}
