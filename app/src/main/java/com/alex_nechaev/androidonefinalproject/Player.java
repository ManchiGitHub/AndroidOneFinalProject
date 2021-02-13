package com.alex_nechaev.androidonefinalproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Player extends GameObject {

    private boolean hasShield;
    private boolean hasExploded;
    private Bitmap shield;
    private Bitmap explosion;

    public Player(float xPosition, float yPosition, int speed) {
        super(Bitmaps.playerImg, xPosition, yPosition, speed);
        this.shield = Bitmaps.playerShieldImg;
        this.explosion = Bitmaps.explosionImg;
        this.hasShield = false;
        this.hasExploded = false;
    }

    public Bitmap getBitmapLevel1() {
        return characterBitmap;
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

    public boolean hasExploded() {
        return hasExploded;
    }

    public void setHasExploded(boolean hasExploded) {
        this.hasExploded = hasExploded;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(characterBitmap, getXPosition() - getSpriteWidth() / 2, getYPosition() - getSpriteHeight() / 2, null);
        if(hasShield) {
            canvas.drawBitmap(shield,getXPosition() - this.shield.getWidth() / 2, getYPosition() - this.shield.getHeight() / 2, null);
        }
        if(hasExploded){
            canvas.drawBitmap(explosion,getXPosition() - this.explosion.getWidth() / 2, getYPosition() - this.shield.getHeight() / 2, null);
        }
    }

    @Override
    public void move() {
    }

    public boolean hasShield() {
        return hasShield;
    }

    public void setHasShield(boolean playerHasShield) {
        hasShield = playerHasShield;
    }

    public void move(float x, float y) {
        this.xPosition = x;
        this.yPosition = y;
        super.rect.set((int) xPosition, (int) yPosition, (int) xPosition + characterBitmap.getWidth(), (int) yPosition + characterBitmap.getHeight());
    }


}
