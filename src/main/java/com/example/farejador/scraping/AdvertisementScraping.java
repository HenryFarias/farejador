package com.example.farejador.scraping;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.farejador.filters.Filter;
import com.example.farejador.models.Ape;
import com.example.farejador.models.Region;
import com.example.farejador.repository.ApeRepository;

@Component
public class AdvertisementScraping {

    private final WebDriverControl webDriverControl;
    private final Advertisemens advertisemens;
    private final ApeRepository apeRepository;
    private final List<Filter> filters;

    @Autowired
    public AdvertisementScraping(WebDriverControl webDriverControl, Advertisemens advertisemens, ApeRepository apeRepository, List<Filter> filters) {
        this.webDriverControl = webDriverControl;
        this.advertisemens = advertisemens;
        this.apeRepository = apeRepository;
        this.filters = filters;
    }

    public void execute(Region region) throws Exception {
        int cont = 0;
        System.out.println("TOTAL LINKS :" + advertisemens.getLinks().size());

        for (String link : advertisemens.getLinks()) {
            cont++;
            System.out.println("NUMERO: " + cont);
            webDriverControl.getDriver().get(link);
            Thread.sleep(3000);

            try {
                saveApe(link, region);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveApe(String link, Region region) {
        Ape ape = new Ape();

        ape.setLink(link);
        ape.setRegion(region);

        if (filter(region)) {
            ape.setFavoriteSystem(true);
        }

        apeRepository.save(ape);
    }

    private boolean filter(Region region) {
        List<Filter> filtersByRegion = this.filters.stream().filter(filter -> filter.isForThis(region)).collect(Collectors.toList());
        List<Filter> result = filtersByRegion.stream().filter(filter -> filter.executeFilter(webDriverControl)).collect(Collectors.toList());

        return filtersByRegion == result;
    }
}
