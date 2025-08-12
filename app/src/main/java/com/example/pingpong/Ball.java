package com.example.pingpong;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class Ball {
    private RectF rectF;
    private float xVelocity;
    private float yVelocity;
    private float ballSize;
    private int screenX;
    private int screenY;

    public Ball (int screenX, int screenY){
        this.screenX=screenX;
        this.screenY=screenY;

        ballSize = 30;

        float startX = (screenX / 2) - (ballSize / 2);
        float startY = (screenY / 2) - (ballSize / 2);
        rectF = new RectF(startX, startY, startX + ballSize, startY + ballSize);
        xVelocity = 8;
        yVelocity = -10;
    }

    public void update(){
        rectF.offset(xVelocity, yVelocity);
        if (rectF.left < 0 || rectF.right > screenX){
            xVelocity = -xVelocity;
        }
        if (rectF.top < 0){
            yVelocity = -yVelocity;
        }
    }

    public void reverseY() {
        yVelocity = -yVelocity;
    }

    public void increaseSpeed(){
        xVelocity *= 1.1f;
        yVelocity *= 1.1f;
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawOval(rectF, paint);
    }

    public RectF getRect(){
        return rectF;
    }

    public float getY(){
        return rectF.top;
    }

    public void resetPosition() {
        rectF.left = (screenX / 2) - (ballSize / 2);
        rectF.top = (screenY / 2) - (ballSize / 2);
        rectF.right = rectF.left + ballSize;
        rectF.bottom = rectF.top + ballSize;
        xVelocity = 8;
        yVelocity = -10;
    }

    public void setPosition(float left, float top) {
        rectF.left = left;
        rectF.right = left + ballSize;
        rectF.top = top;
        rectF.bottom = top + ballSize;
    }

    public float getBallSize() {
        return ballSize;
    }

}
