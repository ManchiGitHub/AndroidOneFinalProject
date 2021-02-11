package com.alex_nechaev.androidonefinalproject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;

public class Bitmaps {

    static Bitmap playerImg, blueAlienImg, greenAlienImg, duckAlienImg, mathafixAlienImg, UFOImg, coinImg, shieldImg, heartImg, galaxyBackgroundImg, starsImg, bulletsImg, playerShieldImg;
    private Bitmap unscaledGalaxyBackground, unscaledStars;

    public Bitmaps(Resources resources) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        playerImg = getBitmap(resources, R.drawable.player_rocket);
        playerShieldImg = getBitmap(resources, R.drawable.player_shield);
        blueAlienImg = getBitmap(resources, R.drawable.blue_alien);
        greenAlienImg = getBitmap(resources, R.drawable.green_alien);
        duckAlienImg = getBitmap(resources, R.drawable.duck_alien);
        mathafixAlienImg = getBitmap(resources, R.drawable.mathafix_alien);
        UFOImg = getBitmap(resources, R.drawable.ufo);
        coinImg = getBitmap(resources, R.drawable.coin_supply);
        shieldImg = getBitmap(resources, R.drawable.shield_supply);
        heartImg = getBitmap(resources, R.drawable.heart_supply);
        unscaledStars = getBitmap(resources, R.drawable.stars);
        unscaledGalaxyBackground = getBitmap(resources, R.drawable.space_galaxy);
        starsImg = Bitmap.createScaledBitmap(unscaledStars, GameActivity.SCREEN_WIDTH, GameActivity.SCREEN_HEIGHT, true);
        galaxyBackgroundImg = Bitmap.createScaledBitmap(unscaledGalaxyBackground, GameActivity.SCREEN_WIDTH, GameActivity.SCREEN_HEIGHT, true);
        bulletsImg = getBitmap(resources, R.drawable.bullet);
    }


//    private Bitmap getBitmap(Resources resources,int drawableRes) {
//        Drawable drawable = resources.getDrawable(drawableRes);
//        Canvas canvas = new Canvas();
//        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//        canvas.setBitmap(bitmap);
//        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//        drawable.draw(canvas);
//        return bitmap;
//    }


    private Bitmap getBitmap(Resources resources, int drawableRes) {
        Bitmap bitmap = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawable = resources.getDrawable(drawableRes);
            Canvas canvas = new Canvas();
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            canvas.setBitmap(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            bitmap = BitmapFactory.decodeResource(resources, drawableRes);
        }
        return bitmap;

    }
}
