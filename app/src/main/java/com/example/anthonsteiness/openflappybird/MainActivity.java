package com.example.anthonsteiness.openflappybird;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class MainActivity extends Activity {

    // Out object to handle the view
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get a Display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();

        // Load the resolution into a point object
        Point resolution = new Point();
        display.getSize(resolution);

        // Stores the screen resolution in Constants class.
        Constants.SCREEN_WIDTH = resolution.x;
        Constants.SCREEN_HEIGHT = resolution.y;

        // Initialize gameView and set it as the view
        gameView = new GameView(this, resolution.x, resolution.y);
        setContentView(gameView);
    }

    // If the Activity is paused make sure to pause our thread
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    // If the Activity is resumed make sure to resume our thread
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
}