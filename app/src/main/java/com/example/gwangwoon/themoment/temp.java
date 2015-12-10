package com.example.gwangwoon.themoment;
import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

import static android.provider.BaseColumns._ID;
import static com.example.gwangwoon.themoment.Constants.DIRECTORY;
import static com.example.gwangwoon.themoment.Constants.MEMO;
import static com.example.gwangwoon.themoment.Constants.PHOTO;
import static com.example.gwangwoon.themoment.Constants.TABLE_NAME;
import static com.example.gwangwoon.themoment.Constants.TIME;
import static com.example.gwangwoon.themoment.Constants.TITLE;

public class temp extends Activity implements OnClickListener
{
    private EventsData events;
    private VideoData videdData;
    private ArrayList<String> items = null;
    private ArrayAdapter<String> myAdapter = null;
    private ImageButton leftButton;
    private static String[]  FROM = {_ID, TIME, TITLE, PHOTO, MEMO};
    private static String ORDER_BY = TIME + " DESC";
    private ListView lv;
    private static String[] video_FROM = {_ID, TIME, TITLE, DIRECTORY, MEMO};


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        android.widget.SearchView searchView = (android.widget.SearchView) findViewById(R.id.searchView);

        events = new EventsData(this);
        videdData = new VideoData(this);
        String temp = null;

        final Cursor cursor = getEvents();
        final Cursor videoCursor = getVideoData();

        items = new ArrayList<String>();
       lv = (ListView) findViewById(R.id.listView_search);

        leftButton = (ImageButton) findViewById(R.id.leftButton_search);
        leftButton.setOnClickListener(this);

        searchView.setIconified(false);
        searchView.setQueryHint("제목을 입력하세요");


        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub
                Log.d("출력", query);
                String title = String.valueOf(query);
                showTitle(cursor, videoCursor, title);
                Toast.makeText(getBaseContext(), query,
                        Toast.LENGTH_SHORT).show();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub

                //	Toast.makeText(getBaseContext(), newText,
                //    Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                int size = myAdapter.getItem(position).length();
                String strDate = myAdapter.getItem(position).substring(size - 16, size-6);
                Toast.makeText(temp.this,strDate, Toast.LENGTH_SHORT).show();
                String title = myAdapter.getItem(position).substring(0, size - 19);

                byte[] drawableIconByteArray = null;
                String tStrDate;
                String tTitle;
                Cursor cursor = getEvents();
                String temp;
                Cursor videoCursor = getVideoData();

                while (cursor.moveToNext()) {
                    tStrDate = cursor.getString(1);
                    tTitle = cursor.getString(2);
                    drawableIconByteArray = cursor.getBlob(3);
                    temp = strDate.substring(0, 10);

                    if(temp.equals(strDate)){
                        if(tTitle.equals(title)){
                            Log.d("날짜",  temp + " / " + strDate );
                            if(drawableIconByteArray != null) {
                                intent = new Intent(temp.this, Show.class);

                                intent.putExtra("date", temp);
                                intent.putExtra("title",title);
                                startActivity(intent);
                            }
                            else{
                                intent = new Intent(temp.this, Pencilshow.class);

                                intent.putExtra("date", temp);
                                intent.putExtra("title",title);
                                startActivity(intent);
                            }
                        }

                    }

                }

                while (videoCursor.moveToNext()) {

                    tStrDate = videoCursor.getString(1);
                    tTitle = videoCursor.getString(2);
                    temp = strDate.substring(0, 10);
                    if (temp.equals(strDate)) {
                        if (tTitle.equals(title)) {
                            intent = new Intent(temp.this, VideoShow.class);
                            intent.putExtra("date", temp);
                            intent.putExtra("title", tTitle);
                            startActivity(intent);
                        }

                    }
                }


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
        cursor = getEvents();
        videoCursor = getVideoData();
        items = new ArrayList<String>();
        //   selectedDate = intent.getStringExtra("date");
        while(   cursor.moveToNext()) {

           strDate = cursor.getString(1);
            mtitle = cursor.getString(2);


            if(title.equals(mtitle)){
                items.add(title + " - " + strDate);
                Log.d("안됨?", title);

            }
        }

        while(   videoCursor.moveToNext()) {

            strDate = videoCursor.getString(1);
            mtitle = videoCursor.getString(2);


            if(title.equals(mtitle)){
                items.add(title + " - " + strDate );
                Log.d("안됨?1", title);
            }
        }


        if(items.size() == 0){
            Toast.makeText(temp.this, "글이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();

        }

        else {

            myAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_expandable_list_item_1,
                    items);

            lv.setAdapter(myAdapter);
        }

    }



}
