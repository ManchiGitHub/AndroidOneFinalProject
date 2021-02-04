package com.alex_nechaev.androidonefinalproject;

import android.graphics.Bitmap;
import android.util.Log;

public class Player extends GameObject {


    public Player(float xPosition, float yPosition, int speed) {
        super(Bitmaps.playerImg, xPosition, yPosition, speed);
    }

    public Bitmap getPlayerBitmap() {
        return characterBitmap;
    }

    public void setPlayerBitmap(Bitmap playerBitmap) {
        this.characterBitmap = playerBitmap;
    }

    public float getXPosition() {
        return xPosition;
    }

    public void setXPosition(float xPosition) {
        this.xPosition = xPosition;
    }

    public float getYPosition() {
        return yPosition;
    }

    public void setYPosition(float yPosition) {
        this.yPosition = yPosition;
    }

    public float getSpriteHeight() {
        return characterBitmap.getHeight();
    }

    @Override
    public void move() {

    }

    public float getSpriteWidth() {
        return characterBitmap.getWidth();
    }

    @Override
    public float getRightBorder() {
        return super.getRightBorder();
    }

    @Override
    public float getLeftBorder() {
        return super.getLeftBorder();
    }

    @Override
    public float getTopBorder() {
        return super.getTopBorder();
    }

    @Override
    public float getBottomBorder() {
        return super.getBottomBorder();
    }

    public void move(float x, float y) {
        this.xPosition = x;
        this.yPosition = y;
    }


}
