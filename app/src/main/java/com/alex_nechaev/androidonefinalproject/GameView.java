package com.alex_nechaev.androidonefinalproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder;
    private GameThread gameThread;
    private Player player;

    private List<GameObject> gameObjects;
    private Random random;

    private float playerYPosition;
    private float playerXPosition;

    public GameView(Context context) {
        super(context);

        //Player values initiation
        final int playerSpeed = 15;
        playerYPosition = MainActivity.SCREEN_HEIGHT - (MainActivity.SCREEN_HEIGHT / 4);
        playerXPosition = MainActivity.SCREEN_WIDTH / 2;

        random = new Random();

        player = new Player(playerXPosition, playerYPosition, playerSpeed);

        gameObjects = new CopyOnWriteArrayList<GameObject>();

        gameThread = new GameThread(this);

        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int e = event.getActionMasked();

        switch (e) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                playerXPosition = event.getX();
                playerYPosition = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        synchronized (holder) {
            player.move(playerXPosition, playerYPosition);

            for (GameObject go : gameObjects) {
                go.move();
                player.isCollision(go);
            }
            if (canvas != null) {
                canvas.drawColor(Color.YELLOW);
                for (GameObject go : gameObjects) {
                    canvas.drawBitmap(go.getPlayerBitmap(), go.getXPosition(), go.getYPosition(), null);
                }
                canvas.drawBitmap(player.getPlayerBitmap(), player.getXPosition() - player.getSpriteWidth() / 2, player.getYPosition() - player.getSpriteHeight() / 2, null);
            }
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameThread.setRunning(true);
        gameThread.start();

        new Thread() {
            @Override
            public void run() {
                super.run();
                while (gameThread.isRunning()) {
                    gameObjects.add(EnemyFactory.createEnemy(eEnemyType.randomEnemy(), GameView.this));
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (gameThread.isRunning()) {
                    gameObjects.add(SupplyFactory.createSupply(eSupplyType.randomSupply(), GameView.this));
                    try {
                        Thread.sleep(25000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        gameThread.setRunning(false);
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
