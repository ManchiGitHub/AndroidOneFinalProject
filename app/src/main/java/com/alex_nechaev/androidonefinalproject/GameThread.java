package com.alex_nechaev.androidonefinalproject;

import android.annotation.SuppressLint;
import android.graphics.Canvas;

public class GameThread extends Thread{

    private GameView gameView;
    private boolean isRunning = false;

    public GameThread(GameView gameView) {
        this.gameView = gameView;
    }

    public void setRunning(boolean running) {
        this.isRunning = running;
    }

    public boolean getRunning() {
        return isRunning;
    }

    @SuppressLint("WrongCall")
    @Override
    public void run() {
        while(isRunning){
            Canvas canvas = null;
            try{
                canvas= gameView.getHolder().lockCanvas();
                synchronized (gameView.getHolder()){
                    gameView.addGameObjects();
                    gameView.onDraw(canvas);
                }
            }finally {
                if(canvas != null){
                    gameView.getHolder().unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}


