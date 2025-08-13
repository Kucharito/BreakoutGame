package com.example.pingpong;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class OptionsActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "PongPrefs";
    public static final String PADDLE_COLOR_KEY = "paddle_color";
    public static final String BRICK_COLOR_KEY = "brick_color";
    public static final String BALL_COLOR_KEY = "ball_color";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Button paddleRed = findViewById(R.id.paddleRed);
        paddleRed.setOnClickListener(v -> {
            editor.putString(PADDLE_COLOR_KEY, "red");
            editor.apply();
        });

        Button paddleBlue = findViewById(R.id.paddleBlue);
        paddleBlue.setOnClickListener(v -> {
            editor.putString(PADDLE_COLOR_KEY, "blue");
            editor.apply();
        });

        Button paddleGreen = findViewById(R.id.paddleGreen);
        paddleGreen.setOnClickListener(v -> {
            editor.putString(PADDLE_COLOR_KEY, "green");
            editor.apply();
        });

        Button bricksRed = findViewById(R.id.brickRed);
        bricksRed.setOnClickListener(v -> {
            editor.putString(BRICK_COLOR_KEY, "red");
            editor.apply();
        });

        Button brickWhite = findViewById(R.id.brickWhite);
        brickWhite.setOnClickListener(v -> {
            editor.putString(BRICK_COLOR_KEY, "white");
            editor.apply();
        });

        Button ballRed = findViewById(R.id.ballRed);
        ballRed.setOnClickListener(v -> {
            editor.putString(BALL_COLOR_KEY, "red");
            editor.apply();
        });

        Button ballWhite = findViewById(R.id.ballWhite);
        ballWhite.setOnClickListener(v -> {
            editor.putString(BALL_COLOR_KEY, "white");
            editor.apply();
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            finish(); // Close this activity and return to the previous one
        });

        // Initialize UI components for options here
        // For example, sliders for sound volume, checkboxes for enabling/disabling features, etc.
    }
}
