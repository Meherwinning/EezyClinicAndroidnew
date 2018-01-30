package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.TimeSlotsListAdapter;
import com.vempower.eezyclinic.views.MyCheckedTextViewRR;
import com.vempower.eezyclinic.views.MyTextViewRR;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.prolificinteractive.materialcalendarview.MaterialCalendarView.SELECTION_MODE_MULTIPLE;

/**
 * Created by satish on 6/12/17.
 */

public abstract class AbstractCalenderViewFragment extends SwipedAutoFitRecyclerViewFragment implements OnDateSelectedListener, OnMonthChangedListener {

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    //private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    static final String   DATE_DISPLAY_FORMAT_NEW="dd-MM-yyyy'\n'EEE";
    private static final SimpleDateFormat DATE_DISPLAY_FORMATTER = new SimpleDateFormat(DATE_DISPLAY_FORMAT_NEW);

    private static final TitleFormatter DEFAULT_TITLE_FORMATTER = new DateFormatTitleFormatter();
    static final String  SERVER_DATE_FORMAT_NEW="yyyy-MM-dd";//"2017-11-22";"2018-01-16"/ /15-12-2017 05:00 PM
    private static final SimpleDateFormat REQUEST_DATE_FORMATTER = new SimpleDateFormat(SERVER_DATE_FORMAT_NEW);


    private MyTextViewRR textView;
    private MyTextViewRR title_tv;

    private MaterialCalendarView widget;



    private View fragmentView;
    private TimeSlotsListAdapter adapter;
    private Button conform_time_bt;
    private MyCheckedTextViewRR today_ctv;
    private Calendar startCalendar;
    private Calendar endCalendar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.activity_basic, container, false);
        setupSwipeRefreshLayout(fragmentView);
        myInit();

        return fragmentView;
    }
    protected abstract  void dateSelectFromCalender(String dateStr);
    protected abstract  void clickOnConfirmButton(String confirmDateTime);
   // abstract void  dateSelectFromCalender();

    public boolean isVarColumnGrid() {
        return true;
    }

    private void myInit() {
        widget =fragmentView.findViewById(R.id.calendarView);
        textView =fragmentView.findViewById(R.id. textView);
        title_tv =fragmentView.findViewById(R.id. title_tv);
        today_ctv = fragmentView.findViewById(R.id.today_ctv);

        conform_time_bt  =fragmentView.findViewById(R.id.conform_time_bt);

        conform_time_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter == null)
                {
                    clickOnConfirmButton(null);
                    return;
                }
                clickOnConfirmButton(adapter.getPreSelectedDateTime());


            }
        });



        widget.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {

            }
        });

       fragmentView.findViewById(R.id.button_past).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onPastButtonClick(view);
           }
       });
        fragmentView.findViewById(R.id.button_future).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFutureButtonClick(view);
            }
        });


        widget.setWeekDayLabels(getResources().getStringArray(R.array.days));


         startCalendar=Calendar.getInstance();


         endCalendar=Calendar.getInstance();
        endCalendar.set(Calendar.YEAR,startCalendar.get(Calendar.YEAR)+10);



        CalendarDay start= CalendarDay.from(startCalendar);
        CalendarDay end= CalendarDay.from(endCalendar);
        widget.newState().isCacheCalendarPositionEnabled(true).setMaximumDate(endCalendar).setMinimumDate(startCalendar).commit();
        widget.selectRange(start,end);

        widget.setOnDateChangedListener(this);
        widget.setOnMonthChangedListener(this);




        widget.setOnMyDayChangeLister(new MaterialCalendarView.MyDayChangeLister() {
            @Override
            public void changeDate(CalendarDay calendarDay) {
                final CharSequence newTitle = DEFAULT_TITLE_FORMATTER.format(calendarDay);
                title_tv.setText(newTitle);
            }
        });



        widget.setDynamicHeightEnabled(true);
        widget.setShowOtherDates(MaterialCalendarView.SHOW_OUT_OF_RANGE);
        widget.setTopbarVisible(false);
        widget.setTileHeightDp((int)getResources().getDimension(R.dimen._11dp));

        widget.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);



        fragmentView.findViewById(R.id.pre_day_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();

                widget.getSelectedDate().copyTo(calendar);
                if(calendar!=null) {
                    calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);


                    if(compareToDay(startCalendar.getTime(),calendar.getTime())<=0)
                    {
                        setCalderDay(calendar);
                    }
                }
            }
        });

        fragmentView.findViewById(R.id.next_day_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();

                widget.getSelectedDate().copyTo(calendar);
                if(calendar!=null) {
                    calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);

                    if(compareToDay(endCalendar.getTime(),calendar.getTime())>=0)
                    {
                        setCalderDay(calendar);
                    }
                }
            }
        });


        today_ctv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!today_ctv.isChecked())
                {
                    setCurrentDate();
                }

            }
        });



    }

    protected void setCurrentDate()
    {
        setCalderDay(Calendar.getInstance());
    }

    public  int compareToDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date1).compareTo(sdf.format(date2));
    }

    protected void setCalderDay(Calendar calendar)
    {
        if((compareToDay(calendar.getTime(), Calendar.getInstance().getTime()))<0)
        {
            calendar= Calendar.getInstance();
        }

        today_ctv.setChecked(false);
        widget.setSelectedDate(calendar);
        widget.setCurrentDate(calendar);

        computeTodayButton(calendar);


        String dateStr= getSelectedServerRequestDateString();
        callTimeSlotsMapper(dateStr);
        title_tv.setText(getSelectedMonthString(widget.getSelectedDate()));
        textView.setText(getSelectedDatesString());
    }

    private void computeTodayButton(Calendar calendar) {
        if(compareToDay(Calendar.getInstance().getTime(),calendar.getTime())==0)
        {
            today_ctv.setChecked(true);
        }else
        {
            today_ctv.setChecked(false);
        }
    }

    public void setOrderItemsToAdapter(String dateStr,List<String> slots) {
        hideProgressView();
       // ((MyTextViewRR)fragmentView.findViewById(R.id.match_found_tv)).setText(orders.size()+"");

        if (adapter == null) {

            adapter = new TimeSlotsListAdapter(dateStr,slots);
            adapter.setOnDiasableDateListener(new TimeSlotsListAdapter.DiasableDateListener() {
                @Override
                public String getDisableDateTime() {
                    return getDiabledDateAndtime();
                }
            });

            recyclerView.setAdapter(adapter);
        } else {
            adapter.setUpdatedList( dateStr,slots);
        }
        if(slots==null || slots.size()==0)
        {
            fragmentView.findViewById(R.id.no_matching_result_tv).setVisibility(View.VISIBLE);
        }else
        {
            fragmentView.findViewById(R.id.no_matching_result_tv).setVisibility(View.GONE);
        }




    }

    private void callTimeSlotsMapper(String dateStr) {
        dateSelectFromCalender(dateStr);

    }

    @Override
    View getFragemtView() {
        return fragmentView;
    }


    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @Nullable CalendarDay date, boolean selected) {
        Calendar calendar=Calendar.getInstance();
        date.copyTo(calendar);

        computeTodayButton(calendar);

        textView.setText(getSelectedDatesString());
        String dateStr= getSelectedServerRequestDateString();
        callTimeSlotsMapper(dateStr);
        // final CharSequence newTitle = DEFAULT_TITLE_FORMATTER.format(date);
        title_tv.setText(getSelectedMonthString(date));

    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        //noinspection ConstantConditions
        title_tv.setText(getSelectedMonthString(date));
        //getSupportActionBar().setTitle(FORMATTER.format(date.getDate()));
    }

    private String getSelectedDatesString() {
        CalendarDay date = widget.getSelectedDate();
        if (date == null) {
            return "-";
        }
        return DATE_DISPLAY_FORMATTER.format(date.getDate());
    }
    private String getSelectedServerRequestDateString() {
        CalendarDay date = widget.getSelectedDate();
        if (date == null) {
            return "-";
        }
        return REQUEST_DATE_FORMATTER.format(date.getDate());
    }

    private String getSelectedMonthString(CalendarDay date) {
        // CalendarDay date = widget.getSelectedDate();
        if (date == null) {
            return "-";
        }
        return DEFAULT_TITLE_FORMATTER.format(date).toString().toUpperCase();
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



    @Override
    protected boolean isCheckTabletOrNot() {
        return false;
    }

    @Override
    protected void fromTopScroll() {
        if (swipeLayout != null) {
            swipeLayout.setRefreshing(false);
        }

    }

    @Override
    protected void fromBottomScroll() {
        if (swipeLayout != null) {
            swipeLayout.setRefreshing(false);
        }

    }

    public String getDiabledDateAndtime() {
        return null;
    }
}
