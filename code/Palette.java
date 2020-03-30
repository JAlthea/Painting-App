package com.example.althea;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;

public class Palette
{
    protected Paint paint = null;
    protected Bitmap bitmap = null;
    protected Canvas canvas = null;
    protected Rect rect = null;

    public Palette(Rect rect)
    {
        paint = new Paint();
        //variable
        paint.setColor(Color.BLACK);
        paint.setAlpha(255);
        paint.setStrokeWidth(10);
        //fixed
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
        paint.setDither(true);

        this.rect = rect;
        bitmap = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas();
        canvas.setBitmap(bitmap);
        canvas.drawColor(Color.WHITE);
    }

    public void setDashLine()
    {
        paint.setPathEffect(new DashPathEffect(new float[]{paint.getStrokeWidth() * 2, paint.getStrokeWidth() * 2}, 0));
    }
}
