package com.example.anthonsteiness.openflappybird;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;


/**
 * Created by Anthon Steiness on 14-04-2017.
 */

public class RunningPlayer implements GameObject
{
    private static int x;
    private static int y;
    private static int gravity = 1;
    private static int vSpeed = 1;
    static int playerHeight;
    static int playerWidth;
    private static int jumpPower = -20;
    private Bitmap bmp;
    private int animationstate = 0;



    public RunningPlayer(Bitmap bmp, int x, int y)
    {
        this.x = x;
        this.y = y;
        this.bmp = bmp;


//        this.width = bmp.getWidth() / mColumnWidth;
//        this.height = bmp.getHeight() / mColumnHeight;
//
        playerHeight = bmp.getHeight() / 2;
        playerWidth = bmp.getWidth() / 2;
    }


    public void checkGround()
    {
        // If the x value is less then then Screen_width
        // Make sure Vertical speed excels, when there is no ground.
        if (x < Constants.SCREEN_WIDTH)
        {
            //System.out.println("TESTER::::INSIDE!!!");
            vSpeed += gravity;

            if (x < Constants.SCREEN_WIDTH && x > 0)
            {
                System.out.println("TESTER::::INSIDE!!!");
            }

            if (x < 0)
            {
                System.out.println("TESTER:::ABOVE!!!");
            }

            if (x > Constants.SCREEN_WIDTH)
            {
                // If x is greater then SCREEN_WIDTH = "Below" Screen edge.
                vSpeed = Constants.SCREEN_WIDTH - x;
            }
        }


        else if (vSpeed > 0)
        {
            System.out.println("TESTER:::BELOW!!!");
            x = Constants.SCREEN_WIDTH;
        }

        //
        x += vSpeed;

    }

    public void onTouch()
    {
        // If we are above ground
//        if (x <= Constants.SCREEN_WIDTH)
//        {
//            vSpeed = jumpPower;
//        }
        if (!Constants.GAME_OVER)
        {
            vSpeed = jumpPower;
        }

    }

    public void checkAnimState(){
        if (vSpeed < 0){
            animationstate =2;
        }
        else if(vSpeed > 0){
            animationstate =1;
        }
        else{
            animationstate = 0;
        }
    }

    public void switchAnimation(){

        if(animationstate ==0)
        {
            bmp = Constants.PLAYER_BMP;
        }
        else if(animationstate ==1)
        {
            bmp = Constants.PLAYER_DOWN_BMP;
        }
        else if(animationstate == 2)
        {
            bmp = Constants.PLAYER_UP_BMP;
        }
    }


    @Override
    public void draw(Canvas canvas)
    {
        update();
        canvas.drawBitmap(bmp, x, y, null);

    }

    @Override
    public void update()
    {
        if (!Constants.PAUSED)
        {
            //if (!Constants.GAME_OVER)
            //{
                checkGround();
            checkAnimState();
            switchAnimation();
            //}
        }
        //checkGround();
    }

    public Bitmap getBitmap()
    {
        return bmp;
    }

    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }

}
