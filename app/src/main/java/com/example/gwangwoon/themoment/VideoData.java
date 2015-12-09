package com.example.gwangwoon.themoment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.gwangwoon.themoment.Constants.MEMO;
import static com.example.gwangwoon.themoment.Constants.DIRECTORY;
import static com.example.gwangwoon.themoment.Constants.TABLE_NAME;
import static com.example.gwangwoon.themoment.Constants.TIME;
import static com.example.gwangwoon.themoment.Constants.TITLE;

/**
 * Created by Gwangwoon on 2015-12-10.
 */
public class VideoData extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "video.db";
    private static final int DATABASE_VERSION = 1;

    /** Create a helper object for the Events database **/
    public VideoData(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + _ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "  + TIME + " TEXT, "
                + TITLE +" TEXT, "+  DIRECTORY + " TEXT, " + MEMO + " TEXT);" );

    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
