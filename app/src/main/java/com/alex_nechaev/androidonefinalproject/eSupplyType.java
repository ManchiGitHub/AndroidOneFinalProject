package com.alex_nechaev.androidonefinalproject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum eSupplyType {

    Coin,
    Shield,
    Heart;

    private static final List<eSupplyType> ENEMIES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = ENEMIES.size();
    private static final Random RANDOM = new Random();

    public static eSupplyType randomSupply() {
        return ENEMIES.get(RANDOM.nextInt(SIZE));
    }

}
