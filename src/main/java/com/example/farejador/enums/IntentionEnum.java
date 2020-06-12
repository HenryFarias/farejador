package com.example.farejador.enums;

import java.util.stream.Stream;

import lombok.Getter;

@Getter
public enum IntentionEnum {

    SALE("//*[@id=\"content\"]/div/div[1]/div/div/div[2]/div/div/ul/li[1]/a/span[1]"),
    RENT("//*[@id=\"content\"]/div/div[1]/div/div/div[2]/div/div/ul/li[3]/a/span[1]");

    private final String xpath;

    IntentionEnum(String xpath) {
        this.xpath = xpath;
    }

    public static Stream<IntentionEnum> stream() {
        return Stream.of(IntentionEnum.values());
    }
}
