package com.alex_nechaev.androidonefinalproject;

public class Shield extends GameObject implements SupplyElement{
    public Shield(float xPosition, float yPosition, int speed) {
        super(Bitmaps.shieldImg, xPosition, yPosition, speed);
    }

    @Override
    public void move() {
        setYPosition(getYPosition()+speed);
        super.move();
    }

    @Override
    public void activateSupplyElement() {

    }
}
