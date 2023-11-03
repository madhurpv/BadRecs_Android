package com.mv.badrecs_badmintonrecords;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class AllObservationClass {

    List<DayClass> dayClassList = new ArrayList<>();
    HashSet<String> containsDate = new HashSet<>();     // Contains These Dates



    boolean containsDate(String date){
        return containsDate.contains(date);
    }

    DayClass getDayClass(String date){
        for(int i=0; i<dayClassList.size(); i++){
            if(dayClassList.get(i).date.equals(date)){
                return dayClassList.get(i);
            }
        }
        return null;
    }

    public void addDayClass(DayClass dayClass){
        dayClassList.add(dayClass);
        containsDate.add(dayClass.date);
    }

    public void deleteDayClass(String date){
        if(containsDate.contains(date)){
            containsDate.remove(date);
            for(int i=0; i<dayClassList.size(); i++){
                if(dayClassList.get(i).date.equals(date)){
                    dayClassList.remove(i);
                    break;
                }
            }
        }
        else{
            Log.w("QWER", "Error : Cannot delete date " + date + ", from AllObservationsClass.java");
        }
    }


}
