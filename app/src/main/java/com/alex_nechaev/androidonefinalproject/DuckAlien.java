package com.alex_nechaev.androidonefinalproject;


public class DuckAlien extends GameObject {

    int moves = 0;
    boolean speedUp = false;

    public DuckAlien(float xPosition, float yPosition, int speed) {
        super(Bitmaps.duckAlienImg, xPosition, yPosition, speed);
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
        double newSpeed;
        if(moves%20 == 0){
            moves = 0;
            speedUp = !speedUp;
        }
        if(speedUp){
            newSpeed = speed*1.5;
        }else{
            newSpeed = speed/4;
        }
        setYPosition(getYPosition()+(float)newSpeed);
        moves++;
    }
}
