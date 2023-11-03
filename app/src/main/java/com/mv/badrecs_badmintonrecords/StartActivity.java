package com.mv.badrecs_badmintonrecords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class StartActivity extends AppCompatActivity {

    EditText nameEditText;
    Button startButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Retrieving the value using its keys the file name
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        // The value will be default as empty string because for
        // first time when the app is opened, there is nothing to show
        if(sh.getLong("OpeningTime", -1) == -1){
            SharedPreferences.Editor myEdit = sh.edit();
            myEdit.putLong("OpeningTime", System.currentTimeMillis());

            AllObservationClass allObservationsClass = new AllObservationClass();
            Gson gson = new Gson();
            String allObservationsClassJson = gson.toJson(allObservationsClass);
            myEdit.putString("allObservationsClass", allObservationsClassJson);

            myEdit.apply();
        }
        else{
            Intent intent = new Intent(StartActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }


        nameEditText = findViewById(R.id.nameEditText);
        startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nameEditText.getText().toString().isEmpty() == false){
                    SharedPreferences.Editor myEdit = sh.edit();
                    myEdit.putString("Name", nameEditText.getText().toString());

                    StatsClass statsClass = new StatsClass();
                    FriendClass self_friend = new FriendClass(nameEditText.getText().toString());
                    statsClass.add_friend(self_friend);
                    Gson gson = new Gson();
                    String statsClassJson = gson.toJson(statsClass);
                    myEdit.putString("statsClass", statsClassJson);
                    myEdit.apply();

                    Intent intent = new Intent(StartActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(StartActivity.this, "Enter your name!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}