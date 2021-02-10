package com.alex_nechaev.androidonefinalproject;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

public class Player extends GameObject {


    public Player(float xPosition, float yPosition, int speed) {
        super(Bitmaps.playerImg, xPosition, yPosition, speed);
    }

    public Bitmap getBitmap() {
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

    public float getSpriteWidth() {
        return characterBitmap.getWidth();
    }

    @Override
    public void move() {

    }

    public void move(float x, float y) {
        this.xPosition = x;
        this.yPosition = y;
        super.rect.set((int) xPosition, (int) yPosition, (int) xPosition + characterBitmap.getWidth(), (int) yPosition + characterBitmap.getHeight());
    }


}
