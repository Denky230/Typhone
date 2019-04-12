package com.stucom.grupo4.typhone.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.support.annotation.Nullable;
import android.view.View;

public class WordTimerView extends View {

    private int totalMs, msLeft;

    private final Paint paint;

    public WordTimerView(Context context) {
        this(context, null, 0);
    }
    public WordTimerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public WordTimerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // Initialize graphics paint
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
    }

    public void setTotalMs(int totalMs) {
        this.invalidate();
        this.totalMs = this.msLeft = totalMs;
    }
    public void updateMsLeft() {
        this.invalidate();

        if (msLeft <= 0) {
            listener.timesUp();
        } else {
            msLeft -= 1000;
        }
    }

    @Override protected void onDraw(Canvas canvas) {
        canvas.scale((float) getWidth() / totalMs, 1);
        canvas.drawRect(0, 0, msLeft, getHeight(), paint);
    }

    public interface WordTimerListener {
        void timesUp();
    }

    protected WordTimerListener listener;
    public void setWordTimerListener(WordTimerListener listener) {
        this.listener = listener;
    }
}