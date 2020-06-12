package com.example.farejador.filters;

import static com.example.farejador.RegionTypeEnum.INGLESES;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.example.farejador.scraping.WebDriverControl;
import com.example.farejador.models.Region;

@Component
public class CepFilter implements Filter {

    private static final String CEP = "//*[@id=\"content\"]/div[2]/div/div[2]/div[1]/div[30]/div/div[1]/div[2]/div[1]/div/dd";

    @Override
    public boolean executeFilter(WebDriverControl webDriverControl) {
        String cep = webDriverControl.getDriver().findElement(By.xpath(CEP)).getText();
        int lastDigitsCep = Integer.parseInt(cep.substring(5, 8));
        return lastDigitsCep >= 500 && lastDigitsCep <= 585;
    }

    @Override
    public boolean isForThis(Region region) {
        return region.getType().equals(INGLESES);
    }
}
