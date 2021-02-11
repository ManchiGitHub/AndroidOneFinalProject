package com.alex_nechaev.androidonefinalproject;

public class OrangeAlien extends GameObject {

    public OrangeAlien(float xPosition, float yPosition, int speed) {
        super(Bitmaps.orangeAlienImg, xPosition, yPosition, speed);
    }

    @Override
    public void move() {
        setYPosition(getYPosition()+speed);
        super.move();
    }
}
