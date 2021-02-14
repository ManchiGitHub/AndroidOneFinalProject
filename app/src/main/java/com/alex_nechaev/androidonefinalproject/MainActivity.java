package com.alex_nechaev.androidonefinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    public static final String GAME_KEY = "game";
    public static final String IS_MUTE_KEY = "is_mute";

    boolean isMute;

    SharedPreferences sp;

    public static MediaPlayer mp;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences(GAME_KEY, MODE_PRIVATE);

        final ImageView volumeIv = findViewById(R.id.volume_iv);

        isMute = sp.getBoolean(IS_MUTE_KEY, false);

        // This if assures that the music will be created only one throughout the game
        if (mp == null) {
            mp = MediaPlayer.create(this, R.raw.main_activity_music);
            mp.setLooping(true); // Set looping
            mp.setVolume(0.6f, 0.6f);
        }

        if (!isMute) {
            mp.start();
        }
        else {
            volumeIv.setImageResource(R.drawable.volume_off);
        }

        Button playImgBtn = findViewById(R.id.play_img_btn);
        playImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button scoreImgBtn = findViewById(R.id.score_img_btn);
        scoreImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TableScoreActivity.class);
                startActivity(intent);
                finish();
            }
        });

        volumeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMute = !isMute;

                if (isMute) {
                    sp.edit().putBoolean(IS_MUTE_KEY, true).commit();
                    volumeIv.setImageResource(R.drawable.volume_off);
                    mp.pause();
                }
                else {
                    sp.edit().putBoolean(IS_MUTE_KEY, false).commit();
                    volumeIv.setImageResource(R.drawable.volume_on);
                    mp.start();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (!isMute) {
            mp.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!isMute) {
            mp.start();
        }
    }
}