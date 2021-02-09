package com.alex_nechaev.androidonefinalproject;

public class MathafixAlien extends GameObject {

    public MathafixAlien(float xPosition, float yPosition, int speed) {
        super(Bitmaps.mathafixAlienImg, xPosition, yPosition, speed);
    }

    @Override
    public void move() {
        setYPosition(getYPosition()+speed);
        super.move();
    }
}
