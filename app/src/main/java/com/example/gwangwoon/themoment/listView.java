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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import static android.provider.BaseColumns._ID;
import static com.example.gwangwoon.themoment.Constants.MEMO;
import static com.example.gwangwoon.themoment.Constants.PHOTO;
import static com.example.gwangwoon.themoment.Constants.TABLE_NAME;
import static com.example.gwangwoon.themoment.Constants.TIME;
import static com.example.gwangwoon.themoment.Constants.TITLE;

/**
 * Created by Gwangwoon on 2015-12-08.
 */
public class listView extends Activity {

    private EventsData events;
    private static String[] FROM = {_ID, TIME, TITLE, PHOTO, MEMO};
    private static String ORDER_BY = TIME + " DESC";

    private String strDate;
    private String mPhoto;
    private String memo;
    private String mtitle;

    private String selectedDate;
    private ArrayList<String> items = null;
    private ArrayAdapter<String> myAdapter =null;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listactivity);
        events = new EventsData(this);
        Intent intent = getIntent();
        selectedDate = intent.getExtras().getString("date");
        items = new ArrayList<String>();

        Cursor cursor = getEvents();
        showTitle(cursor);

        myAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1,
                items);
        ListView lv = (ListView)findViewById(R.id.listView);
      lv.setAdapter(myAdapter);

        Log.d("이거다", selectedDate);


       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(listView.this, Show.class);
                mtitle = myAdapter.getItem(position);
                Log.d("통과1", selectedDate);
                Log.d("통과2", mtitle);
                intent.putExtra("date", selectedDate);
                intent.putExtra("title",mtitle );
               startActivity(intent);
            }
        });





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

    private void showTitle(Cursor cursor){


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
                Log.d("들어갔다!!",temp);
                items.add(title);
            }
        }
        Log.d("크기", Integer.toString(items.size()));
        if(items.size() == 0){
            Toast.makeText(listView.this, "글이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }
        //myAdapter.notifyDataSetChanged();




        //화면에보여주기
/*
        Date = (TextView)findViewById(R.id.date_show);
        Date.setText(strDate);
        Title = (TextView)findViewById(R.id.title_show);
        Title.setText(title);
        content = (TextView)findViewById(R.id.memo_show);
        content.setText(memo);
        imageView = (ImageView)findViewById(R.id.image_show);

        imageView.setImageBitmap(photo);
*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
