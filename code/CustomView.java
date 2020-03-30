package com.example.althea;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomView extends SurfaceView implements SurfaceHolder.Callback, Runnable
{
    private SurfaceHolder holder = null;
    private boolean mRunning = false;
    private Thread thread = null;
    private Context context = null;

    //User touch
    private ArrayList<Points> points = null;
    //Undo, Redo
    private ArrayList<Points> undoPoints = null;

    //Canvas, paint, bitmap setting
    private Palette palette = null;

    public CustomView(Context context, Palette palette)
    {
        super(context);
        this.context = context;
        this.palette = palette;
        points = new ArrayList<Points>();
        undoPoints = new ArrayList<Points>();

        holder = getHolder();
        holder.addCallback(this);
    }

    public Palette getPalette()
    {
        return palette;
    }

    public void reset()
    {
        points.clear();
        points = new ArrayList<Points>();
        undoPoints.clear();
        undoPoints = new ArrayList<Points>();
    }

    @Override
    protected void onDetachedFromWindow()
    {
        //Return Resource
        if (palette.bitmap != null)
            palette.bitmap.recycle();
        palette.bitmap = null;
        points.clear();
        palette = null;
        super.onDetachedFromWindow();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        mRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        mRunning = false;
        while (retry)
        {
            try
            {
                thread.join();
                retry = false;
            }
            catch (Exception e)
            {
            }
        }
    }

    /** Undo
     * */
    public void onClickUndo()
    {
        if (points.size() > 0)
        {
            undoPoints.add(points.remove(points.size() - 1));
            invalidate();
        }
        else
            Toast.makeText(context, "Can't undo anymore", Toast.LENGTH_SHORT).show();
    }

    /** Redo
     * */
    public void onClickRedo()
    {
        if (undoPoints.size() > 0)
        {
            points.add(undoPoints.remove(undoPoints.size() - 1));
            invalidate();
        }
        else
            Toast.makeText(context, "Can't redo anymore", Toast.LENGTH_SHORT).show();
    }

    /** Touch
     * */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            points.add(new Points(event.getX(), event.getY(), true, new Paint(palette.paint)));
        else if (event.getAction() == MotionEvent.ACTION_MOVE)
            points.add(new Points(event.getX(), event.getY(), false, new Paint(palette.paint)));

        return true;
    }

    /** Drawing
     * */
    @Override
    public synchronized void run()
    {
        Canvas canvas;

        while (mRunning == true)
        {
            canvas = holder.lockCanvas();
            if (canvas != null)
            {
                canvas.drawColor(Color.WHITE);
                canvas.drawBitmap(palette.bitmap, 0, 0, null);

                if (!points.isEmpty())
                {
                    canvas.drawPoint(points.get(0).x, points.get(0).y, points.get(0).colorState);
                    //palette.canvas.drawPoint(points.get(0).x, points.get(0).y, points.get(0).colorState);
                }

                for (int i = 1; i < points.size(); i++)
                    if (points.get(i).odd)
                    {
                        canvas.drawPoint(points.get(i).x, points.get(i).y, points.get(i).colorState);
                        //palette.canvas.drawPoint(points.get(i).x, points.get(i).y, points.get(i).colorState);
                    }
                    else
                    {
                        canvas.drawLine(points.get(i - 1).x, points.get(i - 1).y, points.get(i).x, points.get(i).y, points.get(i).colorState);
                        //palette.canvas.drawLine(points.get(i - 1).x, points.get(i - 1).y, points.get(i).x, points.get(i).y, points.get(i).colorState);
                    }

                holder.unlockCanvasAndPost(canvas);
            }
        }
    }
}