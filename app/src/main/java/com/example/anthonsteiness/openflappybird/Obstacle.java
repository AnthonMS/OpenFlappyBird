package com.example.anthonsteiness.openflappybird;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;


/**
 * Created by Anthon Steiness on 13-04-2017.
 */

public class Obstacle implements GameObject
{
    private Rect rectangle;
    private Rect rectangle2;
    private int color;

    private Bitmap bmp;
    static int playerHeight;
    static int playerWidth;

    public Obstacle(Bitmap bmp, int obstacleHeight, int color, int startX, int startY, int playerGap)
    {
        this.color = color;
        // Left, Top, Right, Bottom

        // Left Rectangle
        rectangle = new Rect(0, startY, startX, startY + obstacleHeight);
        // Right rectangle
        rectangle2 = new Rect(startX + playerGap, startY, Constants.SCREEN_WIDTH, startY + obstacleHeight);

        this.bmp = bmp;
        playerHeight = bmp.getHeight() / 2;
        playerWidth = bmp.getWidth() / 2;

    }


    public Obstacle(int obstacleHeight, int color, int startX, int startY, int playerGap)
    {
        this.color = color;
        // Left, Top, Right, Bottom

        // Left Rectangle
        rectangle = new Rect(0, startY, startX, startY + obstacleHeight);
        // Right rectangle
        rectangle2 = new Rect(startX + playerGap, startY, Constants.SCREEN_WIDTH, startY + obstacleHeight);

    }

    public boolean playerCollide(RunningPlayer player)
    {
        Bitmap bmp = player.getBitmap();
        int x = player.getX();
        int y = player.getY();
        Rect playerRect = new Rect(x, y, x + bmp.getWidth(), y + bmp.getHeight());

        return Rect.intersects(rectangle, playerRect) || Rect.intersects(rectangle2, playerRect);
    }

    public Rect getRectangle()
    {
        return rectangle;
    }

    public void incrementY(float y)
    {
        rectangle.top += y;
        rectangle.bottom += y;
        rectangle2.top += y;
        rectangle2.bottom += y;

    }

    @Override
    public void draw(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(Color.argb(150, 0, 0, 0));
        canvas.drawRect(rectangle, paint);
        canvas.drawRect(rectangle2, paint);

        canvas.drawBitmap(bmp, rectangle, rectangle, null);
        canvas.drawBitmap(bmp, rectangle2, rectangle2, null);
    }

    @Override
    public void update()
    {

    }
}
