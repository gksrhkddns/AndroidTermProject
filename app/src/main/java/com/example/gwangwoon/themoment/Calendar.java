package com.example.gwangwoon.themoment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Gwangwoon on 2015-12-08.
 */
public class Calendar extends Activity {
    CalendarView cal;
    String date;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        cal = (CalendarView) findViewById(R.id.calendarView);
        cal.setShowWeekNumber(false);

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                // TODO Auto-generated method stub
                if (dayOfMonth < 10) date = year + "." + (month + 1) + ".0" + dayOfMonth;
                else date = year + "." + (month+1) + "." + dayOfMonth;

                Log.d("날짜!", date);
                Intent intent = new Intent(Calendar.this, listView.class);
                intent.putExtra("date", date);
                startActivity(intent);

                Toast.makeText(getBaseContext(), "Selected Date is\n\n"
                                + dayOfMonth + " : " + (month + 1) + " : " + year,
                        Toast.LENGTH_LONG).show();
            }
        });

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
