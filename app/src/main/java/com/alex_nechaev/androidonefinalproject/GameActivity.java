package com.alex_nechaev.androidonefinalproject;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//This activity runs when the "PLAY GAME" button is pressed.
public class GameActivity extends AppCompatActivity implements GameListener {

    static int SCREEN_WIDTH, SCREEN_HEIGHT;
    GameView gameView;
    FrameLayout gameLayout;
    ImageButton pauseBtn;
    Dialog pauseDialog;
    public static String PLAYER_DETAILS = "playerDetails";
    SharedPreferences sp;
    MediaPlayer mp;
    boolean isMute;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = getSharedPreferences(MainActivity.GAME_KEY, MODE_PRIVATE);
        isMute = sp.getBoolean(MainActivity.IS_MUTE_KEY, false);

        if (mp == null) {
            mp = MediaPlayer.create(this, R.raw.game_activity_music);
            mp.setLooping(true); // Set looping
            mp.setVolume(0.6f, 0.6f);
        }

        if (!isMute) {
            mp.start();
        }

        //REMOVES THE NOTIFICATION BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //GETS THE SCREEN SIZE
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        SCREEN_HEIGHT = displayMetrics.heightPixels;
        SCREEN_WIDTH = displayMetrics.widthPixels;

        //LOADS BITMAPS TO BITMAPS CLASS
        Bitmaps bitmaps = new Bitmaps(getResources());

        //LOADS GAME VIEW AND LOADS THE GAME WIDGET IN IT.
        gameView = new GameView(GameActivity.this);
        startNewGame();

        //PAUSE DIALOG INIT
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

        //RESUME THE GAME
        Button resumeBtn = pauseDialog.findViewById(R.id.resume_btn);
        resumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.setPauseButtonPressed(false);
                pauseDialog.dismiss();
            }
        });

        //REPLAY THE GAME
        Button replayBtn = pauseDialog.findViewById(R.id.replay_btn);
        replayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.setPauseButtonPressed(false);
                gameView.replayGame();
                pauseDialog.dismiss();
            }
        });

        //EXIT THE GAME AND BACK TO THE MAIN MENU
        Button exitBtn = pauseDialog.findViewById(R.id.exit_btn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog exitDialog;
                exitDialog = new Dialog(GameActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                exitDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                exitDialog.setCancelable(false);
                exitDialog.setContentView(R.layout.exit_menu);
                if(!gameView.isHasExited()){
                    exitDialog.show();
                }
                pauseDialog.dismiss();

                Button applyBtn = exitDialog.findViewById(R.id.exit_yes_btn);
                applyBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gameView.setPauseButtonPressed(true);
                        gameView.setGameOver(true);
                        Intent intent = new Intent(GameActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

                Button cancelBtn = exitDialog.findViewById(R.id.exit_no_btn);
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gameView.setPauseButtonPressed(false);
                        exitDialog.dismiss();
                    }
                });
            }
        });

        ImageView volumeIv = pauseDialog.findViewById(R.id.volume_iv);
        if (isMute) {
            volumeIv.setImageResource(R.drawable.volume_off);
        }
        else {
            volumeIv.setImageResource(R.drawable.volume_on);
        }

        volumeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMute = !isMute;

                if (isMute) {
                    sp.edit().putBoolean(MainActivity.IS_MUTE_KEY, true).commit();
                    volumeIv.setImageResource(R.drawable.volume_off);
                    mp.pause();
                }
                else {
                    sp.edit().putBoolean(MainActivity.IS_MUTE_KEY, false).commit();
                    volumeIv.setImageResource(R.drawable.volume_on);
                    mp.start();
                }
            }
        });

        setContentView(gameLayout);
    }

    //GAME OVER DIALOG
    @Override
    public void onGameOver() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(gameView.isGameOver()) {
                    gameView.pause();
                    if(!gameView.isHasExited()) {
                        gameView.getGameOverDialog().show();
                    }
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
        pauseBtn.setImageDrawable(getResources().getDrawable(R.drawable.btn_pause_selector));
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
        if(!gameView.isGameOver()){
            pauseDialog.show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

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
        gameView.resume();
    }
}
