package com.example.nguyentrunghuan.a50001_iot;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nguyen Trung Huan on 12/10/2016.
 * Datapoints can be found in the hashmap 'datapoints'
 * Number of students can found in 'sc'
 */
public class viewLessonActivity extends AppCompatActivity {
    protected Typeface mTfRegular;
    protected Typeface mTfLight;
    protected HashMap<String, Integer> datapoints = new HashMap<>();
    protected int sc;
    protected DatabaseReference lessonlevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewlesson);


        Bundle bundle = getIntent().getExtras();
        String lessonName = bundle.getString("data");

        //Declare elements
        final TextView toofast = (TextView) findViewById(R.id.speed_tf);
        final TextView fast = (TextView) findViewById(R.id.speed_f);
        final TextView goodpace = (TextView) findViewById(R.id.speed_gp);
        final TextView slow = (TextView) findViewById(R.id.speed_s);
        final TextView tooslow = (TextView) findViewById(R.id.speed_ts);
        final TextView studentcount = (TextView) findViewById(R.id.sc);

        final TextView name = (TextView) findViewById(R.id.name);
        name.setText(lessonName);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference courselevel = database.getReference("50001").getRef();
        lessonlevel = courselevel.child(lessonName).getRef();

        /*** SET PROPERTIES FOR PIECHART ***/

        mTfRegular = Typeface.createFromAsset(getApplication().getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getApplicationContext().getAssets(), "OpenSans-Light.ttf");


        PieChart mChart = (PieChart) findViewById(R.id.chart1);
        mChart.setBackgroundColor(Color.WHITE);

        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);

        mChart.setCenterTextTypeface(mTfLight);
        mChart.setCenterText(generateCenterSpannableText());

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationEnabled(false);
        mChart.setHighlightPerTapEnabled(true);

        mChart.setMaxAngle(360f); // HALF CHART
        mChart.setRotationAngle(360f);
        mChart.setCenterTextOffset(0, -20);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTypeface(mTfRegular);
        mChart.setEntryLabelTextSize(12f);

        /*** SET PROPERTIES FOR PIECHART ***/

        /*** LIVE FEED OF DATA ***/

        lessonlevel.child("Feedback data").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                String value = dataSnapshot.getValue().toString();
                Log.w("Data change detected","");
                switch (key) {
                    case "Too fast":
                        toofast.setText(value);
                        datapoints.put("Too fast", Integer.parseInt(value));
                        break;
                    case "Fast":
                        fast.setText(value);
                        datapoints.put("Fast", Integer.parseInt(value));
                        break;
                    case "Good pace":
                        goodpace.setText(value);
                        datapoints.put("Good pace", Integer.parseInt(value));
                        break;
                    case "Slow":
                        slow.setText(value);
                        datapoints.put("Slow", Integer.parseInt(value));
                        break;
                    case "Too slow":
                        tooslow.setText(value);
                        datapoints.put("Too slow", Integer.parseInt(value));
                        break;
                    case "Student count":
                        studentcount.setText(value);
                        sc = Integer.parseInt(value);
                        break;
                }
                setData();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                String value = dataSnapshot.getValue().toString();
                Log.w("Data change detected","");
                switch (key) {
                    case "Too fast":
                        toofast.setText(value);
                        datapoints.put("Too fast", Integer.parseInt(value));
                        break;
                    case "Fast":
                        fast.setText(value);
                        datapoints.put("Fast", Integer.parseInt(value));
                        break;
                    case "Good pace":
                        goodpace.setText(value);
                        datapoints.put("Good pace", Integer.parseInt(value));
                        break;
                    case "Slow":
                        slow.setText(value);
                        datapoints.put("Slow", Integer.parseInt(value));
                        break;
                    case "Too slow":
                        tooslow.setText(value);
                        datapoints.put("Too slow", Integer.parseInt(value));
                        break;
                    case "Student count":
                        studentcount.setText(value);
                        sc = Integer.parseInt(value);
                        break;
                }
                setData();
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        /*** LIVE FEED OF DATA ***/
    }

        /*** (additional methods) DRAW PIECHART ***/
    private void setData() {
        PieChart mChart = (PieChart) findViewById(R.id.chart1);



        ArrayList<PieEntry> values = new ArrayList<PieEntry>();
        for (String key : datapoints.keySet()) {
            if (datapoints.get(key) != 0)
                values.add(new PieEntry(datapoints.get(key), key));
        }

        PieDataSet dataSet = new PieDataSet(values, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(mTfLight);
        mChart.setData(data);

        mChart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("Classroom Feed\n Developed by SUTD");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 18, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 18, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 18, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 18, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 18, s.length(), 0);
        return s;
    }
        /*** (additional methods) DRAW PIECHART ***/

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu, menu);
            return super.onCreateOptionsMenu(menu);
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //remove lesson
        lessonlevel.setValue(null);
        Toast.makeText(getApplicationContext(), "Lesson removed" ,
                Toast.LENGTH_LONG).show();

        return true;
    }


}


