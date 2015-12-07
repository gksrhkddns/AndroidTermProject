package com.example.gwangwoon.themoment;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.w3c.dom.Text;

public class Video extends Activity  {

    private static final int PICK_FROM_VIDEO = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_VIDEO = 2;

    private Uri mVideoCaptureUri;
    private VideoView mVideoView;
    private TextView Datepick;
    private EditText content;
    private Button saveButton;
    private Bitmap photo;
    private String strDate;
    private String contents;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.DD HH:MM", java.util.Locale.getDefault());
        strDate = dateFormat.format(date);
        Datepick =(TextView)findViewById(R.id.date1);
        Datepick.setText(strDate);

        content = (EditText)findViewById(R.id.editText1);
        contents = content.getText().toString();
        contents = contents.replace("'","''");
        //mButton = (Button) findViewById(R.id.button);
      //  mVideoView = (ImageView) findViewById(R.id.image);

        saveButton = (Button) findViewById(R.id.SaveButton1);
        // mButton.setOnClickListener(this);
        //saveButton.setOnClickListener(this);
        doTakeVideoAction();

    }
    private void doTakeVideoAction()
    {


        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        // 임시로 사용할 파일의 경로를 생성
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".mp4";
        mVideoCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mVideoCaptureUri);
        // 특정기기에서 사진을 저장못하는 문제가 있어 다음을 주석처리 합니다.
        //intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_FROM_VIDEO);

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode != RESULT_OK)
        {
            return;
        }

        switch(requestCode)
        {
            case CROP_FROM_VIDEO:
            {
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                // 임시 파일을 삭제합니다.
                final Bundle extras = data.getExtras();

                    mVideoView.setVideoURI(mVideoCaptureUri);
                // 임시 파일 삭제
                File f = new File(mVideoCaptureUri.getPath());
                if(f.exists())
                {
                    f.delete();
                }

                break;
            }

         /*   case PICK_FROM_ALBUM:
            {
                // 이후의 처리가 카메라와 같으므로 일단  break없이 진행합니다.
                // 실제 코드에서는 좀더 합리적인 방법을 선택하시기 바랍니다.

                mImageCaptureUri = data.getData();
            }
            */


        }
    }


    /**
     * 카메라에서 이미지 가져오기
     */
}
