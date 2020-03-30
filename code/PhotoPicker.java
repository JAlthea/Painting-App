package com.example.althea;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.yongbeam.y_photopicker.util.photopicker.utils.YPhotoPickerIntent;

public class PhotoPicker extends AppCompatActivity
{
    private Context context = null;

    public PhotoPicker(Context context)
    {
        YPhotoPickerIntent intent = new YPhotoPickerIntent(context);
        intent.setMaxSelectCount(20);
        intent.setShowCamera(true);
        intent.setShowGif(true);
        intent.setSelectCheckBox(false);
        intent.setMaxGrideItemCount(3);
        startActivityForResult(intent, 3000);
    }
}
