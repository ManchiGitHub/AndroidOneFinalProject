package com.alex_nechaev.androidonefinalproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public abstract class GameObject {

    Bitmap characterBitmap;
    float xPosition, yPosition;
    int speed;

    public GameObject(Bitmap characterBitmap, float xPosition, float yPosition, int speed) {
        this.characterBitmap = characterBitmap;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.speed = speed;
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

    public float getRightBorder(){
        return xPosition+characterBitmap.getWidth();
    }

    public float getLeftBorder(){
        return xPosition;
    }

    public float getTopBorder(){
        return yPosition;
    }

    public float getBottomBorder(){
        return yPosition+characterBitmap.getHeight();
    }

    public float getSpriteWidth(){
        return characterBitmap.getWidth();
    }

    public float getSpriteHeight(){
        return characterBitmap.getHeight();
    }

    public abstract void move();

    public boolean isCollision(GameObject gameObject) {
        if (gameObject instanceof BlueAlien) {
            BlueAlien blueAlien = (BlueAlien) gameObject;

            float leftBorder = blueAlien.getLeftBorder();
            float rightBorder = blueAlien.getRightBorder();
            float topBorder = blueAlien.getTopBorder();
            float bottomBorder = blueAlien.getBottomBorder();

            //Collision test in the Y axis:
            boolean collisionFromTop = bottomBorder>=getTopBorder() && bottomBorder<=getBottomBorder();
            boolean collisionFromBottom = topBorder>=getTopBorder() && topBorder<=getBottomBorder();
            //Collision test in the X axis:
            boolean collisionFromRight = getRightBorder() >= leftBorder && getRightBorder() <=rightBorder;
            boolean collisionFromLeft = getLeftBorder() >= leftBorder && getLeftBorder() <= rightBorder;

            if ((collisionFromTop || collisionFromBottom) && (collisionFromRight || collisionFromLeft)){
                //Collision is Happened
                Log.d("Collision","Collision Collision Collision");
                return true;
            }

        }
        return false;
    }

}
