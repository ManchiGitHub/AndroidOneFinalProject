package com.alex_nechaev.androidonefinalproject;

public class UFO extends GameObject {
    public UFO(float xPosition, float yPosition, int speed) {
        super(Bitmaps.UFOImg, xPosition, yPosition, speed);
    }

    @Override
    public void move() {
        setYPosition(getYPosition()+speed);
        super.move();
    }
}
