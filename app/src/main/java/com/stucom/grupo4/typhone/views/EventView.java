package com.stucom.grupo4.typhone.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.stucom.grupo4.typhone.control.GameController;
import com.stucom.grupo4.typhone.model.modifiers.Modifier;
import com.stucom.grupo4.typhone.tools.Tools;

import java.util.ArrayList;
import java.util.List;

public class EventView extends View {

    // Event states
    public enum EventState {
        EVENT_DOWNTIME(4),
        EVENT_ANNOUNCEMENT(3),
        EVENT_ACTIVE(3),
        MODIFIER_ACTIVE(10);

        int seconds;    // state duration
        EventState(int seconds) { this.seconds = seconds; }
    }
    private EventState eventState;
    public int nextEventStateSeconds;

    private List<Modifier> eventModifiers;

    private GameController controller;

    private Rect rect;

    public EventView(Context context) {
        this(context, null, 0);
    }
    public EventView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public EventView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        eventModifiers = new ArrayList<>();
        controller = GameController.getInstance();
        setEventState(EventState.values()[0]);

        final int size = 200;
        rect = new Rect(0, 0, size, size);
    }

    // Event states
    public void nextEventState() {
        int currStateOrdinal = this.eventState.ordinal();
        int nextStateOrdinal = ++currStateOrdinal == EventState.values().length ? 0 : currStateOrdinal;
        setEventState(EventState.values()[nextStateOrdinal]);
    }
    public void setEventState(EventState eventState) {
        this.eventState = eventState;
        this.nextEventStateSeconds += eventState.seconds;
        switch (eventState) {
            case EVENT_DOWNTIME: setEventDowntime(); break;
            case EVENT_ANNOUNCEMENT: setEventAnnouncement(); break;
            case EVENT_ACTIVE: setEventActive(); break;
            case MODIFIER_ACTIVE: setModifierActive(); break;
        }

        Tools.log(eventState.name());
    }

    private void setEventDowntime() {
        // Cancel active modifiers
        controller.clearModifiers();

        Tools.log("mods cleared");
    }
    private void setEventAnnouncement() {
        // Get game modifiers pool
        Modifier[] modifiers = controller.getModifiers();

        // Pull random game modifier
        int rand = (int) (Math.random() * modifiers.length);
        Modifier randomModifier = modifiers[rand];
        // Add modifier to event modifiers
        eventModifiers.add(randomModifier);

        // Announce pulled modifier
        this.invalidate();

        Tools.log("mod pulled: " + randomModifier.getClass().getSimpleName());
    }
    private void setEventActive() {
        // Announce event starting
        this.invalidate();
    }
    private void setModifierActive() {
        // Add event modifiers to game active modifiers
        for (Modifier modifier : eventModifiers) {
            controller.addModifier(modifier);
        }
        // Clear event modifiers
        eventModifiers.clear();
        // Clear event view
        this.invalidate();
    }

    @Override protected void onDraw(Canvas canvas) {

        // Avoid onDraw when there's no event going on
        // since there is nothing to draw
        if (eventModifiers.isEmpty()) return;

        // Get icon to draw
        Bitmap b = BitmapFactory.decodeResource(getResources(), eventModifiers.get(0).getIconResID());
        if (b == null) return;

        // Draw event modifier icon
        canvas.translate(getWidth() / 2 - rect.width() / 2, getHeight() / 2 - rect.height() / 2);
        canvas.drawBitmap(b,null, rect,null);
    }
}
