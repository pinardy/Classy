package com.example.pin.studentapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class FeedbackActivity extends AppCompatActivity {

    Button learnObj1;
    Button learnObj2;
    Button learnObj3;
    Button learnObj4;
    Firebase myFirebaseRef;
    FirebaseDatabase database;
    ArrayList<String> listOfLessons;
    Integer lessonIndex;
    Intent lesson;


    public FeedbackActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        database = FirebaseDatabase.getInstance();


        listOfLessons = new ArrayList<>();

        //The firebase library must be initialized once with an Android context
        //This must happen before any firebase app ref is created or used
        Firebase.setAndroidContext(this);

        final DatabaseReference databaseRef50001 = database.getReference("50001");


        databaseRef50001.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                String child = dataSnapshot.getKey();

                // Store the modules added by the prof app into an an ArrayList
                listOfLessons.add(child);
//                Toast.makeText(FeedbackActivity.this, child, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
    }

    //TODO: Get values/strings from buttons into LearnPaceActivity for display
    public void feedback(View v) {
        try {
            startActivity(lesson);
        }
        catch (Exception e){
            Intent i = new Intent(FeedbackActivity.this, LearnPaceActivity.class);
            startActivity(i);
        }
    }


    //TODO: Read string from an ArrayList
    //TODO: Dynamically add buttons (WeiXuan)
    /* Store the module names in an arraylist.
    Press button, references arraylist according to index
    stores value
    */

    //TODO: Refresh sets the values to be passed over in feedback
    public void refresh(View v) {
        // Instantiate the buttons
        learnObj1 = (Button) findViewById(R.id.learnObj1);
        learnObj2 = (Button) findViewById(R.id.learnObj2);
        learnObj3 = (Button) findViewById(R.id.learnObj3);
        learnObj4 = (Button) findViewById(R.id.learnObj4);

        final DatabaseReference databaseRef50001 = database.getReference("50001");

        databaseRef50001.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                String child = dataSnapshot.getKey();

                // Store the lessons added by the prof app into an an ArrayList
                if (!listOfLessons.contains(child)) {
                    listOfLessons.add(child);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                listOfLessons.remove(s);
                String child = dataSnapshot.getKey();
                listOfLessons.add(child);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                Toast.makeText(FeedbackActivity.this, "child removed", Toast.LENGTH_SHORT).show();
                String child = dataSnapshot.getKey();
                listOfLessons.remove(child);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(FeedbackActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }

        });


        try {
            learnObj1.setText(listOfLessons.get(0));
            lesson = new Intent(getApplicationContext(), LearnPaceActivity.class);
            learnObj1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lessonIndex = 0;
                    lesson.putExtra("Lessons", listOfLessons);
                    lesson.putExtra("Index", lessonIndex);
                    startActivity(lesson);
                }
            });

        } catch (Exception e){
            learnObj1.setText("-");
        }

        try {
            learnObj2.setText(listOfLessons.get(1));
            lesson = new Intent(getApplicationContext(), LearnPaceActivity.class);
            learnObj2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lessonIndex = 1;
                    lesson.putExtra("Lessons", listOfLessons);
                    lesson.putExtra("Index", lessonIndex);
                    startActivity(lesson);
                }
            });

        } catch (Exception e){
            learnObj2.setText("-");
        }

        try {
            learnObj3.setText(listOfLessons.get(2));
            lesson = new Intent(getApplicationContext(), LearnPaceActivity.class);
            learnObj3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lessonIndex = 2;
                    lesson.putExtra("Lessons", listOfLessons);
                    lesson.putExtra("Index", lessonIndex);
                    startActivity(lesson);
                }
            });

        } catch (Exception e){
            learnObj3.setText("-");
        }

        try {
            learnObj4.setText(listOfLessons.get(3));
            lesson = new Intent(getApplicationContext(), LearnPaceActivity.class);
            learnObj4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lessonIndex = 3;
                    lesson.putExtra("Lessons", listOfLessons);
                    lesson.putExtra("Index", lessonIndex);
                    startActivity(lesson);
                }
            });
        } catch (Exception e){
            learnObj4.setText("-");
        }

    }


    // Test button
    public void test(View v){
        for (int i=0; i<listOfLessons.size(); i++){
            Toast.makeText(FeedbackActivity.this, listOfLessons.get(i), Toast.LENGTH_SHORT).show();
        }
    }

}