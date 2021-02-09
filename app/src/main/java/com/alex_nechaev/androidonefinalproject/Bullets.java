package com.alex_nechaev.androidonefinalproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Bullets extends GameObject {

    private float yPosition;
    private float xPosition;
    private static int speed = 100;
    private Bitmap bitmap;


    public Bullets(Bitmap characterBitmap, float xPosition, float yPosition, int speed) {
        super(characterBitmap, xPosition, yPosition, speed);
        this.bitmap = characterBitmap;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    @Override
    public boolean isCollision(GameObject go) {

        if ((this.xPosition >= go.getXPosition()) && this.xPosition <=  go.getXPosition()+go.getSpriteWidth()){
            if(this.yPosition <= (go.getYPosition()+go.getSpriteHeight()) && this.yPosition >= go.yPosition ){
                return true;
            }
        }
        return false;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public float getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public float getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    @Override
    public void move() {
        this.yPosition -= speed;
    }
}
