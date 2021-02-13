package com.alex_nechaev.androidonefinalproject;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private final int MAX_HEARTS = 3;
    private final int BACKGROUND_SPEED = 40;

    Paint alphaPaint;
    Paint scorePaint;
    private int yTransition;

    private Dialog gameOverDialog;

    private volatile int score ;
    double deltaScore;

    long enemyTimer, heartTimer, shieldTimer, onShieldTimer, explosionTimer, coinTimer, bulletTimer;
    private Random random;

    private SurfaceHolder holder;
    private GameListener gameListenerDialogBox;

    private GameThread gameThread;
    private boolean isPauseButtonPressed;
    private boolean isGameOver;
    private boolean hasExited;

    private Explosions explosions;
    private float playerYPosition;

    private float playerXPosition;
    private Player player;
    private List<GameObject> enemyObjects;

    private List<GameObject> heartObjects;
    private List<GameObject> shieldObjects;
    private List<GameObject> coinObjects;
    private List<GameObject> bullets;
    private int heartIndex = MAX_HEARTS;

    public GameView(Context context) {
        super(context);

        enemyTimer = heartTimer = shieldTimer = coinTimer = System.currentTimeMillis();
        isPauseButtonPressed = false;
        isGameOver = false;
        hasExited = false;

        //Player values initiation
        playerYPosition = GameActivity.SCREEN_HEIGHT - (GameActivity.SCREEN_HEIGHT / 4);
        playerXPosition = GameActivity.SCREEN_WIDTH / 2;

        player = new Player(playerXPosition, playerYPosition, 0);
        explosions = new Explosions(context);
        alphaPaint = new Paint();
        alphaPaint.setAlpha(100);

        scorePaint = new Paint();
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(50);

        enemyObjects = new CopyOnWriteArrayList<GameObject>();
        heartObjects = new CopyOnWriteArrayList<GameObject>();
        shieldObjects = new CopyOnWriteArrayList<GameObject>();
        coinObjects = new CopyOnWriteArrayList<GameObject>();
        bullets = new CopyOnWriteArrayList<GameObject>();


        gameOverDialog = new Dialog(context, R.style.Theme_AppCompat_Light_Dialog_Alert);
        gameOverDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        gameOverDialog.setCancelable(false);
        gameOverDialog.setContentView(R.layout.gameover_menu);
        gameOverDialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;

        TextView playerScoreTextView = gameOverDialog.findViewById(R.id.player_score_text_vew);
        playerScoreTextView.setText("Your score is: "+score);
        EditText playerNameEditText = gameOverDialog.findViewById(R.id.player_name_edit_text);
        ImageButton submitBtn = gameOverDialog.findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "SUBMIT PRESSED", Toast.LENGTH_SHORT).show();
            }
        });


        random = new Random();
        gameListenerDialogBox=((GameListener)context);

        gameThread = new GameThread(this);

        holder = getHolder();
        holder.addCallback(this);
    }

    public void setPauseButtonPressed(boolean pauseButtonPressed) {
        isPauseButtonPressed = pauseButtonPressed;
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
                if (event.getX() - (player.getBitmapLevel1().getWidth() / 2) + 50 > 0 && event.getX() + (player.getBitmapLevel1().getWidth() / 2) - 50 < GameActivity.SCREEN_WIDTH) {
                    playerXPosition = event.getX();
                }
                if (event.getY() - (player.getBitmapLevel1().getWidth() / 2) > 0 && event.getY() + player.getBitmapLevel1().getWidth() / 2 < GameActivity.SCREEN_HEIGHT) {
                    playerYPosition = event.getY();
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        synchronized (holder) {

            if (!isPauseButtonPressed) {
                player.move(playerXPosition, playerYPosition);
                for (GameObject bullet : bullets) {
                    bullet.move();
                    if (bullet.getYPosition() + bullet.getSpriteHeight() < 0) {
                        bullets.remove(bullet);
                    }
                }
                for (GameObject ho : heartObjects) {
                    ho.move();
                    if (ho.getYPosition() > GameActivity.SCREEN_HEIGHT) {
                        heartObjects.remove(ho);
                    }
                    if (player.isCollision(ho)) {
                        addHeart();
                        heartObjects.remove(ho);
                    }
                }

                for (GameObject co : coinObjects) {
                    co.move();
                    if (co.getYPosition() > GameActivity.SCREEN_HEIGHT) {
                        coinObjects.remove(co);
                    }
                    if (player.isCollision(co)) {
                        coinObjects.remove(co);
                        score += 1000;
                    }
                }

                for (GameObject so : shieldObjects) {
                    so.move();
                    if (so.getYPosition() > GameActivity.SCREEN_HEIGHT) {
                        shieldObjects.remove(so);
                    }
                    if (player.isCollision(so)) {
                        player.setHasShield(true);
                        shieldObjects.remove(so);
                    }
                }

                for (GameObject eo : enemyObjects) {
                    eo.move();
                    if (eo.getYPosition() > GameActivity.SCREEN_HEIGHT) {
                        enemyObjects.remove(eo);
                    }
                    if (player.isCollision(eo)) {
                        if (player.hasShield()) {
                            player.setHasShield(false);
                        } else {
                            player.setHasExploded(true);
                            removeHeart();
                        }
                        enemyObjects.remove(eo);
                    }

                    for (GameObject bullet : bullets) {
                        if (bullet.isCollision(eo)) {
                            explosions.setxPosition(eo.xPosition);
                            explosions.setyPosition(eo.yPosition);
                            explosions.setExplode(true);
                            bullets.remove(bullet);
                            enemyObjects.remove(eo);
                            score += 300;
                        }
                    }
                }
            }

            if (canvas != null) {

                drawBackground(canvas);

                for (GameObject ho : heartObjects) {
                    ho.draw(canvas);
                }
                for (GameObject co : coinObjects) {
                    co.draw(canvas);
                }
                for (GameObject so : shieldObjects) {
                    so.draw(canvas);
                }
                for (GameObject eo : enemyObjects) {
                    eo.draw(canvas);
                }
                for (GameObject bullet : bullets) {
                    bullet.draw(canvas);
                }
                if (explosions.isExplode()) {
                    explosions.draw(canvas);
                }
                if(!isGameOver){
                    player.draw(canvas);
                }
                drawHeart(canvas);
                drawScore(canvas);
            }
        }
    }

    private void drawScore(Canvas canvas) {
        canvas.drawText("SCORE: " + score, Bitmaps.heartImg.getWidth() * 5, 100, scorePaint);
    }

    private void drawBackground(Canvas canvas) {

        canvas.drawBitmap(Bitmaps.galaxyBackgroundImg, 0, 0, null);
        canvas.drawBitmap(Bitmaps.starsImg, 0, yTransition, alphaPaint);
        canvas.drawBitmap(Bitmaps.starsImg, 0, yTransition - GameActivity.SCREEN_HEIGHT, alphaPaint);
        yTransition += BACKGROUND_SPEED;
        if (yTransition > GameActivity.SCREEN_HEIGHT) {
            yTransition = 0;
        }
    }

    private void removeHeart() {
        if (heartIndex == 1) {
            this.heartIndex--;
            isGameOver = true;
        } else {
            if (heartIndex != 0)
                this.heartIndex--;
        }
    }

    private void drawHeart(Canvas canvas) {
        int top = 50;
        if (this.heartIndex == 3) {
            canvas.drawBitmap(Bitmaps.heartImg, 20, top, null);
            canvas.drawBitmap(Bitmaps.heartImg, 20 + (Bitmaps.heartImg.getWidth()) + 5, top, null);
            canvas.drawBitmap(Bitmaps.heartImg, 20 + (Bitmaps.heartImg.getWidth() * 2) + 10, top, null);
        } else if (this.heartIndex == 2) {
            canvas.drawBitmap(Bitmaps.heartImg, 20, top, null);
            canvas.drawBitmap(Bitmaps.heartImg, 20 + (Bitmaps.heartImg.getWidth()) + 5, top, null);
        } else if (this.heartIndex == 1) {
            canvas.drawBitmap(Bitmaps.heartImg, 20, top, null);
        }

    }

    private void addHeart() {
        if (heartIndex == 3) {
            score += 2000;
        } else if (heartIndex != 3) {
            heartIndex++;
        }
    }

    public int getScore() {
        return score;
    }

    private void gameOver() {
        enemyObjects.clear();
        heartObjects.clear();
        shieldObjects.clear();
        coinObjects.clear();
        bullets.clear();
        gameListenerDialogBox.onGameOver();
    }

    public void addGameObjects() {
        if (!isPauseButtonPressed) {
            addEnemyObject();
            addHeartObject();
            addShieldObject();
            addCoinObject();
            checkShieldTimeOnPlayer();
            checkExplosionTimeOnPlayer();
            addBullet();
            addScore();
        }
        if(isGameOver){
            gameOver();
        }
    }

    private void checkExplosionTimeOnPlayer() {
        if (player.hasExploded()) {
            explosionTimer++;
        } else {
            explosionTimer = 0;
        }
        if (explosionTimer % 5 == 0) {
            explosionTimer = 0;
            player.setHasExploded(false);
        }
    }

    private void addScore() {
        if(!isGameOver)
            score++;
    }

    private void addBullet() {
        long currentHeartTimer = System.currentTimeMillis();
        if (score < 120000) {
            this.deltaScore = score / 300;
        }
        if (currentHeartTimer - bulletTimer > 900 - deltaScore && !isPauseButtonPressed) {
            if(score <=10000) {
                bullets.add(new Bullet(Bitmaps.bulletLv1Img, playerXPosition - Bitmaps.bulletLv1Img.getWidth() / 2, playerYPosition));
            }else if(score <=20000){
                bullets.add(new Bullet(Bitmaps.bulletLv2Img, playerXPosition - Bitmaps.bulletLv2Img.getWidth() / 2, playerYPosition));
            }else{
                bullets.add(new Bullet(Bitmaps.bulletLv3Img, playerXPosition - Bitmaps.bulletLv3Img.getWidth() / 2, playerYPosition));
            }
            bulletTimer = System.currentTimeMillis();
        }
    }

    private void addHeartObject() {
        long currentHeartTimer = System.currentTimeMillis();
        int xPosition = random.nextInt(getWidth() - 100);
        int yPosition = getHeight() / 6 * (-1);
        if (currentHeartTimer - heartTimer > 32200 && !isPauseButtonPressed) {
            heartObjects.add(new Heart(xPosition, yPosition, (xPosition % 10) + 5));
            heartTimer = System.currentTimeMillis();
        }
    }

    private void addShieldObject() {
        long currentShieldTimer = System.currentTimeMillis();
        int xPosition = random.nextInt(getWidth() - 100);
        int yPosition = getHeight() / 6 * (-1);
        if (currentShieldTimer - shieldTimer > 47700 && !isPauseButtonPressed) {
            shieldObjects.add(new Shield(xPosition, yPosition, (xPosition % 10) + 5));
            shieldTimer = System.currentTimeMillis();
        }
    }

    private void addCoinObject() {
        long currentCoinTimer = System.currentTimeMillis();
        int xPosition = random.nextInt(getWidth() - 100);
        int yPosition = getHeight() / 6 * (-1);
        if (currentCoinTimer - coinTimer > 13100 && !isPauseButtonPressed) {
            coinObjects.add(new Coin(xPosition, yPosition, (xPosition % 10) + 5));
            coinTimer = System.currentTimeMillis();
        }
    }

    private void addEnemyObject() {
        long currentEnemyTimer = System.currentTimeMillis();
        if (currentEnemyTimer - enemyTimer > 1700 - this.deltaScore && !isPauseButtonPressed) {
            enemyObjects.add(EnemyFactory.createEnemy(eEnemyType.randomEnemy(), GameView.this, score));
            enemyTimer = System.currentTimeMillis();
        }
    }

    private void checkShieldTimeOnPlayer() {
        if (player.hasShield()) {
            onShieldTimer++;
        } else {
            onShieldTimer = 0;
        }
        if (onShieldTimer % 500 == 0) {
            onShieldTimer = 0;
            player.setHasShield(false);
        }
    }

    public void pause() {
        isPauseButtonPressed = true;
        try {
            if (gameThread.getRunning()) {
                gameThread.setRunning(false);
                gameThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        if (isPauseButtonPressed) {
            gameThread = new GameThread(this);
        }
    }

    public void resumeOnPause() {
        resume();
        surfaceCreated(getHolder());
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    public boolean isHasExited() {
        return hasExited;
    }

    public void setHasExited(boolean hasExited) {
        this.hasExited = hasExited;
    }
}
