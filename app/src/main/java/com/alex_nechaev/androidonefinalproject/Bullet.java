package com.alex_nechaev.androidonefinalproject;

import android.graphics.Bitmap;

public class Bullet extends GameObject {

    private float yPosition;
    private float xPosition;
    private final static int BULLET_SPEED = 50;
    private Bitmap bitmap;


    public Bullet(float xPosition, float yPosition) {
        super(Bitmaps.bulletsImg, xPosition, yPosition,BULLET_SPEED);
        this.bitmap = characterBitmap;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    @Override
    public boolean isCollision(GameObject go) {

        if ((this.xPosition >= go.getXPosition()) && this.xPosition <= go.getXPosition() + go.getSpriteWidth()) {
            if (this.yPosition <= (go.getYPosition() + go.getSpriteHeight()) && this.yPosition >= go.yPosition) {
                return true;
            }
        }
        return false;
    }

    public int getSpeed() {
        return BULLET_SPEED;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public float getYPosition() {
        return yPosition;
    }

    public void setYPosition(float yPosition) {
        this.yPosition = yPosition;
    }

    public float getXPosition() {
        return xPosition;
    }

    public void setXPosition(float xPosition) {
        this.xPosition = xPosition;
    }

    @Override
    public void move() {
        this.yPosition -= BULLET_SPEED;
    }
}