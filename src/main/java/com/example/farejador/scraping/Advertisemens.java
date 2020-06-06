package com.example.farejador.scraping;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Advertisemens {

    private List<String> links;

}
