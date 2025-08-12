package com.example.pingpong;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class Brick {
    private RectF rectF;
    private boolean isVisible;

    public Brick(float left, float top, float height, float width){
        rectF = new RectF(left, top, left + width, top + height);
        isVisible = true;
    }

    public void draw(Canvas canvas, Paint paint){
        if (isVisible) {
            canvas.drawRect(rectF, paint);
        }
    }

    public RectF getRectF() {
        return rectF;
    }
    public boolean isVisible() {
        return isVisible;
    }
    public void setInvisible(){
        isVisible = false;
    }
}
