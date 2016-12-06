package com.example.pin.studentapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
    Integer count1 = 0;
    Integer count2 = 0;
    Integer count3 = 0;
    Integer count4 = 0;
    Integer count5 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learnpace);

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

        // Get selected Radio Button
        RadioButton selectRadio = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());

        // Find out the option and add to counter
        String option = selectRadio.getText().toString();

        if (option.equals("The lesson is too fast")){
            //encode a 1
            count1 += 1;
        }
        if (option.equals("The lesson is fast")){
            //encode a 2
            count2 += 1;
        }
        if (option.equals("The lesson is at a good pace")){
            //encode a 3
            count3 += 1;
        }
        if (option.equals("The lesson is slow")){
            //encode a 4
            count4 += 1;
        }
        if (option.equals("The lesson is too slow")){
            //encode a 5
            count5 += 1;
        }
        //TODO: Send clarification to firebase
        String clarify = clarification.getText().toString();


        //TODO: send the counts to Firebase
    }

    //Method for testing the counts
    public void printCounts(View v){
        String count_string = count1.toString();

        Toast.makeText(LearnPaceActivity.this,
                count_string, Toast.LENGTH_LONG).show();
    }
}
