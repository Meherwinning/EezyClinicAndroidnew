package com.vempower.eezyclinic.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;
import com.vempower.eezyclinic.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Shows off the most basic usage
 */
public class BasicActivity extends AppCompatActivity implements OnDateSelectedListener, OnMonthChangedListener {

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private static final TitleFormatter DEFAULT_TITLE_FORMATTER = new DateFormatTitleFormatter();

    //  private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
   /* @BindView(R.id.calendarView)
    MaterialCalendarView widget;

    @BindView(R.id.textView)
    */
  TextView textView,title_tv;

    MaterialCalendarView widget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
       // ButterKnife.bind(this);
         widget =findViewById(R.id.calendarView);
        textView =findViewById(R.id. textView);
        title_tv =findViewById(R.id. title_tv);



        widget.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {

            }
        });

        widget.setWeekDayLabels(getResources().getStringArray(R.array.days));


        Calendar startCalendar=Calendar.getInstance();


        Calendar endCalendar=Calendar.getInstance();
        endCalendar.set(Calendar.YEAR,startCalendar.get(Calendar.YEAR)+1);



       CalendarDay start= CalendarDay.from(startCalendar);
        CalendarDay end= CalendarDay.from(endCalendar);
        widget.newState().isCacheCalendarPositionEnabled(true).setMaximumDate(endCalendar).setMinimumDate(startCalendar).commit();
        widget.selectRange(start,end);







        widget.setOnDateChangedListener(this);
        widget.setOnMonthChangedListener(this);


        widget.setSelectedDate(startCalendar);
        widget.setCurrentDate(startCalendar);


        //Setup initial text


       /* widget.addDecorators(
                new MySelectorDecorator(this),
                new HighlightWeekendsDecorator(),
                oneDayDecorator
        );*/

       widget.setOnMyDayChangeLister(new MaterialCalendarView.MyDayChangeLister() {
           @Override
           public void changeDate(CalendarDay calendarDay) {
               final CharSequence newTitle = DEFAULT_TITLE_FORMATTER.format(calendarDay);
               title_tv.setText(newTitle);
           }
       });


        textView.setText(getSelectedDatesString());

    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @Nullable CalendarDay date, boolean selected) {
        textView.setText(getSelectedDatesString());
        final CharSequence newTitle = DEFAULT_TITLE_FORMATTER.format(date);
        title_tv.setText(newTitle);

    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        //noinspection ConstantConditions
        //getSupportActionBar().setTitle(FORMATTER.format(date.getDate()));
    }

    private String getSelectedDatesString() {
        CalendarDay date = widget.getSelectedDate();
        if (date == null) {
            return "No Selection";
        }
        return FORMATTER.format(date.getDate());
    }

    public void onFutureButtonClick(View view)
    {
        if(widget!=null)
        {
            widget.onFutureButtonClick();
        }
    }
    public void onPastButtonClick(View view)
    {
        if(widget!=null)
        {
            widget.onPastButtonClick();
        }
    }
}
