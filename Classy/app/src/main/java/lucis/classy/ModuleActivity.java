package lucis.classy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import lucis.classy.R;


public class ModuleActivity extends AppCompatActivity{

    String moduleString;
    Intent moduleCreate;
    Intent moduleView;
    TextView moduleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);

        //Extract data from MainActivity
        moduleString = getIntent().getExtras().getString("Module");
        moduleName = (TextView) findViewById(R.id.textViewModule);
        moduleName.setText(moduleString);

        moduleCreate = new Intent(getApplicationContext(), CreateActivity.class);
        moduleView = new Intent(getApplicationContext(), ViewActivity.class);
        moduleCreate.putExtra("Module", moduleString);
        moduleView.putExtra("Module", moduleString);


        // Declare elements in the layout
        Button create = (Button) findViewById(R.id.create);


        // Connect to the Firebase database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference courselevel = database.getReference(moduleString);


    }

    public void createClass(View v){
        startActivity(moduleCreate);
    }

    public void viewClass(View v){
        startActivity(moduleView);
    }

}
