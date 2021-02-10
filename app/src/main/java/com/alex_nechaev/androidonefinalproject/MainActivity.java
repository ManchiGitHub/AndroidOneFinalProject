package com.alex_nechaev.androidonefinalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static int SCREEN_WIDTH, SCREEN_HEIGHT;
    GameView gameView;
    ImageButton pauseBtn;
    Button resumeBtn;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        SCREEN_HEIGHT = displayMetrics.heightPixels;
        SCREEN_WIDTH = displayMetrics.widthPixels;
        Bitmaps bitmaps = new Bitmaps(getResources());

        gameView = new GameView(MainActivity.this);


        FrameLayout gameLayout = new FrameLayout(this);



        LinearLayout gameWidgets = new LinearLayout(this);
        gameWidgets.setGravity(Gravity.TOP | Gravity.RIGHT);

        pauseBtn = new ImageButton(this);
        pauseBtn.setImageDrawable(getResources().getDrawable(R.drawable.pause_drawable));
        pauseBtn.setBackground(null);
        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(12,12,12,12);

        pauseBtn.setLayoutParams(params);
        gameWidgets.requestLayout();

        gameWidgets.addView(pauseBtn);

        gameLayout.addView(gameView);
        gameLayout.addView(gameWidgets);

        builder = new AlertDialog.Builder(MainActivity.this);
//        View pauseLayout = getLayoutInflater().inflate(R.layout.pause_menu,null);
//        builder.setView(pauseLayout);
        builder.setCancelable(false);


        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.setPauseButtonPressed(true);
                builder.setPositiveButton("Resume", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Resume", Toast.LENGTH_SHORT).show();
                        gameView.setPauseButtonPressed(false);
                    }
                });
                builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Exit", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });



        Log.d("STATE", "onCreate: ");
        Log.d("STATE", "pauseBtn: "+gameView.isPauseButtonPressed());
        setContentView(gameLayout);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
        builder.show();
        Log.d("STATE", "onPause: ");
        Log.d("STATE", "pauseBtn: "+gameView.isPauseButtonPressed());
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
        Log.d("STATE", "onResume: ");
        Log.d("STATE", "pauseBtn: "+gameView.isPauseButtonPressed());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("STATE", "onStop: ");
        Log.d("STATE", "pauseBtn: "+gameView.isPauseButtonPressed());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("STATE", "onStart: ");
        Log.d("STATE", "pauseBtn: "+gameView.isPauseButtonPressed());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("STATE", "onRestart: ");
        Log.d("STATE", "pauseBtn: "+gameView.isPauseButtonPressed());

    }
}