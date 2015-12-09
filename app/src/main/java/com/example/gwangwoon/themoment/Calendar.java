package com.example.gwangwoon.themoment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Gwangwoon on 2015-12-08.
 */
public class Calendar extends Activity implements View.OnClickListener {
    CalendarView cal;
    String date;
    private ImageButton menuButton;
    private ImageButton settingButton;
    private ImageButton searchButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        cal = (CalendarView) findViewById(R.id.calendarView);
        cal.setShowWeekNumber(false);
        menuButton =(ImageButton)findViewById(R.id.menubutton_calendar);
        menuButton.setOnClickListener(this);

        settingButton =(ImageButton)findViewById(R.id.settingbutton_calendar);
        settingButton.setOnClickListener(this);

        searchButton = (ImageButton)findViewById(R.id.searchbutton_calendar);
        searchButton.setOnClickListener(this);

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


    public void onClick(View v){

        switch(v.getId()) {
            case R.id.menubutton_calendar:
                finish();
                break;
            case R.id.settingbutton_calendar:
                AlertDialog alertDialog = new AlertDialog.Builder(Calendar.this).create();
                alertDialog.setTitle("The Moment");
                alertDialog.setMessage("현재 버전 1.0.0" + "\n" + "개발자 : 한광운");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                break;
            case R.id.searchbutton_calendar:
                Intent intent = new Intent(this, SearchView.class);
                startActivity(intent);
                break;
        }
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
