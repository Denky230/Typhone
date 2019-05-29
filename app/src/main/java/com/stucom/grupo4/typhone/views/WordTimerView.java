package com.stucom.grupo4.typhone.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.support.annotation.Nullable;
import android.view.View;

public class WordTimerView extends View {

    private final int TIMER_INTERVAL_MILLISECONDS = 10;     // Timer will tick every... milliseconds
    private int totalMs, msLeft;

    private CountDownTimer timer;   // Bar timer
    private final Paint paint;      // Graphics paint

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

    public void startTimer(int totalMs) {
        // Make sure timer is not already running
        stopTimer();

        this.totalMs = this.msLeft = totalMs;
        timer = new CountDownTimer(totalMs, TIMER_INTERVAL_MILLISECONDS) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update word time left
                updateMsLeft((int) millisUntilFinished);
            }

            @Override
            public void onFinish() {
                updateMsLeft(0);
                listener.timesUp();
            }
        }.start();
    }
    public void resumeTimer() {
        startTimer(msLeft);
    }
    public void stopTimer() {
        if (timer == null) return;
        this.timer.cancel();
    }

    public void updateMsLeft(int millis) {
        this.invalidate();
        msLeft = millis;
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