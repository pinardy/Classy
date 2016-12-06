package com.example.pin.studentapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class FeedbackActivity extends AppCompatActivity {

    Button learnObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
    }

    public void feedback(View v){
        Intent i = new Intent(FeedbackActivity.this, LearnPaceActivity.class);
        startActivity(i);
    }

    //TODO: Firebase to change the text of the buttons to the subjects
    public void refresh(View v){
        learnObj = (Button) findViewById(R.id.learnObj1);
        try {
            //TODO: Replace "Hah" with module name from Firebase
            learnObj.setText("Hah");
        } catch (Exception e){
            learnObj.setText("-");
        }
    }
}


