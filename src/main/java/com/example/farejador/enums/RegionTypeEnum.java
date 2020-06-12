package com.example.farejador.enums;

import java.util.stream.Stream;

public enum RegionTypeEnum {

    INGLESES, NORTH, EAST, INGLESES_SALE;

    public static Stream<RegionTypeEnum> stream() {
        return Stream.of(RegionTypeEnum.values());
    }
}
