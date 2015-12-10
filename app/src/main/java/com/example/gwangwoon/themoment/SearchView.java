package com.example.gwangwoon.themoment;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.gwangwoon.themoment.Constants.DIRECTORY;
import static com.example.gwangwoon.themoment.Constants.MEMO;
import static com.example.gwangwoon.themoment.Constants.PHOTO;
import static com.example.gwangwoon.themoment.Constants.TABLE_NAME;
import static com.example.gwangwoon.themoment.Constants.TIME;
import static com.example.gwangwoon.themoment.Constants.TITLE;

/**
 * Created by Gwangwoon on 2015-12-10.
 */
public class SearchView extends Activity implements View.OnClickListener {

    private EventsData events;
    private VideoData videdData;
    private ArrayList<String> items = null;
    private ArrayAdapter<String> myAdapter = null;
    private ImageButton leftButton;
    private static String[]  FROM = {_ID, TIME, TITLE, PHOTO, MEMO};
    private static String ORDER_BY = TIME + " DESC";

    private static String[] video_FROM = {_ID, TIME, TITLE, DIRECTORY, MEMO};


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

       android.widget.SearchView searchView = (android.widget.SearchView) findViewById(R.id.searchView);

        events = new EventsData(this);
        videdData = new VideoData(this);
        String temp = null;

        final Cursor cursor = getEvents();
         final Cursor videoCursor = getVideoData();


        myAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1,
                items);
        ListView lv = (ListView) findViewById(R.id.listView_search);
        lv.setAdapter(myAdapter);
        leftButton = (ImageButton) findViewById(R.id.leftButton_search);
        leftButton.setOnClickListener(this);

        searchView.setIconified(false);
        searchView.setQueryHint("두 자 이상 입력하세요");
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {


            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub


                String title = String.valueOf(hasFocus);
                showTitle(cursor, videoCursor, title);
                Toast.makeText(getBaseContext(), String.valueOf(hasFocus),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onClick(View v){

        switch(v.getId()) {
            case R.id.leftButton_search:
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

    private Cursor getVideoData(){
        //관리된 질의 실행하기. 필요하면 액티비티가 종료와
        //결과 묶음을 다시 질의하는 작업을 처리할 것이다.
        //(perform a managed query. The Activity will handle closing)
        //(and re_querying the cursor when needed.)
        SQLiteDatabase db = videdData.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, video_FROM, null, null, null, null, ORDER_BY);
        startManagingCursor(cursor);
        return cursor;

    }

    private void showTitle(Cursor cursor, Cursor videoCursor, String title){


        int i = 0;
        String temp;
        String mtitle;
        // Intent intent = getIntent();
        String  strDate;
        //   selectedDate = intent.getStringExtra("date");
        while(   cursor.moveToNext()) {

            strDate = cursor.getString(1);
           mtitle = cursor.getString(2);


            if(title.equals(mtitle)){
                items.add(title);

            }
        }
        while(   videoCursor.moveToNext()) {

            strDate = cursor.getString(1);
            mtitle = cursor.getString(2);


            if(title.equals(mtitle)){
                items.add(title);

            }
            }


        if(items.size() == 0){
            Toast.makeText(SearchView.this, "글이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

}
