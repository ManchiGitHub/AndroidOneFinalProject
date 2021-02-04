package com.alex_nechaev.androidonefinalproject;

public class Heart extends GameObject {
    public Heart(float xPosition, float yPosition, int speed) {
        super(Bitmaps.heartImg, xPosition, yPosition, speed);
    }

    public float getRightBorder() {
        return getXPosition() + getSpriteWidth();
    }

    public float getLeftBorder() {
        return getXPosition() + 40;
    }

    public float getTopBorder() {
        return getYPosition();
    }

    public float getBottomBorder() {
        return getYPosition() + getSpriteHeight();
    }

    @Override
    public void move() {
        setYPosition(getYPosition() + speed);
    }
}
