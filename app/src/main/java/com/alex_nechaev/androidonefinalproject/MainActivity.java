package com.alex_nechaev.androidonefinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    static int SCREEN_WIDTH, SCREEN_HEIGHT;
    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        SCREEN_HEIGHT = displayMetrics.heightPixels;
        SCREEN_WIDTH = displayMetrics.widthPixels;
        Bitmaps bitmaps = new Bitmaps(getResources());

        gameView = new GameView(MainActivity.this);
        setContentView(gameView);
//        setContentView(new GameView(MainActivity.this));
        Log.d("STATE", "onCreate: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
        Log.d("STATE", "onPause: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
        Log.d("STATE", "onResume: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("STATE", "onStop: ");
    }
}