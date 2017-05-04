package com.example.anthonsteiness.openflappybird;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Locale;

/**
 * Created by Anthon Steiness on 12-04-2017.
 */

public class GameView extends SurfaceView implements Runnable
{
    // This is our Thread
    private Thread gameThread = null;

    // This is new. We need a SurfaceHolder
    // When we use Paint and Canvas in a thread
    // We will see it in action in the draw method soon.
    private SurfaceHolder ourHolder;

    // A boolean which we will set and unset
    // when the game is running- or not.
    private volatile boolean playing;

    // For Drawing
    private Paint paint;
    private Canvas canvas;

    // Control the fps
    long fps = 60;

    // This is used to help calculate the fps
    private long timeThisFrame;

    // Screen resolution
    int screenX;
    int screenY;

    // Holds a reference to the Activity
    Context context;

    // The player
    private Bitmap playerBmp;
    private RunningPlayer runningPlayer;
    private Bitmap poleBmp;

    private ObstacleManager obstacleManager;

    private Rect rect = new Rect();

    MediaPlayer tapSound;
    private Bitmap johnCena;

    public static int HighScore;
    static SharedPreferences prefs;
    private String saveScore = "Highscore";


    public GameView(Context context, int screenX, int screenY)
    {
        super(context);

        this.context = context;

        this.screenX = screenX;
        this.screenY = screenY;

        // Initialize our drawing objects
        ourHolder = getHolder();
        paint = new Paint();

        String sPackage ="com.example.anthonsteiness.openflappybird";

        prefs = context.getSharedPreferences(sPackage, context.MODE_PRIVATE);

        Constants.HIGHSCORE = prefs.getInt(saveScore, 0);

        Constants.ASSET_MANAGER = context.getApplicationContext().getAssets();
        //Constants.TYPEFACE = Typeface.createFromAsset(Constants.ASSET_MANAGER, "superrocket.ttf");
        //Constants.TYPEFACE = Typeface.createFromAsset(Constants.ASSET_MANAGER, String.format(Locale.US, "fonts/%s", "superrocket.ttf"));
        Constants.TYPEFACE = Typeface.createFromAsset(Constants.ASSET_MANAGER, String.format(Locale.US, "fonts/%s", "sketch3d.otf"));
        Constants.GO_TYPEFACE = Typeface.createFromAsset(Constants.ASSET_MANAGER, String.format(Locale.US, "fonts/%s", "bloodlust2.ttf"));

        Constants.CURRENT_CONTEXT = context;

        Constants.JOHN_CENA = MediaPlayer.create(context, R.raw.johncenacutlong);
        tapSound = MediaPlayer.create(context, R.raw.bounceyofrankie);

        playerBmp = BitmapFactory.decodeResource(getResources(), R.drawable.smallswaggyflappy);
        Constants.PLAYER_BMP = BitmapFactory.decodeResource(getResources(), R.drawable.smallswaggyflappy);
        Constants.PLAYER_DOWN_BMP = BitmapFactory.decodeResource(getResources(), R.drawable.smallswaggyflappydown);
        Constants.PLAYER_UP_BMP = BitmapFactory.decodeResource(getResources(), R.drawable.smallswaggyflappyup);
        runningPlayer = new RunningPlayer(playerBmp, 550, 650);

        poleBmp = BitmapFactory.decodeResource(getResources(), R.drawable.testbg7);
        obstacleManager = new ObstacleManager(poleBmp, 400, 650, 100, Color.GREEN);

        johnCena = BitmapFactory.decodeResource(getResources(), R.drawable.johncenameme4);

    }

    public void reset()
    {
        runningPlayer = new RunningPlayer(playerBmp, 550, 650);
        obstacleManager = new ObstacleManager(poleBmp, 400, 650, 100, Color.GREEN);
        //prefs.edit().putInt(saveScore, Constants.HIGHSCORE).commit();

    }

    @Override
    public void run()
    {
        while (playing)
        {
            // Capture current time in milliseconds
            long startFrameTime = System.currentTimeMillis();

            // Update the frame
            update();

            // Draw the frame
            draw();

            // Calculate the fps this frame
            // We can use this in animations and more
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1)
            {
                fps = 1000 / timeThisFrame;
            }
        }
    }



    // Everything that needs to be updated goes in here
    // Movement, collision detection etc.
    public void update()
    {
        //if (!Constants.PAUSED)
        //{
            if (!Constants.GAME_OVER)
            {
                obstacleManager.update();
                if (obstacleManager.playerCollide(runningPlayer))
                {
                    Constants.GAME_OVER = true;
                    Constants.GAME_OVER_TIME = System.currentTimeMillis();
                    Constants.JOHN_CENA.start();
                }
            }
        //}
        //HighScore = Constants.HIGHSCORE;
    }



    public void draw()
    {
        if (ourHolder.getSurface().isValid())
        {
            // Lock the canvas ready to draw
            canvas = ourHolder.lockCanvas();

            // Draw background color
            canvas.drawColor(Color.argb(255,  26, 128, 182));

            // Display the current fps on the screen
            paint.setColor(Color.argb(255, 249, 129, 0));
            paint.setTextSize(45);
            canvas.drawText("FPS:" + fps, 20, 40, paint);

            // Draw the player
            runningPlayer.draw(canvas);

            obstacleManager.draw(canvas);

            if (Constants.GAME_OVER)
            {
                Paint paint = new Paint();
                paint.setTextSize(200);
                paint.setColor(Color.RED);
                paint.setTypeface(Constants.GO_TYPEFACE);
                drawGameOver(canvas, paint, "GAME OVER!");

            }

            // Draw everything to the screen
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void drawGameOver(Canvas canvas, Paint paint, String text)
    {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(rect);
        int cHeight = rect.height();
        int cWidth = rect.width();
        paint.getTextBounds(text, 0, text.length(), rect);
        float x = cWidth / 2f - rect.width() / 2f - rect.left;
        float y = cHeight / 2f + rect.height() / 2f - rect.bottom;
        canvas.drawText(text, x, y, paint);

        Rect rect2 = new Rect(0 - 200, 0, Constants.SCREEN_HEIGHT, (Constants.SCREEN_WIDTH / 2));
        Rect rect3 = new Rect(0, 0, Constants.SCREEN_HEIGHT, Constants.SCREEN_WIDTH / 2);

        canvas.drawBitmap(johnCena, rect3, rect2, null);

        prefs.edit().putInt(saveScore, Constants.HIGHSCORE).commit();
    }


    public void pause()
    {
        playing = false;
        try
        {
            gameThread.join();
        }
        catch (InterruptedException ex)
        {
            // Error
        }
    }

    // Make a new thread and start it
    // Execution moves to our run method
    public void resume()
    {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent)
    {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK)
        {
            // Player has touched the screen
            case MotionEvent.ACTION_DOWN:
                Constants.PAUSED = false;
                runningPlayer.onTouch();

                if (!Constants.GAME_OVER)
                {
                    tapSound.start();

                }



                if (Constants.GAME_OVER && System.currentTimeMillis() - Constants.GAME_OVER_TIME >= 2000)
                {
                    reset();
                    Constants.GAME_OVER = false;
                    Constants.PAUSED = true;
                }



                break;

            // Player has removed finger from screen
            case MotionEvent.ACTION_UP:
                break;

            // Player moves finger on screen
            case MotionEvent.ACTION_MOVE:
                //playerPoint.set((int)motionEvent.getX(), (int)motionEvent.getY());
                break;
        }


        return true;
    }

}
