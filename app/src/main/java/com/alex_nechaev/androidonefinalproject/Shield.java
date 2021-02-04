package com.alex_nechaev.androidonefinalproject;

public class Shield extends GameObject {
    public Shield(float xPosition, float yPosition, int speed) {
        super(Bitmaps.shieldImg, xPosition, yPosition, speed);
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
        setYPosition(getYPosition()+speed);
    }
}
