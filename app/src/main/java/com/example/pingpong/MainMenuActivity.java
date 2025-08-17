package com.example.pingpong;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button startButton = findViewById(R.id.startButton);
        Button optionsButton = findViewById(R.id.optionsButton);
        Button exitButton = findViewById(R.id.exitButton);
        Button infoButton = findViewById(R.id.infoButton);

        startButton.setOnClickListener(v -> {
            Intent intentStart = new Intent(MainMenuActivity.this, MainActivity.class);
            startActivity(intentStart);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        optionsButton.setOnClickListener(v -> {
            Intent intentOptions = new Intent(MainMenuActivity.this, OptionsActivity.class);
            startActivity(intentOptions);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        infoButton.setOnClickListener(v ->{
            Intent intentInfo = new Intent(MainMenuActivity.this, InfoActivity.class);
            startActivity(intentInfo);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        exitButton.setOnClickListener(v -> {
            finish(); // Close the app
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }
}
