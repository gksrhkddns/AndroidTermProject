package com.example.gwangwoon.themoment;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import org.w3c.dom.Text;

import static com.example.gwangwoon.themoment.Constants.DIRECTORY;
import static com.example.gwangwoon.themoment.Constants.MEMO;
import static com.example.gwangwoon.themoment.Constants.PHOTO;
import static com.example.gwangwoon.themoment.Constants.TABLE_NAME;
import static com.example.gwangwoon.themoment.Constants.TIME;
import static com.example.gwangwoon.themoment.Constants.TITLE;

public class Video extends Activity  implements OnClickListener {

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_CAMERA = 2;

    private VideoData videoData;
    private Uri mVideoCaptureUri;
    VideoView videoView;
    private TextView Datepick;
    private EditText content;
    private EditText title;
    private Button saveButton;
    private String strDate;
    private String memo,mtitle;
    Video video;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        doTakeVideoAction();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);


        videoData = new VideoData(this);



        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", java.util.Locale.getDefault());
        strDate = dateFormat.format(date);
        Datepick = (TextView) findViewById(R.id.date_video);
        Datepick.setText(strDate);

        content = (EditText) findViewById(R.id.editText1_video);
        title = (EditText) findViewById(R.id.editText2_video);
        //mButton = (Button) findViewById(R.id.button);


        saveButton = (Button) findViewById(R.id.SaveButton_video);

        saveButton.setOnClickListener(this);

        videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setVideoURI(mVideoCaptureUri);
        videoView.requestFocus();
        videoView.start();
        videoView.setZOrderOnTop(true);

        videoView.setMediaController(new android.widget.MediaController(this));


    }
    @Override

    public void onClick(View v){ switch(v.getId()){

        case R.id.SaveButton_video:


            memo = content.getText().toString();
            mtitle = title.getText().toString();
            Log.d("시간", strDate);
            Log.d("제목", mtitle);
            Log.d("동영상", mVideoCaptureUri.toString());
            Log.d("메모", memo);
           addEvent(strDate,mtitle,mVideoCaptureUri , memo);
            Toast.makeText(getBaseContext(), "저장되었습니다.",
                    Toast.LENGTH_LONG).show();
            finish();
            break;


    }

    }
    private void doTakeVideoAction()
    {


        Intent intent = new Intent("android.media.action.VIDEO_CAPTURE");
        String url ="tmp_" + String.valueOf(System.currentTimeMillis()) + ".mp4";
       //mVideoCaptureUri = Uri.parse("android.resource://" + getPackageName() + "/" + "R.raw." + url);

        mVideoCaptureUri = Uri.fromFile(new File(getExternalFilesDir(null), url));
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mVideoCaptureUri);

        startActivityForResult(intent, CROP_FROM_CAMERA);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode ==RESULT_OK){
            if(requestCode == CROP_FROM_CAMERA){
             //   Uri uri = data.getData();
                //mVideoCaptureUri = data.getData();
                Log.d("데이터", mVideoCaptureUri.toString());



            }
        }

    }

    private void addEvent(String strDate,String title, Uri uri, String memo){
        long ret = 0;
       SQLiteDatabase db = videoData.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(TIME, strDate);
        values.put(TITLE, title);
        values.put(DIRECTORY, uri.toString() );
        values.put(MEMO, memo);
        db.insertOrThrow(TABLE_NAME, null, values);


    }


}
