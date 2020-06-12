package com.example.farejador;

import java.util.stream.Stream;

public enum RegionTypeEnum {

    INGLESES, NORTH, EAST;

    public static Stream<RegionTypeEnum> stream() {
        return Stream.of(RegionTypeEnum.values());
    }
}
