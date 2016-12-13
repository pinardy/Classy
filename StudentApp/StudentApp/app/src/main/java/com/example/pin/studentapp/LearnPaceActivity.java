package com.example.pin.studentapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Pin on 06-Dec-16.
 */

public class LearnPaceActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    Button submit;
    EditText clarification;
    EditText password;
    String pwFromFirebase;
    ArrayList<String> listOfLessons;
    Integer lessonIndex;
    TextView textViewLesson;
    Integer oncePerSession;

    Firebase myFirebaseRef;
    Firebase myFirebaseRefClarify;
    Firebase ref;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learnpace);
        database = FirebaseDatabase.getInstance();

        textViewLesson = (TextView) findViewById(R.id.textViewLesson);

        // Extracting the data from previous activity
        listOfLessons = getIntent().getExtras().getStringArrayList("Lessons");
        lessonIndex = getIntent().getExtras().getInt("Index");

        // Set the lesson name to the name of the button from previous activity
        textViewLesson.setText(listOfLessons.get(lessonIndex));

        //The firebase library must be initialized once with an Android context
        //This must happen before any firebase app ref is created or used
        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://student-app-23e7b.firebaseio.com/").child("Feedback data");
        myFirebaseRefClarify = new Firebase("https://student-app-23e7b.firebaseio.com/").child(listOfLessons.get(lessonIndex)).child("Clarification");


        ref = new Firebase("https://student-app-23e7b.firebaseio.com/").child("50001").child(listOfLessons.get(lessonIndex)).child("Feedback data");
    }

    //TODO: Allow for only one feedback per session

    public void sendInfo(View v) {

        // Check if feedback has already been sent
        if (oncePerSession == 1) {
            Toast.makeText(LearnPaceActivity.this,
                    "Feedback already submitted", Toast.LENGTH_LONG).show();
        }

        radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
        submit = (Button) findViewById(R.id.submit);
        clarification = (EditText) findViewById(R.id.editText_clarification);
        password = (EditText) findViewById(R.id.editText_pw);

        database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("Module Password");

        final String pwString = password.getText().toString();
        if (oncePerSession == null) {
            oncePerSession += 1;

            databaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Obtain password from firebase
                    pwFromFirebase = dataSnapshot.getValue(String.class);
                    if (Objects.equals(pwString, pwFromFirebase)) {

                        final HashMap<String, String> data = new HashMap<>();

                        try {
                            String moduleName = listOfLessons.get(lessonIndex);

                            // Get selected Radio Button
                            RadioButton selectRadio = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());

                            // Find out the option and add to counter
                            String option = selectRadio.getText().toString();

                            // Increment count of student's feedback
                            updateFeedback(moduleName, "Student count");

                            if (option.equals("The lesson is too fast")) {
                                updateFeedback(moduleName, "Too fast");
                            }
                            if (option.equals("The lesson is fast")) {
                                updateFeedback(moduleName, "Fast");
                            }
                            if (option.equals("The lesson is at a good pace")) {
                                updateFeedback(moduleName, "Good pace");
                            }
                            if (option.equals("The lesson is slow")) {
                                updateFeedback(moduleName, "Slow");
                            }
                            if (option.equals("The lesson is too slow")) {
                                updateFeedback(moduleName, "Too slow");
                            }

                            String clarify = clarification.getText().toString();
                            myFirebaseRefClarify.setValue(clarify);


                            Toast.makeText(LearnPaceActivity.this,
                                    "Feedback sent", Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            Toast.makeText(LearnPaceActivity.this,
                                    "No option selected", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(LearnPaceActivity.this,
                                "Invalid password", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(LearnPaceActivity.this,
                            "onCancelled", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    /* This method utilizes string concat to define the getReference
     location in order to update feedback to the correct location */

    public void updateFeedback(String moduleName, String feedback){

        // String concat to def reference location
        String refLocation = "50001/" + moduleName + "/Feedback data/" + feedback;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference(refLocation);

        /* We run transaction to allow multiple users to interact with the app
        so that data will be updated, not replaced. */

        ref.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData currentData) {
                if(currentData.getValue() == null) {
                    currentData.setValue(1);
                } else {
                    currentData.setValue((Long) currentData.getValue() + 1);
                }
                return Transaction.success(currentData); //we can also abort by calling Transaction.abort()
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
//                Toast.makeText(LearnPaceActivity.this,
//                        "Completed!", Toast.LENGTH_LONG).show();
            }
        });
    }

}
