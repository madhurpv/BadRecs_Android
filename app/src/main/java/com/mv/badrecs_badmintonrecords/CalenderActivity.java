package com.mv.badrecs_badmintonrecords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalenderActivity extends AppCompatActivity implements OnDateSelectedListener {


    List<String> playedDateList;


    MaterialCalendarView calendarView;

    SharedPreferences sharedPreferences;
    Gson gson;
    AllObservationClass allObservationsClassObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);




        sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        gson = new Gson();
        String json = sharedPreferences.getString("allObservationsClass", "");
        allObservationsClassObj = gson.fromJson(json, AllObservationClass.class);

        playedDateList = allObservationsClassObj.getDates();


        calendarView = findViewById(R.id.calenderView);
        calendarView.setShowOtherDates(MaterialCalendarView.SHOW_ALL);

        //final LocalDate min = getLocalDate("2019-01-01");
        //final LocalDate max = getLocalDate("2019-12-30");

        //calendarView.state().edit().setMinimumDate(min).setMaximumDate(max).commit();

        setEvent(playedDateList);

        calendarView.invalidateDecorators();
        calendarView.setOnDateChangedListener(this);

    }


    void setEvent(List<String> dateList) {
        List<LocalDate> localDateList = new ArrayList<>();

        for (String string : dateList) {
            LocalDate calendar = getLocalDate(string);
            if (calendar != null) {
                localDateList.add(calendar);
            }
        }
        List<CalendarDay> datesLeft = new ArrayList<>();
        List<CalendarDay> datesCenter = new ArrayList<>();
        List<CalendarDay> datesRight = new ArrayList<>();
        List<CalendarDay> datesCircle = new ArrayList<>();

        for (LocalDate localDate : localDateList) {
            boolean right = false;
            boolean left = false;
            for (LocalDate day1 : localDateList) {
                if (localDate.isEqual(day1.plusDays(1))) {
                    left = true;
                }
                if (day1.isEqual(localDate.plusDays(1))) {
                    right = true;
                }
            }

            if (left && right) {
                datesCenter.add(CalendarDay.from(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())));
            } else if (left) {
                datesLeft.add(CalendarDay.from(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())));
            } else if (right) {
                datesRight.add(CalendarDay.from(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())));
            } else {
                datesCircle.add(CalendarDay.from(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())));
            }
        }

        setSelectionOnCalender(datesCenter, R.drawable.calender_highlight_center);
        setSelectionOnCalender(datesLeft, R.drawable.calender_highlight_left);
        setSelectionOnCalender(datesRight, R.drawable.calender_highlight_right);
        setSelectionOnCalender(datesCircle, R.drawable.calender_highlight_circle);
    }

    void setSelectionOnCalender(List<CalendarDay> calendarDayList, int drawable) {
        calendarView.addDecorators(new EventDecorator(CalenderActivity.this, drawable, calendarDayList));
    }

    LocalDate getLocalDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        try {
            Date input = sdf.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(input);
            return LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        Log.d("selected", "" + selected);
        if (selected == true) {

        }
    }

}