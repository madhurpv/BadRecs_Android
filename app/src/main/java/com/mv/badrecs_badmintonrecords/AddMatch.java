package com.mv.badrecs_badmintonrecords;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AddMatch extends AppCompatActivity {


    TextView nameTextView;
    EditText opponent1EditText;
    EditText teamMateEditText;
    EditText opponent2EditText;
    SeekBar seekBarWin;
    EditText descriptionEditText;
    TableLayout tableLayoutDisplay;
    ImageView imageViewRemoveEntry;
    ImageView imageViewAddEntry;
    EditText scoreMyEditText;
    EditText scoreOpponentEditText;
    CardView addButtonCardView;

    List<String> tableViewMyScoreEntries = new ArrayList<>();
    List<String> tableViewOpponentScoreEntries = new ArrayList<>();

    // TODO : Add warning if length of scores = 0

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_match);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

        nameTextView = findViewById(R.id.nameTextView);
        opponent1EditText = findViewById(R.id.opponent1EditText);
        teamMateEditText = findViewById(R.id.teamMateEditText);
        opponent2EditText = findViewById(R.id.opponent2EditText);
        seekBarWin = findViewById(R.id.seekBarWin);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        tableLayoutDisplay = findViewById(R.id.tableLayoutDisplay);
        imageViewRemoveEntry = findViewById(R.id.imageViewRemoveEntry);
        imageViewAddEntry = findViewById(R.id.imageViewAddEntry);
        scoreMyEditText = findViewById(R.id.scoreMyEditText);
        scoreOpponentEditText = findViewById(R.id.scoreOpponentEditText);
        addButtonCardView = findViewById(R.id.addButtonCardView);


        nameTextView.setText(sharedPreferences.getString("Name", "ERROR").concat(" vs "));


        Gson gson = new Gson();
        String statsClassJson = sharedPreferences.getString("statsClass", "");
        StatsClass statsClass = gson.fromJson(statsClassJson, StatsClass.class);
        List<String> friendsList = statsClass.get_friends_list();

        String todaysDate = getIntent().getStringExtra("todaysDate");


        imageViewAddEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableViewMyScoreEntries.add(scoreMyEditText.getText().toString() + "  ");
                tableViewOpponentScoreEntries.add("  " + scoreOpponentEditText.getText().toString());
                update_TableLayout();
            }
        });

        imageViewRemoveEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tableViewMyScoreEntries.size() > 0){
                    tableViewMyScoreEntries.remove(tableViewMyScoreEntries.size()-1);
                    tableViewOpponentScoreEntries.remove(tableViewOpponentScoreEntries.size()-1);
                    update_TableLayout();
                }
            }
        });

        addButtonCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(opponent1EditText.getText().toString().isEmpty()){
                    Toast.makeText(AddMatch.this, "Add opponents name!", Toast.LENGTH_SHORT).show();
                }
                MatchClass newMatch = new MatchClass();
                newMatch.description = descriptionEditText.getText().toString();
                if(seekBarWin.getProgress() == 0){
                    newMatch.win = MatchClass.MATCH_WIN; // WIN
                }
                else if(seekBarWin.getProgress() == 2){
                    newMatch.win = MatchClass.MATCH_LOSE;
                }
                else{
                    newMatch.win = MatchClass.MATCH_UNDECIDED;
                }
                Log.d("QWER", "Seekbar : " + seekBarWin.getProgress());
                if(opponent2EditText.getText().toString().isEmpty() && teamMateEditText.getText().toString().isEmpty()){
                    newMatch.player1Name = sharedPreferences.getString("Name", "ERROR");
                    newMatch.title = sharedPreferences.getString("Name", "ERROR") + " vs " + opponent1EditText.getText().toString();
                    newMatch.opponent1Name = opponent1EditText.getText().toString();
                }
                else if(opponent2EditText.getText().toString().isEmpty()){
                    newMatch.title = sharedPreferences.getString("Name", "ERROR") + " &" + teamMateEditText.getText().toString() + " vs " + opponent1EditText.getText().toString();
                    newMatch.player1Name = sharedPreferences.getString("Name", "ERROR");
                    newMatch.opponent1Name = opponent1EditText.getText().toString();
                    newMatch.player2Name = teamMateEditText.getText().toString();
                }
                else if(teamMateEditText.getText().toString().isEmpty()){
                    newMatch.title = sharedPreferences.getString("Name", "ERROR") + " vs " + opponent1EditText.getText().toString() + " & " + opponent2EditText.getText().toString();
                    newMatch.player1Name = sharedPreferences.getString("Name", "ERROR");
                    newMatch.opponent1Name = opponent1EditText.getText().toString();
                    newMatch.opponent2Name = opponent2EditText.getText().toString();
                }
                else{
                    newMatch.title = sharedPreferences.getString("Name", "ERROR") + " &" + teamMateEditText.getText().toString() + " vs " + opponent1EditText.getText().toString() + " & " + opponent2EditText.getText().toString();
                    newMatch.player1Name = sharedPreferences.getString("Name", "ERROR");
                    newMatch.player2Name = teamMateEditText.getText().toString();
                    newMatch.opponent1Name = opponent1EditText.getText().toString();
                    newMatch.opponent2Name = opponent2EditText.getText().toString();
                }
                newMatch.time = System.currentTimeMillis();
                for(int i=0; i<tableViewMyScoreEntries.size(); i++){
                    newMatch.scores.add(tableViewMyScoreEntries.get(i) + "-" + tableViewOpponentScoreEntries.get(i));
                }
                newMatch.finalScore = tableViewMyScoreEntries.get(tableViewMyScoreEntries.size()-1) + "-" + tableViewOpponentScoreEntries.get(tableViewMyScoreEntries.size()-1);

                Gson gson = new Gson();
                String json = sharedPreferences.getString("allObservationsClass", "");
                AllObservationClass allObservationsClassObj = gson.fromJson(json, AllObservationClass.class);
                if(allObservationsClassObj.containsDate(todaysDate)){
                    allObservationsClassObj.getDayClass(todaysDate).add_match(newMatch);
                    Log.d("QWER", "Not first match on date!");
                }
                else{
                    DayClass dayClass = new DayClass(todaysDate);
                    dayClass.add_match(newMatch);
                    allObservationsClassObj.addDayClass(dayClass);
                    Log.d("QWER", "First match on date!");
                }
                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                String allObservationsClassJson = gson.toJson(allObservationsClassObj);
                myEdit.putString("allObservationsClass", allObservationsClassJson);

                if(!opponent1EditText.getText().toString().isEmpty()){
                    if(!statsClass.is_friend(opponent1EditText.getText().toString())){
                        statsClass.add_friend(opponent1EditText.getText().toString());
                    }
                    else{
                        FriendClass friendClass = statsClass.get_friend(opponent1EditText.getText().toString());
                        if(newMatch.win == MatchClass.MATCH_WIN){
                            friendClass.for_score++;
                        }
                        else if(newMatch.win == MatchClass.MATCH_LOSE){
                            friendClass.against_score++;
                        }
                        friendClass.for_points += Integer.parseInt(tableViewMyScoreEntries.get(tableViewMyScoreEntries.size()-1).trim());
                        friendClass.against_points += Integer.parseInt(tableViewOpponentScoreEntries.get(tableViewMyScoreEntries.size()-1).trim());
                        statsClass.update_friend(friendClass);
                    }
                }

                if(!opponent2EditText.getText().toString().isEmpty()){
                    if(!statsClass.is_friend(opponent2EditText.getText().toString())){
                        statsClass.add_friend(opponent2EditText.getText().toString());
                    }
                    else{
                        FriendClass friendClass = statsClass.get_friend(opponent2EditText.getText().toString());
                        if(newMatch.win == MatchClass.MATCH_WIN){
                            friendClass.for_score++;
                        }
                        else if(newMatch.win == MatchClass.MATCH_LOSE){
                            friendClass.against_score++;
                        }
                        friendClass.for_points += Integer.parseInt(tableViewMyScoreEntries.get(tableViewMyScoreEntries.size()-1).trim());
                        friendClass.against_points += Integer.parseInt(tableViewOpponentScoreEntries.get(tableViewMyScoreEntries.size()-1).trim());
                        statsClass.update_friend(friendClass);
                    }
                }

                if(!teamMateEditText.getText().toString().isEmpty()){
                    if(!statsClass.is_friend(teamMateEditText.getText().toString())){
                        statsClass.add_friend(teamMateEditText.getText().toString());
                    }
                    else{
                        FriendClass friendClass = statsClass.get_friend(opponent1EditText.getText().toString());
                        if(newMatch.win == MatchClass.MATCH_WIN){
                            friendClass.won_with++;
                        }
                        else if(newMatch.win == MatchClass.MATCH_LOSE){
                            friendClass.lost_with++;
                        }
                        statsClass.update_friend(friendClass);
                    }
                }
                String statsClassJson = gson.toJson(statsClass);
                myEdit.putString("statsClass", statsClassJson);

                myEdit.apply();

                HomeActivity.titles.clear();
                HomeActivity.titles.addAll(allObservationsClassObj.getDayClass(todaysDate).getMatchesTitles());
                HomeActivity.times.clear();
                HomeActivity.times.addAll(allObservationsClassObj.getDayClass(todaysDate).getMatchesTimes());
                HomeActivity.scores.clear();
                HomeActivity.scores.addAll(allObservationsClassObj.getDayClass(todaysDate).getMatchesFinalScores());
                HomeActivity.wins.clear();
                HomeActivity.wins.addAll(allObservationsClassObj.getDayClass(todaysDate).getMatchesWins());
                HomeActivity.noDataToShowTextView.setVisibility(View.GONE);
                HomeActivity.adapter.notifyDataSetChanged();

                finish();
            }
        });




        // TODO : Add friend, friend score when friend selected, etc

        opponent1EditText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Dialog dialog=new Dialog(AddMatch.this);
                dialog.setContentView(R.layout.dialog_searchable_spinner);
                dialog.getWindow().setLayout(800,1200);
                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                // show dialog
                dialog.show();
                // Initialize and assign variable
                EditText editText=dialog.findViewById(R.id.edit_text);
                ListView listView=dialog.findViewById(R.id.list_view);
                // Initialize array adapter
                ArrayAdapter<String> adapter=new ArrayAdapter<>(AddMatch.this, android.R.layout.simple_list_item_1,friendsList);
                // set adapter
                listView.setAdapter(adapter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        opponent1EditText.setText(adapter.getItem(position));
                        dialog.dismiss();
                    }
                });



                return false;
            }
        });



        opponent2EditText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Dialog dialog=new Dialog(AddMatch.this);
                dialog.setContentView(R.layout.dialog_searchable_spinner);
                dialog.getWindow().setLayout(800,1200);
                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                // show dialog
                dialog.show();
                // Initialize and assign variable
                EditText editText=dialog.findViewById(R.id.edit_text);
                ListView listView=dialog.findViewById(R.id.list_view);
                // Initialize array adapter
                ArrayAdapter<String> adapter=new ArrayAdapter<>(AddMatch.this, android.R.layout.simple_list_item_1,friendsList);
                // set adapter
                listView.setAdapter(adapter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        opponent2EditText.setText(adapter.getItem(position));
                        dialog.dismiss();
                    }
                });



                return false;
            }
        });


        teamMateEditText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Dialog dialog=new Dialog(AddMatch.this);
                dialog.setContentView(R.layout.dialog_searchable_spinner);
                dialog.getWindow().setLayout(800,1200);
                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                // show dialog
                dialog.show();
                // Initialize and assign variable
                EditText editText=dialog.findViewById(R.id.edit_text);
                ListView listView=dialog.findViewById(R.id.list_view);
                // Initialize array adapter
                ArrayAdapter<String> adapter=new ArrayAdapter<>(AddMatch.this, android.R.layout.simple_list_item_1,friendsList);
                // set adapter
                listView.setAdapter(adapter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        teamMateEditText.setText(adapter.getItem(position));
                        dialog.dismiss();
                    }
                });



                return false;
            }
        });



    }

    void update_TableLayout(){
        tableLayoutDisplay.removeAllViews();
        for(int i = 0; i< tableViewMyScoreEntries.size(); i++){
            TableRow tr_head = new TableRow(AddMatch.this);
            tr_head.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            tr_head.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            tr_head.setDividerDrawable(ContextCompat.getDrawable(AddMatch.this, R.drawable.tablelayout_divider));

            TextView myScore = new TextView(AddMatch.this);
            myScore.setText(tableViewMyScoreEntries.get(i));
            myScore.setGravity(Gravity.END);
            myScore.setTypeface(Typeface.create("cursive", Typeface.NORMAL));
            myScore.setTextColor(Color.parseColor("#AAAAAA"));
            myScore.setTextSize(18);
            myScore.setPadding(5, 5, 25, 5);
            //myScore.setBackground(ContextCompat.getDrawable(AddMatch.this, R.drawable.tablerow_background));
            tr_head.addView(myScore);// add the column to the table row here

            TextView opponentScore = new TextView(AddMatch.this);
            opponentScore.setText(tableViewOpponentScoreEntries.get(i));
            opponentScore.setTypeface(Typeface.create("cursive", Typeface.NORMAL));
            opponentScore.setTextColor(Color.parseColor("#AAAAAA"));
            opponentScore.setTextSize(18);
            opponentScore.setPadding(25, 5, 5, 5);
            tr_head.addView(opponentScore); // add the column to the table row here

            tableLayoutDisplay.addView(tr_head, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
        }
    }


    // Override the onBackPressed method to show a dialog
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        // Create a new AlertDialog.Builder object
        //super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Set the title and message of the dialog
        builder.setTitle("Cancel Match Add?");
        builder.setMessage("Do you really want to exit? You will not be able to recover this match's data if you do not save!");
        // Set the positive and negative buttons of the dialog
        builder.setPositiveButton("Yes, I want to exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Finish the current activity
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });
        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}