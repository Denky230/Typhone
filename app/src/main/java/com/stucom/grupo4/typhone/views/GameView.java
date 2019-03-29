package com.stucom.grupo4.typhone.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.support.annotation.Nullable;
import android.view.View;

public class GameView extends View {

    private final Paint brush;

    public GameView(Context context) {
        this(context, null, 0);
    }
    public GameView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // Initialize graphics brush
        brush = new Paint();
        brush.setColor(Color.RED);
        brush.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
         canvas.drawPaint(brush);
    }
}