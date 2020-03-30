package com.example.althea;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity
{
    private Context mainContext = this;
    protected CustomView myView = null;
    protected Palette palette = null;
    protected int nowPenColor = Color.BLACK;
    protected float nowPenStroke = 10;
    protected float penWidth = 10;
    protected static int imgCount = 0;
    private Activity activity = this;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        setContentView(R.layout.activity_main);

        //Menu
        final Button btnColor = findViewById(R.id.btnColor);
        ImageButton btnEraser = findViewById(R.id.btnEraser);
        ImageButton btnPen = findViewById(R.id.btnPen);
        ImageButton btnRedo = findViewById(R.id.btnRedo);
        ImageButton btnUndo = findViewById(R.id.btnUndo);
        ImageButton btnEtc = findViewById(R.id.btnEtc);

        //Color
        btnColor.setBackgroundColor(nowPenColor);
        btnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                palette.paint.setStrokeWidth(penWidth);
                if (palette != null)
                    new ColorPicker(mainContext, palette.paint.getColor(), btnColor, palette);
                else
                    new ColorPicker(mainContext, nowPenColor, btnColor, null);
            }
        });

        //Pen
        btnPen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                palette.paint.setStrokeWidth(penWidth);
                if (palette != null)
                    new PenPicker(mainContext, palette.paint.getStrokeWidth(), palette);
                else
                    new PenPicker(mainContext, nowPenStroke, null);
            }
        });

        //Eraser
        btnEraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (palette != null)
                {
                    btnColor.setBackgroundColor(Color.WHITE);
                    palette.paint.setColor(Color.WHITE);

                    penWidth = palette.paint.getStrokeWidth();
                    palette.paint.setStrokeWidth(penWidth * 3);
                }
            }
        });

        //Undo
        btnUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myView != null)
                    myView.onClickUndo();
            }
        });

        //Redo
        btnRedo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myView != null)
                    myView.onClickRedo();
            }
        });

        //Etc
        btnEtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (palette != null)
                    new EtcMenu(mainContext, myView, imgCount++, activity);
            }
        });
    }

    //lt call focus of window altered. App starting, it's true.
    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        if (hasFocus)
        {
            //CustomView Creating
            if (myView != null)
                return;

            LinearLayout Canvas = findViewById(R.id.Canvas);
            if (Canvas != null)
            {
                Rect rect = new Rect(0, 0, Canvas.getMeasuredWidth(), Canvas.getMeasuredHeight());
                palette = new Palette(rect);
                myView = new CustomView(this, palette);

                LinearLayout.LayoutParams mlp = (LinearLayout.LayoutParams) myView.getLayoutParams();
                if (mlp == null)
                {
                    mlp = new LinearLayout.LayoutParams(Canvas.getMeasuredWidth(), Canvas.getMeasuredHeight());
                }
                else
                {
                    mlp.width = Canvas.getMeasuredWidth();
                    mlp.height = Canvas.getMeasuredHeight();
                }

                mlp.weight = 0.9f;
                ((LinearLayout.LayoutParams) Canvas.getLayoutParams()).weight = 0;

                Canvas.addView(myView, 0, mlp);
            }
        }
    }

    //Backspace button
    @Override
    public void onBackPressed()
    {
        return;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    //Saving Activity
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }
}