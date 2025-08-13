package com.example.pingpong;

import android.os.Bundle;
import android.widget.Button;

public class OptionsActivity extends MainMenuActivity {

    // This class can be used to implement options like sound settings, difficulty levels, etc.
    // Currently, it extends MainMenuActivity and does not add any new functionality.
    // You can add methods and UI elements to customize game options.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            finish(); // Close this activity and return to the previous one
        });

        // Initialize UI components for options here
        // For example, sliders for sound volume, checkboxes for enabling/disabling features, etc.
    }
}
