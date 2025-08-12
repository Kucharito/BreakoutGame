package com.example.pingpong;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class Paddle {
    private RectF rectF;
    private float width;
    private float height;
    private int screenX;
    private int screenY;

    public Paddle(int screenX, int screenY){
        this.screenX = screenX;
        this.screenY = screenY;

        width = 200;
        height = 20;

        float left = (screenX / 2) - (width / 2);
        float top = screenY - height - 50; // opravené
        float right = (screenX / 2) + (width / 2);
        float bottom = screenY - 50; // opravené
        rectF = new RectF(left, top, right, bottom);
    }

    public void setX(float x){
        float half = width / 2;
        rectF.left = x - half;
        rectF.right = x + half;

        if(rectF.left < 0){
            rectF.left = 0;
            rectF.right = width;
        } else if(rectF.right > screenX){
            rectF.right = screenX;
            rectF.left = screenX - width;
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawRect(rectF, paint);
    }

    public RectF getRectF() {
        return rectF;
    }
}

