package com.alex_nechaev.androidonefinalproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class GameObject {

    Bitmap characterBitmap;
    float xPosition, yPosition;
    int speed;
    Rect rect;

    public GameObject(Bitmap characterBitmap, float xPosition, float yPosition, int speed) {
        this.characterBitmap = characterBitmap;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.speed = speed;
        this.rect = new Rect();
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

    public float getRightBorder() {
        return xPosition + characterBitmap.getWidth();
    }

    public float getLeftBorder() {
        return xPosition;
    }

    public float getTopBorder() {
        return yPosition;
    }

    public float getBottomBorder() {
        return yPosition + characterBitmap.getHeight();
    }

    public float getSpriteWidth() {
        return characterBitmap.getWidth();
    }

    public float getSpriteHeight() {
        return characterBitmap.getHeight();
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(characterBitmap, getXPosition(), getYPosition(), null);
    }

    public void move() {
        rect.set((int) xPosition, (int) yPosition, (int) xPosition + characterBitmap.getWidth(), (int) yPosition + characterBitmap.getHeight());
    }

    public boolean isCollision(GameObject gameObject) {
        if(gameObject!=null){
            return this.rect.intersect(gameObject.rect);
        }
        return false;
    }

}
