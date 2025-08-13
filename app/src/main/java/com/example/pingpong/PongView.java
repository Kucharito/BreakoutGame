package com.example.pingpong;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class PongView extends SurfaceView implements Runnable {
    private Thread gameThread;
    boolean isPlaying;
    private Paint paint;
    private SurfaceHolder surfaceHolder;
    private Paddle paddle;
    private Ball ball;
    private int score =0;
    private int level = 1;
    ArrayList<Brick> bricks;

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

        }
        if (ball.getY() > getHeight()) {
            // Ball is out of bounds, reset position
            ball.resetPosition();
            score = 0; // Reset score on miss
            level = 1; // Reset level on miss
            generateBricks(8 * level, getWidth()); // Regenerate bricks for new level
        }
        // Check for collisions with bricks
        if (bricks != null){
            for (Brick brick : bricks){
                if(brick.isVisible() && ball.getRect().intersect(brick.getRectF())){
                    brick.setInvisible();
                    RectF ballRect = ball.getRect();
                    RectF brickRect = brick.getRectF();

                    float overlapLeft = ballRect.right - brickRect.left;
                    float overlapRight = brickRect.right - ballRect.left;
                    float overlapTop = ballRect.bottom - brickRect.top;
                    float overlapBottom = brickRect.bottom - ballRect.top;

                    if (overlapLeft < overlapRight && overlapLeft < overlapTop && overlapLeft < overlapBottom) {
                        // Ball hit the left side of the brick
                        ball.reverseX();
                        ball.setPosition(brickRect.left - ball.getBallSize(), ballRect.top);
                    } else if (overlapRight < overlapLeft && overlapRight < overlapTop && overlapRight < overlapBottom) {
                        // Ball hit the right side of the brick
                        ball.reverseX();
                        ball.setPosition(brickRect.right, ballRect.top);
                    } else if (overlapTop < overlapLeft && overlapTop < overlapRight && overlapTop < overlapBottom) {
                        // Ball hit the top side of the brick
                        ball.reverseY();
                        ball.setPosition(ballRect.left, brickRect.top - ball.getBallSize());
                    } else {
                        // Ball hit the bottom side of the brick
                        ball.reverseY();
                        ball.setPosition(ballRect.left, brickRect.bottom);
                    }
                    // Increase score for hitting a brick

                    score = score +10;
                    break;
                }
            }
        }
        if (bricks != null && allBricksDestroyed()){
            level++;
            float baseSpeed = 1.1f + (level -1)* 0.4f;
            ball.setSpeedBall(baseSpeed*8, -baseSpeed*10);
            generateBricks(5 * level, getWidth()); // Increase number of bricks with level
        }
    }

    private boolean allBricksDestroyed()
    {
        for (Brick brick : bricks) {
            if (brick.isVisible()) {
                return false; // At least one brick is still visible
            }
        }
        return true;
    }

    private void draw(){
        if (surfaceHolder.getSurface().isValid()) {
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            paint.setColor(Color.WHITE);
            paint.setTextSize(50);
            canvas.drawText("Score "+ score + "Level :"+ level,50,50, paint);

            SharedPreferences sharedPreferences = getContext().getSharedPreferences(OptionsActivity.PREFS_NAME, Context.MODE_PRIVATE);
            String paddleColor = sharedPreferences.getString(OptionsActivity.PADDLE_COLOR_KEY, "white");
            String brickColor = sharedPreferences.getString(OptionsActivity.BRICK_COLOR_KEY, "white");
            String ballColor = sharedPreferences.getString(OptionsActivity.BALL_COLOR_KEY, "white");

            if( paddle != null) {
                paint.setColor(paddleColor.equals("red") ? Color.RED :
                        paddleColor.equals("green") ? Color.GREEN :
                        paddleColor.equals("blue") ? Color.BLUE : Color.WHITE);
                paddle.draw(canvas, paint);
            }
            if( ball != null) {
                paint.setColor(ballColor.equals("red") ? Color.RED :
                        ballColor.equals("white") ? Color.WHITE : Color.GRAY);
                ball.draw(canvas, paint);
            }
            if(bricks != null){
                paint.setColor(brickColor.equals("red") ? Color.RED :
                        brickColor.equals("white") ? Color.WHITE : Color.GRAY);
                for (Brick brick : bricks) {
                    if (brick.isVisible()) {
                        brick.draw(canvas, paint);
                    }
                }
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            Thread.sleep(17);
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

    private void generateBricks(int count, int screenW){
        bricks.clear();
        float brickHeight = 50;
        float verticalOffset = 100;
        //float horizontalOffset = getWidth()/2;
        float brickWidth = screenW / 8;

        int bricksPreRow = (int)(screenW / brickWidth);
        int created = 0;
        for (int row = 0; row < count; row++) {
            for (int col = 0; col < bricksPreRow && created < count; col++) {
                float left = col * brickWidth + 5; //+ horizontalOffset;
                float top = row * (brickHeight + 10) + verticalOffset;
                bricks.add(new Brick(left, top, brickHeight - 10, brickWidth - 5));
                created++;
            }
        }
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
        if (paddle == null) {
            paddle = new Paddle(w, h);
        }
        if( ball == null) {
            ball = new Ball(w, h);
        }
        if(bricks == null) {
            bricks = new ArrayList<>();
            generateBricks(8, w);
        }
    }
}
