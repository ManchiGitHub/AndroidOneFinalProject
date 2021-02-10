package com.alex_nechaev.androidonefinalproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private final int PLAYER_SPEED = 15;
    private final int MAX_HEARTS = 3;

    private SurfaceHolder holder;

    private volatile boolean isPauseButtonPressed;

    private GameThread gameThread;

    long enemyTimer, supplyTimer;

//    boolean isPause = false;

    private float playerYPosition;
    private float playerXPosition;
    private Player player;

    private List<GameObject> enemyObjects;
    private List<GameObject> supplyObjects;
    private boolean[] hearts = {true, true, true};
    private Random random;


    public GameView(Context context) {
        super(context);

        //Player values initiation
        enemyTimer = supplyTimer = System.currentTimeMillis();
        isPauseButtonPressed = false;

        playerYPosition = MainActivity.SCREEN_HEIGHT - (MainActivity.SCREEN_HEIGHT / 4);
        playerXPosition = MainActivity.SCREEN_WIDTH / 2;

        player = new Player(playerXPosition, playerYPosition, PLAYER_SPEED);

        enemyObjects = new CopyOnWriteArrayList<GameObject>();
        supplyObjects = new CopyOnWriteArrayList<GameObject>();

        random = new Random();

        gameThread = new GameThread(this);

        holder = getHolder();
        holder.addCallback(this);
    }

    public boolean isPauseButtonPressed() {
        return isPauseButtonPressed;
    }

    public void setPauseButtonPressed(boolean pauseButtonPressed) {
        isPauseButtonPressed = pauseButtonPressed;
    }

    public GameThread getGameThread() {
        return gameThread;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameThread.setRunning(true);
        gameThread.start();
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int e = event.getActionMasked();

        float fixedEventX = (event.getX() + 40);
        float fixedEventY = (event.getY() + 150);

        float expendLeftBorder = (player.getLeftBorder() - 100);
        float expendRightBorder = (player.getRightBorder() + 100);
        float expendTopBorder = (player.getTopBorder() - 100);
        float expendBottomBorder = (player.getBottomBorder() + 100);

        switch (e) {
            case MotionEvent.ACTION_DOWN:

                return (fixedEventX >= expendLeftBorder && fixedEventX <= expendRightBorder && fixedEventY >= expendTopBorder && fixedEventY <= expendBottomBorder);
            case MotionEvent.ACTION_MOVE:
                playerXPosition = event.getX();
                playerYPosition = event.getY();
                resumeOnPause();
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        synchronized (holder) {

            if(isPauseButtonPressed){
                if (canvas != null) {
                    canvas.drawColor(Color.YELLOW);

                    for (GameObject so : supplyObjects) {
                        so.draw(canvas);
                    }
                    for (GameObject eo : enemyObjects) {
                        eo.draw(canvas);
                    }
                    player.draw(canvas);
                }
            }else{
                player.move(playerXPosition, playerYPosition);

                for (GameObject so : supplyObjects) {
                    so.move();
                    if (player.isCollision(so)) {
                        if (so instanceof Heart) {
                            addHeart();
                        }

                        supplyObjects.remove(so);
                    }
                }
                for (GameObject eo : enemyObjects) {
                    eo.move();
                    if (player.isCollision(eo)) {
                        removeHeart();
                        enemyObjects.remove(eo);
                    }
                }

                if (canvas != null) {
                    canvas.drawColor(Color.YELLOW);

                    for (GameObject so : supplyObjects) {
                        so.draw(canvas);
                    }
                    for (GameObject eo : enemyObjects) {
                        eo.draw(canvas);
                    }
                    player.draw(canvas);
                }
            }


        }

    }


    private void removeHeart() {
    }

    private void addHeart() {

    }


    public void addGameObjects() {
        if (!isPauseButtonPressed) {
            addEnemyObject();
            addSupplyObject();
        }

    }

    private void addSupplyObject() {
        long currentSupplyTimer = System.currentTimeMillis();
        if (currentSupplyTimer - supplyTimer > 12000) {
            supplyObjects.add(SupplyFactory.createSupply(eSupplyType.randomSupply(), GameView.this));
            supplyTimer = System.currentTimeMillis();
        }
    }

    private void addEnemyObject() {
        long currentEnemyTimer = System.currentTimeMillis();
        if (currentEnemyTimer - enemyTimer > 2500) {
            enemyObjects.add(EnemyFactory.createEnemy(eEnemyType.randomEnemy(), GameView.this));
            enemyTimer = System.currentTimeMillis();
        }
    }

    public void pause() {
        try {
            if (gameThread.getRunning()) {
                Log.d("STATE", "onPause - GAME VIEW ");
                gameThread.setRunning(false);
                gameThread.join();
//                isPause = true;
                isPauseButtonPressed = true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        if (isPauseButtonPressed) {
            gameThread = new GameThread(this);
//            isPause = false;
            isPauseButtonPressed = false;
        }
    }

    public void resumeOnPause() {
        if (!isPauseButtonPressed) {
            resume();
            surfaceCreated(getHolder());
        }

    }


}
