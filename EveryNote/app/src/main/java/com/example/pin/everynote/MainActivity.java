package com.example.pin.everynote;

import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.pin.everynote.DrawView;

public class MainActivity extends AppCompatActivity {
    private DrawView customCanvas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customCanvas = (DrawView) findViewById(R.id.simpleDrawingView1);
    }


    public void callClearCanvas(View v) {
        customCanvas.clearCanvas();
    }
}
