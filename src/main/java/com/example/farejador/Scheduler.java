package com.example.farejador;

import static com.example.farejador.neighborhood.NeighborhoodToScraping.EAST;
import static com.example.farejador.neighborhood.NeighborhoodToScraping.INGLESES;
import static com.example.farejador.neighborhood.NeighborhoodToScraping.NORTH;

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
    public void schduleIngleses() throws Exception {
        advertisementService.executeScraping(INGLESES);
    }

    @Scheduled(cron = "0 0 12 1/1 * ?")
    public void schduleNoth() throws Exception {
        advertisementService.executeScraping(NORTH);
    }

    @Scheduled(cron = "0 0 12 1/1 * ?")
    public void schduleEast() throws Exception {
        advertisementService.executeScraping(EAST);
    }
}
