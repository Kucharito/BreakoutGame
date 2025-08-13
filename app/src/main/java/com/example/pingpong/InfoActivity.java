package com.example.pingpong;

import android.os.Bundle;
import android.widget.Button;

public class InfoActivity extends MainMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // Initialize UI components and set up event listeners if needed
        // For example, you can set up a back button to return to the main menu
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            finish(); // Close this activity and return to the previous one
        });
    }
}
