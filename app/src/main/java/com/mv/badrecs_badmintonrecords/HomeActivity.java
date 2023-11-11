package com.mv.badrecs_badmintonrecords;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeRecyclerAdapter.ItemClickListener{


    public static TextView noDataToShowTextView;
    TextView dateTextView;

    FloatingActionButton addNewObservation;
    public static HomeRecyclerAdapter adapter; // Edited from AddMatch.java
    RecyclerView homeRecyclerView;

    public static List<String> titles;      // Edited from AddMatch.java
    public static List<Long> times;        // Edited from AddMatch.java
    public static List<String> scores;    // Edited from AddMatch.java
    public static List<Integer> wins;    // Edited from AddMatch.java

    SharedPreferences sharedPreferences;
    Gson gson;
    AllObservationClass allObservationsClassObj;
    String todaysDate;

    FloatingActionButton floatingActionMoreDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        noDataToShowTextView = findViewById(R.id.noDataToShowTextView);
        dateTextView = findViewById(R.id.dateTextView);
        addNewObservation = findViewById(R.id.addNewObservation);

        homeRecyclerView = findViewById(R.id.homeRecyclerView);


        sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        gson = new Gson();
        String json = sharedPreferences.getString("allObservationsClass", "");
        allObservationsClassObj = gson.fromJson(json, AllObservationClass.class);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        todaysDate = localDateTime.format(dateTimeFormatter);
        dateTextView.setText(todaysDate);
        if(allObservationsClassObj.containsDate(todaysDate)){
            titles = allObservationsClassObj.getDayClass(todaysDate).getMatchesTitles();
            times = allObservationsClassObj.getDayClass(todaysDate).getMatchesTimes();
            scores = allObservationsClassObj.getDayClass(todaysDate).getMatchesFinalScores();
            wins = allObservationsClassObj.getDayClass(todaysDate).getMatchesWins();
            noDataToShowTextView.setVisibility(View.GONE);
        }
        else {
            titles = new ArrayList<>();
            times = new ArrayList<>();
            scores = new ArrayList<>();
            wins = new ArrayList<>();
            noDataToShowTextView.setVisibility(View.VISIBLE);
        }

        homeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HomeRecyclerAdapter(this, titles, times, scores, wins);
        adapter.setClickListener(this);
        homeRecyclerView.setAdapter(adapter);

        Log.d("QWER", "All Titles : " + titles);
        //Log.d("QWER", "AllDates : " + allObservationsClassObj.containsDate);
        //Log.d("QWER", "DayClass : " + allObservationsClassObj.getDayClass(todaysDate).no_of_matches);
        //Log.d("QWER", "Titles : " + allObservationsClassObj.getDayClass(todaysDate).getMatchesTitles());
        //Log.d("QWER", "Wins : " + allObservationsClassObj.getDayClass(todaysDate).getMatchesWins());

        adapter.notifyDataSetChanged();

        addNewObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddMatch.class);
                intent.putExtra("todaysDate", todaysDate);
                startActivity(intent);
            }
        });


        floatingActionMoreDetails = findViewById(R.id.floatingActionMoreDetails);
        floatingActionMoreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(HomeActivity.this);
                dialog.setContentView(R.layout.dialog_moreoptions);

                CardView dialogCancelCardView = dialog.findViewById(R.id.dialogCancelCardView);
                CardView dialogCalenderCardView = dialog.findViewById(R.id.dialogCalenderCardView);
                CardView dialogDeleteCardView = dialog.findViewById(R.id.dialogDeleteCardView);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialogCancelCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialogCalenderCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HomeActivity.this, CalenderActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });



                dialog.show();
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.setContentView(R.layout.dialog_homeactivity);

        CardView dialogCancelCardView = dialog.findViewById(R.id.dialogCancelCardView);
        CardView dialogDetailsCardView = dialog.findViewById(R.id.dialogDetailsCardView);
        CardView dialogDeleteCardView = dialog.findViewById(R.id.dialogDeleteCardView);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogCancelCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialogDetailsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : Open new activity to show all details
            }
        });

        dialogDeleteCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titles.remove(position);
                times.remove(position);
                wins.remove(position);
                scores.remove(position);
                adapter.notifyItemRemoved(position);
                if(titles.size() == 0){
                    noDataToShowTextView.setVisibility(View.VISIBLE);
                    allObservationsClassObj.deleteDayClass(todaysDate);
                }
                else{
                   allObservationsClassObj.getDayClass(todaysDate).delete_match(position);;
                }
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                String allObservationsClassJson = gson.toJson(allObservationsClassObj);
                myEdit.putString("allObservationsClass", allObservationsClassJson);
                myEdit.apply();
                dialog.dismiss();
            }
        });

        dialog.show();
    }




}