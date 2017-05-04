package com.example.anthonsteiness.openflappybird;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.MediaPlayer;

/**
 * Created by Anthon Steiness on 13-04-2017.
 */

public class Constants
{
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    public static Context CURRENT_CONTEXT;

    public static boolean GAME_OVER = false;
    public static long GAME_OVER_TIME;

    public static boolean PAUSED = true;

    public static MediaPlayer JOHN_CENA;

    public static Bitmap PLAYER_BMP;
    public static Bitmap PLAYER_UP_BMP;
    public static Bitmap PLAYER_DOWN_BMP;

    public static int HIGHSCORE;

    public static SharedPreferences PREFS;
    //public String SAVE_SCORE = "Highscore";

    static public AssetManager ASSET_MANAGER;
    public static Typeface TYPEFACE;
    public static Typeface GO_TYPEFACE;
}
