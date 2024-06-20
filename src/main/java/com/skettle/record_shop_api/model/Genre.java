package com.skettle.record_shop_api.model;

import java.util.Arrays;

public enum Genre {
    ROCK,
    POP,
    ELECTRONIC,
    JAZZ,
    METAL,
    ALTERNATIVE,
    CLASSICAL,
    RAP;

    public static boolean isValid(String name) {
        return Arrays.stream(values()).anyMatch(e -> e.name().equals(name));
    }
}
