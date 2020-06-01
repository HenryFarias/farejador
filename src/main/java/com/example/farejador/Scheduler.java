package com.example.farejador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.farejador.service.AdvertisementService;

@Component
public class Scheduler {

    private final AdvertisementService advertisementService;

    @Autowired
    public Scheduler(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @Scheduled(cron = "0 0 12 1/1 * ?")
    public void schdule() throws Exception {
        advertisementService.executeScraping();
    }
}
