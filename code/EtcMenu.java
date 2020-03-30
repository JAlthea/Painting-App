package com.example.althea;

import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import com.yongbeam.y_photopicker.util.photopicker.PhotoPickerActivity;
import com.yongbeam.y_photopicker.util.photopicker.utils.YPhotoPickerIntent;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class EtcMenu
{
    private int imgCount = 0;
    private Context context = null;
    private CustomView myView = null;
    private ImageView imageView = null;


    public EtcMenu(Context context, CustomView myView, int imgCount)
    {
        this.context = context;
        this.myView = myView;
        this.imgCount = imgCount;
        this.imageView = new ImageView(context);
        EtcDialog();
    }

    public void EtcDialog()
    {
        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.etc_manu);
        dialog.show();

        final ImageButton ibNew = dialog.findViewById(R.id.New);
        final ImageButton ibSave = dialog.findViewById(R.id.Save);
        final ImageButton ibLoad = dialog.findViewById(R.id.Load);
        final ImageButton ibSource = dialog.findViewById(R.id.Source);

        ibNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myView.reset();
                dialog.dismiss();
            }
        });

        ibSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String root = Environment.getExternalStorageState();
                String sdPath;
                if (root.equals(Environment.MEDIA_MOUNTED))
                    sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                else
                    sdPath = Environment.MEDIA_UNMOUNTED;

                File myDir = new File(sdPath);
                myDir.mkdirs();
                String fname = "Image-" + imgCount + ".jpg";
                File file = new File(myDir, fname);
                if (file.exists()) file.delete();
                Log.i("LOAD", sdPath + fname);
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    myView.getPalette().bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    //out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                dialog.dismiss();
            }
        });

        ibLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PhotoPicker(context);

                /*
                // 경로 얻는법
                String sdcard = Environment.getExternalStorageState();
                if(!sdcard.equals(Environment.MEDIA_MOUNTED))
                {
                    //SD카드 UNMOUNTED
                    Log.d("mstag","sdcard unmounted");
                    root = "" + Environment.getRootDirectory().getAbsolutePath() + rootFolderName; //내부저장소의 주소를 얻어옴
                }
                else {
                    //SD카드 MOUNT
                    Log.d("mstag","sdcard mounted");
                    root = "" + Environment.getExternalStorageDirectory().getAbsolutePath() + rootFolderName; //외부저장소의 주소를 얻어옴
                }
                Log.d("mstag","root dir is => "+root);

                memoRoot = root + memoListFolderName;

                File rootCheck = new File(root);

                if(!rootCheck.exists())
                { //최상위 루트폴더 미 존재시
                    rootCheck.mkdir();
                    Log.d("mstag","root make");
                    rootCheck = new File(memoRoot);

                    if(!rootCheck.exists()) { //하위 메모저장폴더 미 존재시
                        rootCheck.mkdir(); Log.d("mstag","root-son make");
                    }
                }
                */
            }
        });

        ibSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "icon source : https://www.flaticon.com" +
                        "\n lib source : https://github.com/QuadFlask/colorpicker", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
