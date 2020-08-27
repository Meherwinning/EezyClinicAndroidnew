package com.vempower.eezyclinic.fragments;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.AbstractFragmentActivity;
import com.vempower.eezyclinic.adapters.TimeSlotsListAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.views.AutoFitFixedRecyclerView;
import com.vempower.eezyclinic.views.MyCheckedTextViewRR;
 ;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.prolificinteractive.materialcalendarview.MaterialCalendarView.SELECTION_MODE_MULTIPLE;

/**
 * Created by satish on 6/12/17.
 */

public abstract class AbstractCalenderViewFragment extends AbstractFragment implements OnDateSelectedListener, OnMonthChangedListener {

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    //private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    static final String   DATE_DISPLAY_FORMAT_NEW="EEE, dd MMM yyyy";//"dd-MM-yyyy'\n'EEE"; //Tue, 3rd Apr 2018
    private static final SimpleDateFormat DATE_DISPLAY_FORMATTER = new SimpleDateFormat(DATE_DISPLAY_FORMAT_NEW);

    private static final TitleFormatter DEFAULT_TITLE_FORMATTER = new DateFormatTitleFormatter();
    static final String  SERVER_DATE_FORMAT_NEW="yyyy-MM-dd";//"2017-11-22";"2018-01-16"/ /15-12-2017 05:00 PM
    private static final SimpleDateFormat REQUEST_DATE_FORMATTER = new SimpleDateFormat(SERVER_DATE_FORMAT_NEW);


    private  TextView textView;
    private  TextView title_tv;

    private MaterialCalendarView widget;



    private View fragmentView;
    private TimeSlotsListAdapter adapter;
    private Button conform_time_bt;
    private MyCheckedTextViewRR today_ctv;
    private Calendar startCalendar;
    private Calendar endCalendar;
    private AutoFitFixedRecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.activity_basic, container, false);
       // setupSwipeRefreshLayout(fragmentView);
        myInit();

        return fragmentView;
    }
    protected abstract  void dateSelectFromCalender(String dateStr);
    protected abstract  void clickOnConfirmButton(String confirmDateTime);
   // abstract void  dateSelectFromCalender();

    private  String getFormattedDate(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        //2nd of march 2015
        int day=cal.get(Calendar.DATE);

        if(!((day>10) && (day<19)))
            switch (day % 10) {
                case 1:
                    return new SimpleDateFormat( "EEE, d'st' MMM yyyy").format(date);
                case 2:
                    return new SimpleDateFormat("EEE, d'nd' MMM yyyy").format(date);
                case 3:
                    return new SimpleDateFormat("EEE, d'rd' MMM yyyy").format(date);
                default:
                    return new SimpleDateFormat("EEE, d'th' MMM yyyy").format(date);
            }
        return new SimpleDateFormat("EEE, d'th' MMM yyyy").format(date);
    }

    public boolean isVarColumnGrid() {
        return true;
    }

    private void myInit() {

        recyclerView =fragmentView.findViewById(R.id.recycler_view);
       // Context context, int spanCount, @RecyclerView.Orientation int orientation, boolean reverseLayout
        GridLayoutManager layoutManager= new GridLayoutManager(MyApplication.getCurrentActivityContext(),4, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        widget =fragmentView.findViewById(R.id.calendarView);
        textView =fragmentView.findViewById(R.id. textView);
        title_tv =fragmentView.findViewById(R.id. title_tv);
        today_ctv = fragmentView.findViewById(R.id.today_ctv);

        conform_time_bt  =fragmentView.findViewById(R.id.conform_time_bt);
        conform_time_bt.setVisibility(View.GONE);

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



       // widget.setDynamicHeightEnabled(true);
        widget.setShowOtherDates(MaterialCalendarView.SHOW_OUT_OF_RANGE);
        widget.setTopbarVisible(false);
        //widget.setTileHeightDp((int)getResources().getDimension(R.dimen._11dp));

        widget.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);

        widget.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
               /* int dragthreshold = 10;

                int downX = 0;

                int downY = 0;*/

                switch (event.getAction()) {
                   /* case MotionEvent.ACTION_DOWN:
                        downX = (int) event.getRawX();

                        downY = (int) event.getRawY();

                        break;*/

                    case MotionEvent.ACTION_MOVE:
                       /* int distanceX = Math.abs((int) event.getRawX() - downX);

                        int distanceY = Math.abs((int) event.getRawY() - downY);

                        if (distanceY > distanceX && distanceY > dragthreshold) {
                            pager.getParent().requestDisallowInterceptTouchEvent(false);

                            mScrollView.getParent().requestDisallowInterceptTouchEvent(true);
                        } else if (distanceX > distanceY && distanceX > dragthreshold) {
                            pager.getParent().requestDisallowInterceptTouchEvent(true);

                            mScrollView.getParent().requestDisallowInterceptTouchEvent(false);
                        }*/

                        break;
                    case MotionEvent.ACTION_UP:
                       // mScrollView.getParent().requestDisallowInterceptTouchEvent(false);

                        widget.getParent().requestDisallowInterceptTouchEvent(false);

                        break;
                }

                return false;
            }
        });



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
       // (( TextView)fragmentView.findViewById(R.id.match_found_tv)).setText(orders.size()+"");

        if (adapter == null) {

            adapter = new TimeSlotsListAdapter(dateStr,slots);
            adapter.setOnDiasableDateListener(new TimeSlotsListAdapter.DiasableDateListener() {
                @Override
                public String getDisableDateTime() {
                    return getDiabledDateAndtime();
                }
            });
            recyclerView.setNestedScrollingEnabled(false);

            recyclerView.setAdapter(adapter);
        } else {
            adapter.setUpdatedList( dateStr,slots);
        }
        if(slots==null || slots.size()==0)
        {
            fragmentView.findViewById(R.id.no_matching_result_tv).setVisibility(View.VISIBLE);
            conform_time_bt.setVisibility(View.GONE);
        }else
        {
            fragmentView.findViewById(R.id.no_matching_result_tv).setVisibility(View.GONE);
            conform_time_bt.setVisibility(View.VISIBLE);
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

            return getFormattedDate(date.getDate());


        //return DATE_DISPLAY_FORMATTER.format(date.getDate());
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



    /*@Override
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

    }*/

    public String getDiabledDateAndtime() {
        return null;
    }
}
