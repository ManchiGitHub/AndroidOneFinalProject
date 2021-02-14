package com.alex_nechaev.androidonefinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TableScoreActivity extends AppCompatActivity {

    boolean isMute;
    SharedPreferences sp;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sp = getSharedPreferences(MainActivity.GAME_KEY, MODE_PRIVATE);
        isMute = sp.getBoolean(MainActivity.IS_MUTE_KEY, false);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_score);

        ListView listView = findViewById(R.id.score_list);

        List<PlayerDetails> playerDetails = (List<PlayerDetails>) FileManager.readFromFile(TableScoreActivity.this,GameActivity.PLAYER_DETAILS);
        if(playerDetails == null){
            playerDetails = new ArrayList<PlayerDetails>();
        }
        Collections.sort(playerDetails);

        PlayerDetailsAdapter playerDetailsAdapter = new PlayerDetailsAdapter(playerDetails);
        listView.setAdapter(playerDetailsAdapter);

        ImageButton backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TableScoreActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (!isMute) {
            MainActivity.mp.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!isMute) {
            MainActivity.mp.start();
        }
    }


}
