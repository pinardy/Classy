package lucis.classy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by muayanfrost on 13/12/16.
 */

public class LoginActivity extends AppCompatActivity{
    private EditText username, password;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        importAccount(); // Imports account for login




        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        Button loginButton = (Button) findViewById(R.id.loginButton);

        // Fixes weird font for the textPassword inputType
        password.setTypeface(Typeface.DEFAULT);
        password.setTransformationMethod(new PasswordTransformationMethod());



        loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (password.getText().toString().equals("professor") && username.getText().toString().equals("professor1")) {
                        StringHolder.userHold = username.getText().toString();
                        nextActivityTeacher();
                    } else if ((password.getText().toString().equals("student1") && username.getText().toString().equals("1001000")) || (password.getText().toString().equals("student2") && username.getText().toString().equals("1001111"))) {
                        StringHolder.userHold = username.getText().toString();
                        nextActivityStudent();
                    } else {
                        Context context = getApplicationContext();
                        CharSequence text = "Wrong username or password!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
            });






//        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
//                if(actionId == EditorInfo.IME_ACTION_SEARCH ||
//                        actionId == EditorInfo.IME_ACTION_DONE ||
//                        actionId == EditorInfo.IME_ACTION_GO ||
//                        actionId == EditorInfo.IME_ACTION_NEXT ||
//                        keyEvent.getAction() == KeyEvent.ACTION_DOWN
//                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
//                    nextActivity();
//                    return true;
//                }
//                return false;
//            }
//        });
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


    // For starting next activity
    public void nextActivityStudent(){
        Intent nextActivity = new Intent(LoginActivity.this, StudentActivity.class);
        startActivity(nextActivity);
        finish();
    }

    public void nextActivityTeacher(){
        Intent nextActivity = new Intent(LoginActivity.this, TeacherActivity.class);
        startActivity(nextActivity);
        finish();
    }

    // Importing account for login (preferably from firebase)
    public void importAccount(){
        Account account1 = new Account("muayan179","muayan115",true);
        Account account2 = new Account("stupidstudent", "iamstupid", false);
    }
}