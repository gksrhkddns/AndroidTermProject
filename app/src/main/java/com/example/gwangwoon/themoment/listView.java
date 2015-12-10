package com.example.gwangwoon.themoment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import static android.provider.BaseColumns._ID;
import static com.example.gwangwoon.themoment.Constants.DIRECTORY;
import static com.example.gwangwoon.themoment.Constants.MEMO;
import static com.example.gwangwoon.themoment.Constants.PHOTO;
import static com.example.gwangwoon.themoment.Constants.TABLE_NAME;
import static com.example.gwangwoon.themoment.Constants.TIME;
import static com.example.gwangwoon.themoment.Constants.TITLE;

/**
 * Created by Gwangwoon on 2015-12-08.
 */
public class listView extends Activity implements View.OnClickListener {

    private EventsData events;
    private VideoData videdData;
    private static String[]  FROM = {_ID, TIME, TITLE, PHOTO, MEMO};
    private static String ORDER_BY = TIME + " DESC";

    private static String[] video_FROM = {_ID, TIME, TITLE, DIRECTORY, MEMO};

    private String strDate;
    private String mPhoto;
    private String memo;
    private String mtitle;

    private String selectedDate;
    private ArrayList<String> items = null;
    private ArrayAdapter<String> myAdapter =null;
    private ListView list;
    private ImageButton leftButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listactivity);
        events = new EventsData(this);
        videdData = new VideoData(this);
        Intent intent = getIntent();
        selectedDate = intent.getExtras().getString("date");
        items = new ArrayList<String>();

        Cursor cursor = getEvents();
        Cursor videoCursor = getVideoData();
        showTitle(cursor,videoCursor);

        myAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1,
                items);
        ListView lv = (ListView)findViewById(R.id.listView);
      lv.setAdapter(myAdapter);
        leftButton =(ImageButton)findViewById(R.id.leftButton_listactivity);
        leftButton.setOnClickListener(this);


        Log.d("이거다", selectedDate);


       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent ;
                mtitle = myAdapter.getItem(position);
                String temp;
                String title;
                String photo;
                byte[] drawableIconByteArray = null;
                Cursor cursor = getEvents();

                Cursor videoCursor = getVideoData();

                while(   cursor.moveToNext()) {
                    strDate = cursor.getString(1);
                    title = cursor.getString(2);
                    drawableIconByteArray = cursor.getBlob(3);
                    temp = strDate.substring(0, 10);

                    if(temp.equals(selectedDate)){
                        if(mtitle.equals(title)){
                            if(drawableIconByteArray != null) {
                                Log.d("멀까",drawableIconByteArray.toString());
                                intent = new Intent(listView.this, Show.class);
                                intent.putExtra("date", selectedDate);
                                intent.putExtra("title", mtitle);
                                startActivity(intent);
                            }
                            else{
                                intent = new Intent(listView.this, Pencilshow.class);
                                intent.putExtra("date", selectedDate);
                                intent.putExtra("title", mtitle);
                                startActivity(intent);
                            }
                        }

                    }
                }

                while(   videoCursor.moveToNext()) {

                    strDate = videoCursor.getString(1);
                    title = videoCursor.getString(2);
                    temp = strDate.substring(0, 10);

                    if(temp.equals(selectedDate)){
                        if(mtitle.equals(title)){
                            intent = new Intent(listView.this, VideoShow.class);
                            intent.putExtra("date", selectedDate);
                            intent.putExtra("title",mtitle );
                            startActivity(intent);
                        }

                    }
                }



            }
        });





    }

    public void onClick(View v){

        switch(v.getId()) {
            case R.id.leftButton_listactivity:
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

    private void showTitle(Cursor cursor, Cursor videoCursor){


        int i = 0;
        String temp;
        String title;
       // Intent intent = getIntent();

     //   selectedDate = intent.getStringExtra("date");
        while(   cursor.moveToNext()) {

            strDate = cursor.getString(1);
            title = cursor.getString(2);
            temp = strDate.substring(0, 10);

            if(temp.equals(selectedDate)){
                items.add(title);

            }
        }
        while(   videoCursor.moveToNext()) {

            strDate = videoCursor.getString(1);
            title = videoCursor.getString(2);
            temp = strDate.substring(0, 10);

            if(temp.equals(selectedDate)){
                items.add(title);

            }
        }
        Log.d("크기", Integer.toString(items.size()));
        if(items.size() == 0){
            Toast.makeText(listView.this, "글이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

}
