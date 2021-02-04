package com.alex_nechaev.androidonefinalproject;

import java.util.Random;

public class EnemyFactory {

private static Random random = new Random();

    public static GameObject createEnemy(eEnemyType enemyType,GameView gameView){
        int xPosition = random.nextInt(gameView.getWidth()-100);
        int yPosition = gameView.getHeight()/8*(-1);

        switch(enemyType){
            case BlueAlien:
                return new BlueAlien(xPosition,yPosition,10);
            case GreenAlien:
                return new GreenAlien(xPosition,yPosition,9);
            case DuckAlien:
                return new DuckAlien(xPosition,yPosition,9);
            case MathafixAlien:
                return new MathafixAlien(xPosition,yPosition,8);
            case UFO:
                return new UFO(xPosition,yPosition,10);
        }
        return null;
    }
}
