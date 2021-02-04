package com.alex_nechaev.androidonefinalproject;

import java.util.Random;

public class SupplyFactory {

    private static Random random = new Random();

    public static GameObject createSupply(eSupplyType supplyType,GameView gameView){
        int xPosition = random.nextInt(gameView.getWidth()-100);
        int yPosition = gameView.getHeight()/6*(-1);

        switch(supplyType){
            case Coin:
                return new Coin(xPosition,yPosition,(xPosition%10)+5);
            case Shield:
                return new Shield(xPosition,yPosition,(xPosition%10)+5);
            case Heart:
                return new Heart(xPosition,yPosition,(xPosition%10)+5);
        }
        return null;
    }
}
