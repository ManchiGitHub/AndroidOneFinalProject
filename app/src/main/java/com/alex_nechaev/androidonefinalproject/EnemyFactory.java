package com.alex_nechaev.androidonefinalproject;

import java.util.Random;

public class EnemyFactory {

private static Random random = new Random();

    public static GameObject createEnemy(eEnemyType enemyType,GameView gameView,int deltaScore){
        int xPosition = random.nextInt(gameView.getWidth()-100);
        int yPosition = gameView.getHeight()/8*(-1);
        double calculatedDeltaScore = deltaScore/3500;
        switch(enemyType){
            case BlueAlien:
                return new BlueAlien(xPosition,yPosition,6+(int)calculatedDeltaScore);
            case GreenAlien:
                return new GreenAlien(xPosition,yPosition,5+(int)calculatedDeltaScore);
            case RobotAlien:
                return new RobotAlien(xPosition,yPosition,5+(int)calculatedDeltaScore);
            case OrangeAlien:
                return new OrangeAlien(xPosition,yPosition,5+(int)calculatedDeltaScore);
            case UFO:
                return new UFO(xPosition,yPosition,6+(int)calculatedDeltaScore);
        }
        return null;
    }
}
