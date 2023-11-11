package com.mv.badrecs_badmintonrecords;

import android.content.Context;
import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import androidx.core.content.res.ResourcesCompat;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.HashSet;
import java.util.List;

public class EventDecorator implements DayViewDecorator {
    Context context;
    private final int drawable;
    private final HashSet<CalendarDay> dates;

    public EventDecorator(Context context, int drawable, List<CalendarDay> calendarDays1) {
        this.context = context;
        this.drawable = drawable;
        this.dates = new HashSet<>(calendarDays1);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        // apply drawable to dayView
        view.setSelectionDrawable(ResourcesCompat.getDrawable(context.getResources(), drawable, null));
        view.addSpan(new ForegroundColorSpan(Color.WHITE));
    }
}