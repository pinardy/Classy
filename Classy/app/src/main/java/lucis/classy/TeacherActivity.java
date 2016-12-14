package lucis.classy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TeacherActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Intent setting;
    Intent secret;
    Intent module;
    Button button50001;
    Button button50002;
    Button button50004;

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Teacher Activity (SelectModuleActivity)
            button50001 = (Button) findViewById(R.id.button50001);
            button50002 = (Button) findViewById(R.id.button50002);
            button50004 = (Button) findViewById(R.id.button50004);

            setting = new Intent(getApplicationContext(), SettingsActivity.class);
            secret = new Intent(getApplicationContext(), SecretView.class);
            module = new Intent(getApplicationContext(), ModuleActivity.class);

            button50001.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    module.putExtra("Module", "50001");
                    startActivity(module);
                }
            });

            button50002.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    module.putExtra("Module", "50002");
                    startActivity(module);
                }
            });

            button50004.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    module.putExtra("Module", "50004");
                    startActivity(module);
                }
            });



        // Additional

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        ImageView profPic = (ImageView) headerView.findViewById(R.id.imageView);
        profPic.setImageResource(R.drawable.profpic);

        TextView userTeacherName = (TextView) headerView.findViewById(R.id.userTeacher);
        userTeacherName.setText("Teacher's Name");

        TextView userTeacher = (TextView) headerView.findViewById(R.id.textView);
        userTeacher.setText(StringHolder.userHold);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.teacher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(secret);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav1t) {
            module.putExtra("Module", "50001");
            startActivity(module);
        } else if (id == R.id.nav2t) {
            module.putExtra("Module", "50002");
            startActivity(module);
        } else if (id == R.id.nav3t) {
            module.putExtra("Module", "50002");
            startActivity(module);
        } else if (id == R.id.navSettingt) {
            startActivity(setting);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
