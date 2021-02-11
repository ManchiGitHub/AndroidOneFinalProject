package com.alex_nechaev.androidonefinalproject;

public class UFO extends GameObject {

    boolean left = true, right = false;
    int moves = 0;

    public UFO(float xPosition, float yPosition, int speed) {
        super(Bitmaps.UFOImg, xPosition, yPosition, speed);
    }

    @Override
    public void move() {
        if(moves % 30 == 0){
            moves =0;
            left = right;
            right = !left;
        }
        if(left){
            if(getLeftBorder() > 0)
                setXPosition(getXPosition()-30);
        }
        if(right){
            if(getRightBorder() < GameActivity.SCREEN_WIDTH)
                setXPosition(getXPosition()+30);
        }
        setYPosition(getYPosition()+speed);
        moves++;

        super.move();
    }
}
