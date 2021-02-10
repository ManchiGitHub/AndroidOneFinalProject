package com.alex_nechaev.androidonefinalproject;

public class Coin extends GameObject implements SupplyElement{
    public Coin(float xPosition, float yPosition, int speed) {
        super(Bitmaps.coinImg, xPosition, yPosition, speed);
    }

    @Override
    public void move() {
        setYPosition(getYPosition()+speed);
        super.move();
    }

    @Override
    public void activateSupplyElement() {

    }
}
