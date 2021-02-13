package com.alex_nechaev.androidonefinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TableScoreActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
}
