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

    /**  We want to store the counts and send it over to the teacher app
     * count1: too fast
     * count2: fast
     * count3: good pace
     * count4: slow
     * count5: too slow
     */

    Firebase myFirebaseRef;
    Firebase myFirebaseRefClarify;
    Firebase ref;
    FirebaseDatabase database;

    Integer studentCount;
    Integer count1;
    Integer count2;
    Integer count3;
    Integer count4;
    Integer count5;

    TextView textViewLesson;

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
        myFirebaseRefClarify = new Firebase("https://student-app-23e7b.firebaseio.com/").child("Clarification");

//        final DatabaseReference databaseRef50001 = database.getReference("50001");
        ref = new Firebase("https://student-app-23e7b.firebaseio.com/").child("50001").child(listOfLessons.get(lessonIndex)).child("Feedback data");
    }

    //TODO: Allow for only one feedback per session
    //TODO: Ensure that each session adds onto current data (not start over) (Use Sidney's method to increment count)

    public void sendInfo(View v){
        radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
        submit = (Button) findViewById(R.id.submit);
        clarification = (EditText) findViewById(R.id.editText_clarification);
        password = (EditText) findViewById(R.id.editText_pw);

        database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("Module Password");

        final String pwString = password.getText().toString();
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Obtain password from firebase
                pwFromFirebase = dataSnapshot.getValue(String.class);
                if (Objects.equals(pwString, pwFromFirebase)) {

                    final HashMap<String, String> data = new HashMap<>();

                    try {
                        // Get selected Radio Button
                        RadioButton selectRadio = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());

                        // Find out the option and add to counter
                        String option = selectRadio.getText().toString();

                        // Increment count of student's feedback
                        studentCount += 1;

                        if (option.equals("The lesson is too fast")) {
                            count1 += 1;
                        }
                        if (option.equals("The lesson is fast")) {
                            count2 += 1;
                        }
                        if (option.equals("The lesson is at a good pace")) {
                            count3 += 1;
                        }
                        if (option.equals("The lesson is slow")) {
                            count4 += 1;
                        }
                        if (option.equals("The lesson is too slow")) {
                            count5 += 1;
                        }

                        //TODO: Send clarification to firebase
                        String clarify = clarification.getText().toString();
                        myFirebaseRefClarify.push();

                        data.put("Student count", studentCount.toString());

                        data.put("Too fast", count1.toString());
                        data.put("Fast", count2.toString());
                        data.put("Good pace", count3.toString());
                        data.put("Slow", count4.toString());
                        data.put("Too slow", count5.toString());
//                        myFirebaseRef.setValue(data);
                        ref.setValue(data);

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
                        "oncancelled", Toast.LENGTH_LONG).show();
            }
        });

    }


    //Function to update feedback firebase
    public void push(View v){
//        push = (Button) findViewById(R.id.testButton);

//        ref = new Firebase("https://student-app-23e7b.firebaseio.com/").child("50001").child(listOfLessons.get(lessonIndex)).child("Feedback data");

        final DatabaseReference ref = database.getReference("50001").child(listOfLessons.get(lessonIndex)).child("Feedback data");
//        Input parameters: the parent address and the feedback name to be passed as arguments

        Firebase upvotesRef = new Firebase("https://docs-examples.firebaseio.com/android/saving-data/fireblog/posts/-JRHTHaIs-jNPLXOQivY/upvotes");

//        upvotesRef.runTransaction(new Transaction.Handler() {
//            @Override
//            public Transaction.Result doTransaction(MutableData currentData) {
//                if(currentData.getValue() == null) {
//                    currentData.setValue(1);
//                } else {
//                    currentData.setValue((Long) currentData.getValue() + 1);
//                }
//
//                return Transaction.success(currentData); //we can also abort by calling Transaction.abort()
//            }
//
//            @Override
//            public void onComplete(DatabaseError databaseError, boolean b,
//                                   DataSnapshot dataSnapshot) {
//            }
//
//        }); 
    }

}
