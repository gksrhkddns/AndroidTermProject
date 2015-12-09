package com.example.gwangwoon.themoment;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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

import org.w3c.dom.Text;

import static com.example.gwangwoon.themoment.Constants.MEMO;
import static com.example.gwangwoon.themoment.Constants.PHOTO;
import static com.example.gwangwoon.themoment.Constants.TABLE_NAME;
import static com.example.gwangwoon.themoment.Constants.TIME;
import static com.example.gwangwoon.themoment.Constants.TITLE;

public class Camera extends Activity implements OnClickListener
{
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private EventsData events;
    private Uri mImageCaptureUri;
    private ImageView mPhotoImageView;
    private TextView Datepick;
    private EditText content;
    private EditText title;
    private Button saveButton;
    private Bitmap photo;
    private String strDate;
    private String sPhoto;
    private String memo;
    String imageSaveUri;
    Uri uri;
    private int dbVersion =1;
    String mtitle;
    //private Button mButton;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);

        events = new EventsData(this);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", java.util.Locale.getDefault());
        strDate = dateFormat.format(date);
        Datepick =(TextView)findViewById(R.id.date);
        Datepick.setText(strDate);

        content = (EditText)findViewById(R.id.editText1);
        title = (EditText)findViewById(R.id.editText2);
        //mButton = (Button) findViewById(R.id.button);
        mPhotoImageView = (ImageView) findViewById(R.id.image);

        saveButton = (Button) findViewById(R.id.SaveButton);

        saveButton.setOnClickListener(this);


        //doTakeAlbumAction();
        doTakePhotoAction();



    }

    /**
     * 카메라에서 이미지 가져오기
     */
    private void doTakePhotoAction()
    {


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";

        mImageCaptureUri = Uri.fromFile(new File(getExternalFilesDir(null), url));
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

        startActivityForResult(intent, PICK_FROM_CAMERA);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
      
      

        if(resultCode != RESULT_OK)
        {
            return;
        }

        switch(requestCode)
        {
            case CROP_FROM_CAMERA:
            {
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                // 임시 파일을 삭제합니다.
                final Bundle extras = data.getExtras();

                if(extras != null)
                {


                    photo = extras.getParcelable("data");

                    mPhotoImageView.setImageBitmap(photo);
                    mPhotoImageView.setScaleType(ImageView.ScaleType.FIT_XY);

                }

                //임시 파일 삭제
                File f = new File(mImageCaptureUri.getPath());
                if(f.exists())
                {
                    f.delete();
                }

                break;
            }

   
            case PICK_FROM_CAMERA:
            {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
                // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.

                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 200);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_CAMERA);

                break;
            }
        }

    }

    @Override
    public void onClick(View v)
    {

        switch(v.getId()){
            case R.id.SaveButton:
                sPhoto =  mImageCaptureUri.getPath();
                Log.d("보자", sPhoto);
                memo = content.getText().toString();
                mtitle = title.getText().toString();
                addEvent(strDate,mtitle,photo , memo);
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

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] imageInByte = stream.toByteArray();

        values.put(TIME, strDate);
        values.put(TITLE, title);
        values.put(PHOTO, imageInByte);
        values.put(MEMO, memo);
        db.insertOrThrow(TABLE_NAME, null, values);


    }




    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {

        }
        return bm;
    }

    public String getStrDate(){return strDate;}

    public Bitmap getPhoto(){return photo;}
}
