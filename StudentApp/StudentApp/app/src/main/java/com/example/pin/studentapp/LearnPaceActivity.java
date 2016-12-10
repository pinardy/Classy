package com.example.pin.studentapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by Pin on 06-Dec-16.
 */

public class LearnPaceActivity extends AppCompatActivity{

    RadioGroup radioGroup;
    Button submit;
    Button print;
    EditText clarification;

    /**  We want to store the counts and send it over to the teacher app
     * count1: too fast
     * count2: fast
     * count3: good pace
     * count4: slow
     * count5: too slow
     */

    Firebase myFirebaseRef;

    Integer studentCount = 0;
    Integer count1 = 0;
    Integer count2 = 0;
    Integer count3 = 0;
    Integer count4 = 0;
    Integer count5 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learnpace);

        //The firebase library must be initialized once with an Android context
        //This must happen before any firebase app ref is created or used
        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://student-app-23e7b.firebaseio.com/").child("Feedback data");

        myFirebaseRef.child("message").setValue("Connected to Firebase2222");

        radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
        submit = (Button) findViewById(R.id.submit);
        print = (Button) findViewById(R.id.countButton);
        clarification = (EditText) findViewById(R.id.editText_clarification);
    }
    //TODO: integrate password attendance checker (obtain from firebase)
    // if passsword is correct, continue
    // else nothing happens (toast to notify user)

    public void sendInfo(View v){
        Toast.makeText(LearnPaceActivity.this,
                "Feedback sent", Toast.LENGTH_LONG).show();

        final HashMap<String, String> data = new HashMap<>();

        // Get selected Radio Button
        RadioButton selectRadio = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());

        // Find out the option and add to counter
        String option = selectRadio.getText().toString();

        // Increment count of student's feedback
        studentCount +=1;

        if (option.equals("The lesson is too fast")){
            //encode a 1
            count1 += 1;
        }
        if (option.equals("The lesson is fast")){
            count2 += 1;
        }
        if (option.equals("The lesson is at a good pace")){
            count3 += 1;
        }
        if (option.equals("The lesson is slow")){
            count4 += 1;
        }
        if (option.equals("The lesson is too slow")){
            count5 += 1;
        }

        //TODO: Send clarification to firebase
        String clarify = clarification.getText().toString();

        data.put("Student Count", studentCount.toString());

        data.put("Too fast", count1.toString());
        data.put("Fast", count2.toString());
        data.put("Good pace", count3.toString());
        data.put("Slow", count4.toString());
        data.put("Too slow", count5.toString());
        myFirebaseRef.setValue(data);
    }

    //Method for testing the counts
    public void printCounts(View v){
        String count_string = count1.toString();

        Toast.makeText(LearnPaceActivity.this,
                count_string, Toast.LENGTH_LONG).show();
    }
}
