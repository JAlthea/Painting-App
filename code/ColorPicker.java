package com.example.althea;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

public class ColorPicker {

    private Button btnColor = null;
    private Palette palette = null;

    public ColorPicker(final Context context, Integer currentBackgroundColor, Button btnColor, Palette palette)
    {
        this.btnColor = btnColor;
        this.palette = palette;
        ColorPickerDialogBuilder
                .with(context)
                .setTitle("Choose color")
                .initialColor(currentBackgroundColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                        Toast.makeText(context, "ColorSelected: 0x" + Integer.toHexString(selectedColor), Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        changeColor(selectedColor);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }

    public void changeColor(int selectedColor)
    {
        btnColor.setBackgroundColor(selectedColor);
        btnColor.setTextColor(selectedColor);
        if (palette != null)
            palette.paint.setColor(selectedColor);
    }
}
