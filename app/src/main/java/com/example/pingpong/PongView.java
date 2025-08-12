package com.example.pingpong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class PongView extends SurfaceView implements Runnable {
    private Thread gameThread;
    boolean isPlaying;
    private Paint paint;
    private SurfaceHolder surfaceHolder;
    private Paddle paddle;
    private Ball ball;
    private int score =0;
    private int level = 1;

    public PongView(Context context) {
        // Initialize the PongView, SurfaceHolder, Paint, etc.
        super(context);
        surfaceHolder = getHolder();
        paint = new Paint();

    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            control();
        }
    }
    private void update() {
        if(ball == null || paddle == null) {
            return; // Ensure paddle and ball are initialized
        }
        ball.update();
        if(ball.getRect().intersect(paddle.getRectF())) {
            ball.reverseY();
            float ballSize = ball.getBallSize();
            ball.setPosition(ball.getRect().left,paddle.getRectF().top - ballSize);
            score++;
            ball.increaseSpeed();
            if (score % 5 == 0) { // Increase level every 5 points
                level++;
                //ball.increaseSpeed();
                // You can add logic to increase difficulty here
            }

        }
        if (ball.getY() > getHeight()) {
            // Ball is out of bounds, reset position
            ball.resetPosition();
            score = 0; // Reset score on miss
            level = 1; // Reset level on miss
        }
    }

    private void draw(){
        if (surfaceHolder.getSurface().isValid()) {
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            paint.setColor(Color.WHITE);
            paint.setTextSize(50);
            canvas.drawText("Score "+ score + "Level :"+ level,50,50, paint);
            if( paddle != null) {
                paddle.draw(canvas, paint);
            }
            if( ball != null) {
                ball.draw(canvas, paint);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            Thread.sleep(17); // Roughly 60 FPS
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause(){
        isPlaying = false;
        try{
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume(){
        isPlaying = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Handle touch events to move the paddle
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            paddle.setX(event.getX());
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // Initialize paddle with new dimensions
        if (paddle == null) {
            paddle = new Paddle(w, h);
        }
        if( ball == null) {
            ball = new Ball(w, h);
        }
    }
}
