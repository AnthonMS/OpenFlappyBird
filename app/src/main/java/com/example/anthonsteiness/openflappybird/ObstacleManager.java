package com.example.anthonsteiness.openflappybird;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import java.util.ArrayList;

/**
 * Created by Anthon Steiness on 13-04-2017.
 */

public class ObstacleManager implements GameObject
{
    // Higher index = lower on screen = higher y value
    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;

    private int score;

    private long startTime;
    private long initTime;

    private Bitmap bmp;
//    static int playerHeight;
//    static int playerWidth;

    public ObstacleManager(Bitmap bmp, int playerGap, int obstacleGap, int obstacleHeight, int color)
    {
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        this.bmp = bmp;
//        playerHeight = bmp.getHeight() / 2;
//        playerWidth = bmp.getWidth() / 2;

        startTime = initTime = System.currentTimeMillis();

        obstacles = new ArrayList<>();

        populateObstacles();
    }

    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color)
    {
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        startTime = initTime = System.currentTimeMillis();

        obstacles = new ArrayList<>();

        populateObstacles();
    }

    public boolean playerCollide(RunningPlayer player)
    {
        for (Obstacle ob : obstacles)
        {
            if (ob.playerCollide(player))
            {
                return true;
            }
        }
        return false;
    }


    public void populateObstacles()
    {
        //if (Constants.PAUSED == false)
        //{
            int currY = -5 * Constants.SCREEN_HEIGHT / 4;

            // while the y value of the bottom of the rectangle
            // is less than 0 (hasn't gone on to the screen yet.
            // then keep generating obstacles
            while (currY < 0)
            {
                // We wanna generate random placements for the player- & obstacleGap
                // Reason to substract playerGap, if we do whole screen width
                // It could generate a playerGap starting on screen going off screen.
                // If changing to sideWays, change SCREEN_WIDTH to ...HEIGHT
                int xStart = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
                obstacles.add(new Obstacle(bmp, obstacleHeight, color, xStart, currY, playerGap));
                currY += obstacleHeight + obstacleGap;
            }
        //}
    }

    @Override
    public void draw(Canvas canvas)
    {
        for(Obstacle ob : obstacles)
        {
            ob.draw(canvas);
        }

        Paint paint = new Paint();
        paint.setTextSize(75);
        paint.setColor(Color.BLACK);
        paint.setTypeface(Constants.TYPEFACE);
        //paint.setTypeface(Typeface.create("Bauhaus 93" , Typeface.BOLD));

        float px = Constants.SCREEN_WIDTH / 4.0f;
        float py = Constants.SCREEN_HEIGHT / 2.0f;

        canvas.rotate(270, px, py);
        canvas.drawText("Current: " + score, 25, 150 + paint.descent() - paint.ascent(), paint);
        canvas.drawText("Highscore: " + Constants.HIGHSCORE, 25, 75 + paint.descent() - paint.ascent(), paint);

        //canvas.rotate(90, px, py);
//        for (Obstacle ob : obstacles)
//        {
//           Rect rect = ob.getRectangle();
//
//            canvas.drawBitmap(bmp, rect, rect, null);
//        }
    }

    @Override
    public void update()
    {
        //if (Constants.PAUSED == false)
        //{
            int elapsedTime = (int) (System.currentTimeMillis() - startTime);
            startTime = System.currentTimeMillis();
            // 10 seconds to move down whole screen
            float speed = Constants.SCREEN_HEIGHT / (2500.0f);
            for (Obstacle ob : obstacles)
            {
                ob.incrementY(speed * elapsedTime);
            }

            if (obstacles.get(obstacles.size() - 1).getRectangle().top >= Constants.SCREEN_HEIGHT)
            {
                int xStart = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
                obstacles.add(0, new Obstacle(bmp, obstacleHeight, color, xStart, obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap, playerGap));
                obstacles.remove(obstacles.size() - 1);
                score++;
                if (score > Constants.HIGHSCORE)
                {
                    Constants.HIGHSCORE = score;
                }
            }
        //}
    }
}
