package com.example.farejador.filters;

import java.util.Arrays;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.example.farejador.enums.RegionTypeEnum;
import com.example.farejador.scraping.WebDriverControl;
import com.example.farejador.models.Region;

@Component
public class ValueMoneyFilter implements Filter {

    @Override
    public boolean executeFilter(WebDriverControl webDriverControl) {
        String valueMoney = webDriverControl.getDriver().findElement(By.tagName("h2")).getText();
        System.out.println("VALOR CAPTURADO: " + valueMoney);
        String value = valueMoney.replace("R$ ", "").replace(".", "");
        return Integer.parseInt(value) > 1000 && Integer.parseInt(value) <= 2500;
    }

    @Override
    public boolean isForThis(Region region) {
        return Arrays.asList(RegionTypeEnum.values()).contains(region.getType());
    }
}
