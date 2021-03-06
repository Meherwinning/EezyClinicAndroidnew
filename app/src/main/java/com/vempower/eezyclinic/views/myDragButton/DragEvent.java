package com.vempower.eezyclinic.views.myDragButton;

import android.graphics.PointF;
import androidx.annotation.NonNull;
import android.view.MotionEvent;

public class DragEvent {

    @NonNull
    public final PointF start;

    @NonNull
    public final MotionEvent motionEvent;

    public DragEvent(@NonNull PointF start, @NonNull MotionEvent motionEvent) {
        this.start = start;
        this.motionEvent = motionEvent;
    }
}
