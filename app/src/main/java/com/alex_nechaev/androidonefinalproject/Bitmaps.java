package com.alex_nechaev.androidonefinalproject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;

public class Bitmaps {

    static Bitmap playerImg, blueAlienImg, greenAlienImg, robotAlienImg, orangeAlienImg, UFOImg, coinImg, shieldImg, heartImg,
            galaxyBackgroundImg, starsImg, bulletLv1Img,bulletLv2Img,bulletLv3Img, playerShieldImg, explosionImg, enemyExplosionImg;
    private Bitmap unscaledGalaxyBackground, unscaledStars;

    public Bitmaps(Resources resources) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        playerImg = getBitmap(resources, R.drawable.player_rocket);
        playerShieldImg = getBitmap(resources, R.drawable.game_effect_shield);
        blueAlienImg = getBitmap(resources, R.drawable.alien_blue);
        greenAlienImg = getBitmap(resources, R.drawable.alien_green);
        robotAlienImg = getBitmap(resources, R.drawable.alien_robot);
        orangeAlienImg = getBitmap(resources, R.drawable.alien_orange);
        UFOImg = getBitmap(resources, R.drawable.alien_ufo);
        coinImg = getBitmap(resources, R.drawable.supply_coin);
        shieldImg = getBitmap(resources, R.drawable.supply_shield);
        heartImg = getBitmap(resources, R.drawable.supply_heart);
        unscaledStars = getBitmap(resources, R.drawable.game_effect_stars);
        unscaledGalaxyBackground = getBitmap(resources, R.drawable.game_background);
        starsImg = Bitmap.createScaledBitmap(unscaledStars, GameActivity.SCREEN_WIDTH, GameActivity.SCREEN_HEIGHT, true);
        galaxyBackgroundImg = getBitmap(resources, R.drawable.game_background);
        bulletLv1Img = getBitmap(resources, R.drawable.bullet_level_1);
        bulletLv2Img = getBitmap(resources, R.drawable.bullet_level_2);
        bulletLv3Img = getBitmap(resources, R.drawable.bullet_level_3);
        explosionImg = getBitmap(resources, R.drawable.game_effect_player_explosion);
        enemyExplosionImg = getBitmap(resources, R.drawable.game_effect_explosion);
    }

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
