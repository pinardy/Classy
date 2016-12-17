package lucis.classy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Nguyen Trung Huan on 12/10/2016.
 */


/** This CreateActivity is for the user (professor) to create new
 * lessons under his/her module
 */

public class CreateActivity extends AppCompatActivity{

    String moduleString;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createclass);

        moduleString = getIntent().getExtras().getString("Module");


        // Declare elements
        final Button clear = (Button) findViewById(R.id.clear);
        final Button submit = (Button) findViewById(R.id.submit);
        final EditText nameField = (EditText) findViewById(R.id.classname);
        final EditText keywordsField = (EditText) findViewById(R.id.keywords);

        // Action: CLEAR
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameField.setText("", TextView.BufferType.EDITABLE);
                keywordsField.setText("", TextView.BufferType.EDITABLE);
            }
        });


        // Action: SUBMIT
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameField.getText().toString();
                String keywords = keywordsField.getText().toString();
                try {
                    Toast.makeText(getApplicationContext(), "Lesson created", Toast.LENGTH_SHORT).show();

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference courselevel = database.getReference(moduleString);

                    if (name.isEmpty()) throw new EmptyTextException("Name cannot be empty");

                    courselevel.child(name).setValue(name);
                    courselevel.child(name).child("keywords").setValue(keywords);
                    courselevel.child(name).child("Feedback data").setValue("");

                    DatabaseReference feedback = courselevel.child(name).child("Feedback data").getRef();
                    feedback.child("Too fast").setValue(0);
                    feedback.child("Fast").setValue(0);
                    feedback.child("Good pace").setValue(0);
                    feedback.child("Slow").setValue(0);
                    feedback.child("Too slow").setValue(0);
                    feedback.child("Student count").setValue(0);


                    courselevel.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
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

                    courselevel.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } catch (EmptyTextException e){
                    Toast.makeText(getApplicationContext(), "Please input the name of the lesson", Toast.LENGTH_SHORT).show();
                };

            }
        });
    }

    static class EmptyTextException extends Exception{
        public EmptyTextException(String message){

        }
    }
}
