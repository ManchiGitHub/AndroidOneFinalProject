package com.alex_nechaev.androidonefinalproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Player extends GameObject {

    private boolean isPlayerHasShield;
    private Bitmap shield;
    long playShieldTimer;

    public Player(float xPosition, float yPosition, int speed) {
        super(Bitmaps.playerImg, xPosition, yPosition, speed);
        this.shield = Bitmaps.playerShieldImg;
        this.isPlayerHasShield = false;
    }

    public long getPlayShieldTimer() {
        return playShieldTimer;
    }

    public void setPlayShieldTimer(long playShieldTimer) {
        this.playShieldTimer = playShieldTimer;
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
    public void draw(Canvas canvas) {
        canvas.drawBitmap(characterBitmap, getXPosition() - getSpriteWidth() / 2, getYPosition() - getSpriteHeight() / 2, null);
        if(isPlayerHasShield) {
            canvas.drawBitmap(this.shield,getXPosition() - this.shield.getWidth() / 2, getYPosition() - this.shield.getHeight() / 2, null);
        }
    }

    private void startShieldTimer() {
        this.playShieldTimer = System.currentTimeMillis();
    }

    @Override
    public void move() {

    }

    public boolean hasShield() {
        return isPlayerHasShield;
    }

    public void setHasShield(boolean playerHasShield) {
        isPlayerHasShield = playerHasShield;
        if(isPlayerHasShield){
            startShieldTimer();
        }
    }

    public void move(float x, float y) {
        this.xPosition = x;
        this.yPosition = y;
        super.rect.set((int) xPosition, (int) yPosition, (int) xPosition + characterBitmap.getWidth(), (int) yPosition + characterBitmap.getHeight());
    }


}
