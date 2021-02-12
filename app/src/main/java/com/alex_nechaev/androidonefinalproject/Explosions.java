package com.alex_nechaev.androidonefinalproject;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;

import android.view.View;

public class Explosions extends View {

    private float xPosition = 0;
    private float yPosition = 0;
    private boolean explode = false;

    public void setExplode(boolean value) {
        this.explode = value;
    }

    public boolean isExplode() {
        return explode;
    }

    public float getxPosition() {
        return xPosition;
    }

    public void setxPosition(float xPosition) {
        this.xPosition = xPosition;
    }

    public float getyPosition() {
        return yPosition;
    }

    public void setyPosition(float yPosition) {
        this.yPosition = yPosition;
    }

    public Explosions(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (!this.explode) {
            return;
        }
        canvas.drawBitmap(Bitmaps.enemyExplosionImg, this.xPosition, this.yPosition, null);
        this.explode = false;
    }
}


