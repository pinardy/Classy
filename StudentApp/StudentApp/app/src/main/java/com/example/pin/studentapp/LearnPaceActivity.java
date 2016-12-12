package com.example.pin.studentapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
//    ArrayList<String> listOfLessons;
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
    FirebaseDatabase database;

    Integer studentCount = 0;
    Integer count1 = 0;
    Integer count2 = 0;
    Integer count3 = 0;
    Integer count4 = 0;
    Integer count5 = 0;

    TextView textViewLesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learnpace);
        textViewLesson = (TextView) findViewById(R.id.textViewLesson);

        // Extracting the data from previous activity
        ArrayList<String> listOfLessons = getIntent().getExtras().getStringArrayList("Lessons");
        lessonIndex = getIntent().getExtras().getInt("Index");

        // Set the lesson name to the name of the button from previous activity
        textViewLesson.setText(listOfLessons.get(lessonIndex));

        //The firebase library must be initialized once with an Android context
        //This must happen before any firebase app ref is created or used
        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://student-app-23e7b.firebaseio.com/").child("Feedback data");
        myFirebaseRefClarify = new Firebase("https://student-app-23e7b.firebaseio.com/").child("Clarification");
    }


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

                        data.put("Student Count", studentCount.toString());

                        data.put("Too fast", count1.toString());
                        data.put("Fast", count2.toString());
                        data.put("Good pace", count3.toString());
                        data.put("Slow", count4.toString());
                        data.put("Too slow", count5.toString());
                        myFirebaseRef.setValue(data);

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


}
