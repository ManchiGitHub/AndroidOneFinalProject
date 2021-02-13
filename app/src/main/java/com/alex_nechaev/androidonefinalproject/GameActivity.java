package com.alex_nechaev.androidonefinalproject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

//This activity runs when the "PLAY GAME" button is pressed.
public class GameActivity extends AppCompatActivity implements GameListener {

    static int SCREEN_WIDTH, SCREEN_HEIGHT;
    GameView gameView;
    FrameLayout gameLayout;
    ImageButton pauseBtn;
    Dialog pauseDialog;
    public static String PLAYER_DETAILS = "playerDetails";

    ArrayList<PlayerDetails> playerDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        SCREEN_HEIGHT = displayMetrics.heightPixels;
        SCREEN_WIDTH = displayMetrics.widthPixels;
        Bitmaps bitmaps = new Bitmaps(getResources());

        if (playerDetails == null) {
            playerDetails = new ArrayList<PlayerDetails>();
        }

        gameView = new GameView(GameActivity.this);
        startNewGame();

        pauseDialog = new Dialog(GameActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        pauseDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pauseDialog.setCancelable(false);
        pauseDialog.setContentView(R.layout.pause_menu);
        pauseDialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.setPauseButtonPressed(true);
                pauseDialog.show();
            }
        });

        ImageButton resumeBtn = pauseDialog.findViewById(R.id.resume_btn);
        resumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GameActivity.this, "Resume", Toast.LENGTH_SHORT).show();
                gameView.setPauseButtonPressed(false);
                pauseDialog.dismiss();
            }
        });

        ImageButton replayBtn = pauseDialog.findViewById(R.id.replay_btn);
        replayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gameView.setPauseButtonPressed(false);
                gameView.replayGame();
                pauseDialog.dismiss();
            }
        });

        ImageButton exitBtn = pauseDialog.findViewById(R.id.exit_btn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Back to Main Menu
                Dialog exitDialog;
                exitDialog = new Dialog(GameActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                exitDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                exitDialog.setCancelable(false);
                exitDialog.setContentView(R.layout.exit_menu);
                if(!gameView.isHasExited()){
                    exitDialog.show();
                }
                pauseDialog.dismiss();

                ImageButton applyBtn = exitDialog.findViewById(R.id.exit_yes_btn);
                applyBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(GameActivity.this, "Exit", Toast.LENGTH_SHORT).show();
                        gameView.setPauseButtonPressed(true);
                        gameView.setHasExited(true);
                        gameView.setGameOver(true);
                        Intent intent = new Intent(GameActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

                ImageButton cancelBtn = exitDialog.findViewById(R.id.exit_no_btn);
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gameView.setPauseButtonPressed(false);
                        exitDialog.dismiss();
                    }
                });
            }
        });

        setContentView(gameLayout);
    }

    @Override
    public void onGameOver() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int score = gameView.getScore();

                final Dialog gameOverDialog;
                gameOverDialog = new Dialog(GameActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                gameOverDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                gameOverDialog.setCancelable(false);
                gameOverDialog.setContentView(R.layout.gameover_menu);
                gameOverDialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;

                TextView playerScoreTextView = gameOverDialog.findViewById(R.id.player_score_text_vew);
                playerScoreTextView.setText("Your score is: " + score);
                EditText playerNameEditText = gameOverDialog.findViewById(R.id.player_name_edit_text);

                ImageButton submitBtn = gameOverDialog.findViewById(R.id.submit_btn);
                submitBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String playerName;

                        if (!playerNameEditText.getText().toString().equals(""))
                            playerName = playerNameEditText.getText().toString();
                        else
                            playerName = "Player" + System.currentTimeMillis();

                        playerDetails = (ArrayList<PlayerDetails>) FileManager.readFromFile(GameActivity.this, PLAYER_DETAILS);
                        if (playerDetails == null) {
                            playerDetails = new ArrayList<PlayerDetails>();
                        }
                        playerDetails.add(new PlayerDetails(score, playerName));
                        FileManager.writeToFile(GameActivity.this, playerDetails, PLAYER_DETAILS);
                        playerNameEditText.setText("");

                        Intent intent = new Intent(GameActivity.this, MainActivity.class);
                        gameOverDialog.dismiss();
                        startActivity(intent);
                    }
                });
                gameView.pause();
                if(!gameView.isHasExited()){
                    gameOverDialog.show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

    }


    private void startNewGame() {
        gameLayout = new FrameLayout(this);
        LinearLayout gameWidgets = new LinearLayout(this);
        gameWidgets.setGravity(Gravity.TOP | Gravity.RIGHT);

        pauseBtn = new ImageButton(this);
        pauseBtn.setImageDrawable(getResources().getDrawable(R.drawable.pause_drawable));
        pauseBtn.setBackground(null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(12, 12, 12, 12);

        pauseBtn.setLayoutParams(params);
        gameWidgets.requestLayout();

        gameWidgets.addView(pauseBtn);

        gameLayout.addView(gameView);
        gameLayout.addView(gameWidgets);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("STATE", "onPause: ");
        gameView.pause();
        if(!gameView.isHasExited()||!gameView.isGameOver()){
            pauseDialog.show();
        }
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

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("STATE", "onStart: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("STATE", "onRestart: ");
    }
}
