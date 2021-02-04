package com.alex_nechaev.androidonefinalproject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum eEnemyType {

    BlueAlien,
    GreenAlien,
    DuckAlien,
    MathafixAlien,
    UFO;

    private static final List<eEnemyType> ENEMIES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = ENEMIES.size();
    private static final Random RANDOM = new Random();

    public static eEnemyType randomEnemy() {
        return ENEMIES.get(RANDOM.nextInt(SIZE));
    }

}
