package com.alex_nechaev.androidonefinalproject;

import java.util.Random;

public class EnemyFactory {

private static Random random = new Random();

    public static GameObject createEnemy(eEnemyType enemyType,GameView gameView,int score){
        int xPosition = random.nextInt(gameView.getWidth()-100);
        int yPosition = gameView.getHeight()/8*(-1);
        double deltaScore = score/20000;
        switch(enemyType){
            case BlueAlien:
                return new BlueAlien(xPosition,yPosition,6+(int)deltaScore);
            case GreenAlien:
                return new GreenAlien(xPosition,yPosition,5+(int)deltaScore);
            case RobotAlien:
                return new RobotAlien(xPosition,yPosition,5+(int)deltaScore);
            case OrangeAlien:
                return new OrangeAlien(xPosition,yPosition,5+(int)deltaScore);
            case UFO:
                return new UFO(xPosition,yPosition,6+(int)deltaScore);
        }
        return null;
    }
}
