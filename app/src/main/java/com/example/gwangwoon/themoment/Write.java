package com.example.gwangwoon.themoment;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.gwangwoon.themoment.Constants.MEMO;
import static com.example.gwangwoon.themoment.Constants.PHOTO;
import static com.example.gwangwoon.themoment.Constants.TABLE_NAME;
import static com.example.gwangwoon.themoment.Constants.TIME;
import static com.example.gwangwoon.themoment.Constants.TITLE;

/**
 * Created by Gwangwoon on 2015-12-09.
 */
public class Write extends Activity  implements View.OnClickListener {

    private EventsData events;


    private TextView Datepick;
    private EditText content;
    private EditText title;
    private Button saveButton_write;
    private Bitmap photo;
    private String strDate;
    private String sPhoto;
    private String memo;
    String mtitle;
    //private Button mButton;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write);

        events = new EventsData(this);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", java.util.Locale.getDefault());
        strDate = dateFormat.format(date);
        Datepick =(TextView)findViewById(R.id.date_write);
        Datepick.setText(strDate);

        content = (EditText)findViewById(R.id.memo_write);
        title = (EditText)findViewById(R.id.title_write);
        //mButton = (Button) findViewById(R.id.button);


        saveButton_write = (Button) findViewById(R.id.SaveButton_write);

        saveButton_write.setOnClickListener(this);


        //doTakeAlbumAction();



    }





    @Override
    public void onClick(View v)
    {

        switch(v.getId()){
            case R.id.SaveButton_write:


                memo = content.getText().toString();
                mtitle = title.getText().toString();
                addEvent(strDate,mtitle,null , memo);
                Toast.makeText(getBaseContext(), "저장되었습니다.",
                        Toast.LENGTH_LONG).show();
                finish();
                break;


        }
    }

    private void addEvent(String strDate,String title, Bitmap photo, String memo){
        long ret = 0;
        SQLiteDatabase db = events.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(TIME, strDate);
        values.put(TITLE, title);
       // values.put(PHOTO, "");
        values.put(MEMO, memo);
        db.insertOrThrow(TABLE_NAME, null, values);


    }





}


