package com.example.gwangwoon.themoment;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.provider.BaseColumns._ID;
import static com.example.gwangwoon.themoment.Constants.TABLE_NAME;
import static com.example.gwangwoon.themoment.Constants.TIME;
import static com.example.gwangwoon.themoment.Constants.MEMO;
import static com.example.gwangwoon.themoment.Constants.PHOTO;
import static com.example.gwangwoon.themoment.Constants.TITLE;
/**
 * Created by Gwangwoon on 2015-12-06.
 */
public class Show extends Activity {


    private EventsData events;
    private static String[] FROM = {_ID, TIME, TITLE, PHOTO, MEMO};
    private static String ORDER_BY = TIME + " DESC";
    private TextView Date;
    private TextView content;
    private TextView Title;
    private ImageView imageView;
    private String strDate = null;
    private String mPhoto;
    private String memo;
    private String title = null;
    private String selectedTitle;
    private String selectedDate;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);

        events = new EventsData(this);
        Intent intent = getIntent();
        selectedDate = intent.getExtras().getString("date");
        selectedTitle = intent.getExtras().getString("title");
        Cursor cursor = getEvents();
       showDB(cursor);







    }
    private Cursor getEvents(){
        //관리된 질의 실행하기. 필요하면 액티비티가 종료와
        //결과 묶음을 다시 질의하는 작업을 처리할 것이다.
        //(perform a managed query. The Activity will handle closing)
        //(and re_querying the cursor when needed.)
        SQLiteDatabase db = events.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
        startManagingCursor(cursor);
        return cursor;

    }

    private void showDB(Cursor cursor){

        byte[] drawableIconByteArray = null;
        long id;
        //could use getColumnIndexOrThrow() to get indexes
       String temp;
        while(   cursor.moveToNext()) {

            strDate = cursor.getString(1);
            title = cursor.getString(2);
            drawableIconByteArray = cursor.getBlob(3);
            memo = cursor.getString(4);
            temp = strDate.substring(0, 10);

            if(temp.equals(selectedDate)&& selectedTitle.equals(title))break;
        }

            Log.d("제목", title);
        //화면에보여주기
        Bitmap photo  = convertByteArrayToBitmap(drawableIconByteArray);
        Date = (TextView)findViewById(R.id.date_show);
        Date.setText(strDate);
        Title = (TextView)findViewById(R.id.title_show);
        Title.setText(title);
        content = (TextView)findViewById(R.id.memo_show);
        content.setText(memo);
        imageView = (ImageView)findViewById(R.id.image_show);

        imageView.setImageBitmap(photo);

    }

    public static Bitmap convertByteArrayToBitmap(byte[] byteArrayToBeCOnvertedIntoBitMap) {
// 넘어온 Byte 배열에서 비트맵을 만들어낸다.
        Bitmap bitMapImage = BitmapFactory.decodeByteArray(byteArrayToBeCOnvertedIntoBitMap, 0,   byteArrayToBeCOnvertedIntoBitMap.length);

        return bitMapImage;
    }



}
