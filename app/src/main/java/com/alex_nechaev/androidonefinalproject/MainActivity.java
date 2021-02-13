package com.alex_nechaev.androidonefinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_main);

        ImageButton playImgBtn = findViewById(R.id.play_img_btn);
        playImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ImageButton scoreImgBtn = findViewById(R.id.score_img_btn);
        scoreImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ArrayList<PlayerDetails> playerDetails = (ArrayList<PlayerDetails>) FileManager.readFromFile(MainActivity.this,GameActivity.PLAYER_DETAILS);
//                if(playerDetails == null){
//                    playerDetails = new ArrayList<PlayerDetails>();
//                }
//                for(PlayerDetails pd: playerDetails){
//                    Log.d("pd", pd.toString());
//                }

                Intent intent = new Intent(MainActivity.this,TableScoreActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}