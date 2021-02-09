package com.alex_nechaev.androidonefinalproject;


import android.util.Log;

public class DuckAlien extends GameObject {

    int moves = 0;
    boolean speedUp = false;

    public DuckAlien(float xPosition, float yPosition, int speed) {
        super(Bitmaps.duckAlienImg, xPosition, yPosition, speed);
    }

    @Override
    public float getRightBorder() {
        return super.getRightBorder()+60;
    }

    @Override
    public float getLeftBorder() {
        return super.getLeftBorder()+60;
    }

    @Override
    public float getTopBorder() {
        return super.getTopBorder()-200;
    }

    @Override
    public float getBottomBorder() {
        return super.getBottomBorder()+100;
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
        super.move();
    }
}
