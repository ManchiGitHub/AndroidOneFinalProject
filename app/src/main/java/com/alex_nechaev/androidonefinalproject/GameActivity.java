package com.alex_nechaev.androidonefinalproject;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//This activity runs when the "PLAY GAME" button is pressed.
public class GameActivity extends AppCompatActivity {

    static int SCREEN_WIDTH, SCREEN_HEIGHT;
    GameView gameView;
    FrameLayout gameLayout;
    ImageButton pauseBtn;
    Dialog pauseDialog;

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

                ImageButton resumeBtn = pauseDialog.findViewById(R.id.resume_btn);
                ImageButton replayBtn = pauseDialog.findViewById(R.id.replay_btn);
                ImageButton exitBtn = pauseDialog.findViewById(R.id.exit_btn);
                pauseDialog.show();

                resumeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(GameActivity.this, "Resume", Toast.LENGTH_SHORT).show();
                        gameView.setPauseButtonPressed(false);
                        pauseDialog.dismiss();
                    }
                });

                replayBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(GameActivity.this, "Replay", Toast.LENGTH_SHORT).show();
                        gameView.setPauseButtonPressed(false);
                        pauseDialog.dismiss();
                    }
                });

                exitBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Back to Main Menu
                        Dialog exitDialog;
                        exitDialog = new Dialog(GameActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                        exitDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        exitDialog.setCancelable(false);
                        exitDialog.setContentView(R.layout.exit_menu);
                        //exitDialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;

                        exitDialog.show();
                        pauseDialog.dismiss();

                        ImageButton applyBtn = exitDialog.findViewById(R.id.exit_yes_btn);
                        applyBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(GameActivity.this, "Exit", Toast.LENGTH_SHORT).show();
                                gameView.setPauseButtonPressed(true);
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
            }
        });
        setContentView(gameLayout);
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
        pauseDialog.show();
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
