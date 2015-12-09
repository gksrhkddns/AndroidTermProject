package com.example.gwangwoon.themoment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import static android.provider.BaseColumns._ID;
import static com.example.gwangwoon.themoment.Constants.TABLE_NAME;
import static com.example.gwangwoon.themoment.Constants.TIME;
import static com.example.gwangwoon.themoment.Constants.MEMO;
import static com.example.gwangwoon.themoment.Constants.PHOTO;
import static com.example.gwangwoon.themoment.Constants.TITLE;
/**
 * Created by Gwangwoon on 2015-12-10.
 */
public class Pencilshow extends Activity implements View.OnClickListener {


private EventsData events;
private static String[] FROM = {_ID, TIME, TITLE, PHOTO, MEMO};
private static String ORDER_BY = TIME + " DESC";
private TextView Date;
private TextView content;
private TextView Title;

private String strDate = null;
private String memo;
private String title = null;
private String selectedTitle;
private String selectedDate;
private ImageButton leftButton;
public void onCreate(Bundle savedInstanceState)
        {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pencilshow);

        events = new EventsData(this);
        Intent intent = getIntent();
        selectedDate = intent.getExtras().getString("date");
        selectedTitle = intent.getExtras().getString("title");
        Cursor cursor = getEvents();
        showDB(cursor);


        leftButton = (ImageButton)findViewById(R.id.leftButton_pencilshow);
        leftButton.setOnClickListener(this);





        }

@Override
public void onClick(View v){

        switch(v.getId()) {
        case R.id.leftButton_pencilshow:
        finish();
        break;
        }
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

        memo = cursor.getString(4);
        temp = strDate.substring(0, 10);

        if(temp.equals(selectedDate)&& selectedTitle.equals(title))break;
        }


        //화면에보여주기

        Date = (TextView)findViewById(R.id.date_pencilshow);
        Date.setText(strDate);
        Title = (TextView)findViewById(R.id.title_pencilshow);
        Title.setText(title);
        content = (TextView)findViewById(R.id.memo_pencilshow);
        content.setText(memo);

        }





        }
