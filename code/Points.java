package com.example.althea;

import android.graphics.Color;
import android.graphics.Paint;

import java.io.Serializable;

public class Points implements Serializable
{
    protected float x;
    protected float y;
    protected boolean odd;
    protected Paint colorState;

    Points(float x, float y, boolean odd, Paint colorState)
    {
        this.x = x;
        this.y = y;
        this.odd = odd;
        this.colorState = colorState;
    }
}
