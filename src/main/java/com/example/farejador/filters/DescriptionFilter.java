package com.example.farejador.filters;

import java.util.Arrays;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.example.farejador.enums.RegionTypeEnum;
import com.example.farejador.scraping.WebDriverControl;
import com.example.farejador.models.Region;

@Component
public class DescriptionFilter implements Filter {

    private static final String DESCRICAO = "//*[@id=\"content\"]/div[2]/div/div[2]/div[1]/div[6]/h1";

    @Override
    public boolean executeFilter(WebDriverControl webDriverControl) {
        String description = webDriverControl.getDriver().findElement(By.xpath(DESCRICAO)).getText();
        return !description.contains("temporada") &&
                !description.contains("TEMPORADA") &&
                !description.contains("Temporada") &&
                !description.contains("Novembro") &&
                !description.contains("NOVEMBRO") &&
                !description.contains("novembro") &&
                !description.contains("dezembro") &&
                !description.contains("DEZEMBRO") &&
                !description.contains("Dezembro");
    }

    @Override
    public boolean isForThis(Region region) {
        return Arrays.asList(RegionTypeEnum.values()).contains(region.getType());
    }
}
