package com.example.farejador.response;

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
public class SearchResponse {

    private Long id;
    private String link;
}
