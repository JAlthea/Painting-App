package com.example.althea;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class PenPicker
{
    private Context context = null;
    private float penStroke;
    private Palette palette = null;

    public PenPicker(Context context, float penStroke, Palette palette)
    {
        this.context = context;
        this.penStroke = penStroke;
        this.palette = palette;
        PenDialog();
    }

    public void PenDialog()
    {
        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.penpicker);
        dialog.show();

        final SeekBar seekBar = dialog.findViewById(R.id.pen_picker_slider);
        final TextView textView = dialog.findViewById(R.id.nowPenWidth);
        final Button cancel = dialog.findViewById(R.id.btnCancel);
        final Button ok = dialog.findViewById(R.id.btnOk);
        final RadioGroup radioGroup = dialog.findViewById(R.id.penChoice);
        final RadioButton rbSolid = dialog.findViewById(R.id.rbSolid);
        final RadioButton rbDash = dialog.findViewById(R.id.rbDash);

        seekBar.setProgress((int)penStroke);
        textView.setText("  " + (int)penStroke);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView textView = dialog.findViewById(R.id.nowPenWidth);
                penStroke = seekBar.getProgress();
                textView.setText("  " + (int)penStroke);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        Paint.Style style = palette.paint.getStyle();
        if (style == Paint.Style.FILL)
            radioGroup.check(R.id.rbSolid);
        else
            radioGroup.check(R.id.rbDash);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbDash)
                    Toast.makeText(context, "PenSelected: Dash Line", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "PenSelected: Solid Line", Toast.LENGTH_SHORT).show();

                radioGroup.check(checkedId);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (palette != null)
                {
                    palette.paint.setStrokeWidth(penStroke);

                    if (rbSolid.isChecked())
                    {
                        palette.paint.setStyle(Paint.Style.FILL);
                        palette.paint.setPathEffect(null);
                    }
                    else
                    {
                        palette.paint.setStyle(Paint.Style.STROKE);
                        palette.setDashLine();
                    }
                }

                dialog.dismiss();
            }
        });
    }
}
