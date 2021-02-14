package com.alex_nechaev.androidonefinalproject;

public class BlueAlien extends GameObject{

    private boolean left = true, right = false;
    private int moves = 0;

    public BlueAlien(float xPosition, float yPosition, int speed) {
        super(Bitmaps.blueAlienImg, xPosition, yPosition, speed);
    }

    @Override
    public void move() {
        if(moves % 50 == 0){
            moves =0;
            left = right;
            right = !left;
        }
        if(left){
            if(getLeftBorder() > 0)
                setXPosition(getXPosition()-7);
        }
        if(right){
            if(getRightBorder() < GameActivity.SCREEN_WIDTH)
                setXPosition(getXPosition()+7);
        }
        setYPosition(getYPosition()+speed);
        moves++;

        super.move();
    }
}