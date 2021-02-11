package com.alex_nechaev.androidonefinalproject;


public class GreenAlien extends GameObject {

    boolean direction = true;

    public GreenAlien(float xPosition, float yPosition, int speed) {
        super(Bitmaps.greenAlienImg, xPosition, yPosition, speed);
    }
    @Override
    public void move() {
        if (direction && getRightBorder() < GameActivity.SCREEN_WIDTH) {
            setXPosition(getXPosition() + 10);
        } else if (!direction && getLeftBorder() > 0) {
            setXPosition(getXPosition() - 10);
        }
        if (getRightBorder() >= GameActivity.SCREEN_WIDTH) {
            direction = false;
        } else if (getLeftBorder() <= 0) {
            direction = true;
        }
        setYPosition(getYPosition() + speed);
        super.move();
    }
}
