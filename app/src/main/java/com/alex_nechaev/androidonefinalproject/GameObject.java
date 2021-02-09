package com.alex_nechaev.androidonefinalproject;

import android.graphics.Bitmap;
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

    public void move() {
        rect.set((int) xPosition, (int) yPosition, (int) xPosition + characterBitmap.getWidth(), (int) yPosition + characterBitmap.getHeight());
    }


    public boolean isCollision(GameObject gameObject) {

//        if (gameObject != null) {
//            float leftBorder = gameObject.getLeftBorder();
//            float rightBorder = gameObject.getRightBorder();
//            float topBorder = gameObject.getTopBorder();
//            float bottomBorder = gameObject.getBottomBorder();
//
//            //Collision test in the Y axis:
//            boolean collisionFromTop = bottomBorder >= getTopBorder() && bottomBorder <= getBottomBorder();
//            boolean collisionFromBottom = topBorder >= getTopBorder() && topBorder <= getBottomBorder();
//            //Collision test in the X axis:
//            boolean collisionFromRight = getRightBorder() >= leftBorder && getRightBorder() <= rightBorder;
//            boolean collisionFromLeft = getLeftBorder() >= leftBorder && getLeftBorder() <= rightBorder;
//
//            if ((collisionFromTop || collisionFromBottom) && (collisionFromRight || collisionFromLeft)) {
//                //Collision is Happened
//                Log.d("Collision", "Collision with" + gameObject.toString());
//                Log.d("Collision", "player:" + getLeftBorder() + ", " + getTopBorder() + ", " + getRightBorder() + ", " + getBottomBorder());
//                Log.d("Collision", "Object:" + leftBorder + ", " + topBorder + ", " + rightBorder + ", " + bottomBorder);
//                return true;
//            }
//        }
//        return false;

        if(gameObject!=null){
            if (this.rect.intersect(gameObject.rect)) {
                Log.d("Collision", "Collision with: "+gameObject.toString());
                Log.d("Collision", "left:"+rect.left+", top:"+rect.top+", right:"+rect.right+", bottom:"+rect.bottom);
                Log.d("Collision", "left:"+gameObject.rect.left+", top:"+gameObject.rect.top+", right:"+gameObject.rect.right+", bottom:"+gameObject.rect.bottom);
                return true;
            }
        }

        return false;
    }

}
