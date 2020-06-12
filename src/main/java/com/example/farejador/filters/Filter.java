package com.example.farejador.filters;

import org.springframework.stereotype.Component;

import com.example.farejador.scraping.WebDriverControl;
import com.example.farejador.models.Region;

@Component
public interface Filter {

    boolean executeFilter(WebDriverControl webDriverControl);
    boolean isForThis(Region region);

}
