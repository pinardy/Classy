package com.example.pin.studentapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FeedbackActivity extends AppCompatActivity {

    Button learnObj;
    Firebase myFirebaseRef;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        //The firebase library must be initialized once with an Android context
        //This must happen before any firebase app ref is created or used
        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://student-app-23e7b.firebaseio.com/");

        myFirebaseRef.child("message").setValue("Connected to Firebase2222");

    }

    public void feedback(View v){
        Intent i = new Intent(FeedbackActivity.this, LearnPaceActivity.class);
        startActivity(i);
    }
    //TODO: Store module names in an ArrayList from prof's app
    //TODO: Read string from an ArrayList
    public void refresh(View v){
        learnObj = (Button) findViewById(R.id.learnObj1);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("Test");



        // Attach a listener to read the data at our posts reference
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String msg = dataSnapshot.getValue().toString();
                learnObj.setText(msg);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                learnObj.setText("-");
            }
        });
    }

}


