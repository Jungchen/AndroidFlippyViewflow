package com.example.longsiyang.androidviewflowdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

    ViewFlow viewFlow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewFlow = (ViewFlow) findViewById(R.id.view_flow_id);
        View view1 = LayoutInflater.from(this).inflate(R.layout.test_page1, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.test_page2, null);
        viewFlow.addView(view1);
        viewFlow.addView(view2);
        viewFlow.setAspectratio(2.0);
        viewFlow.setPeriod(-1000);
        viewFlow.flipStart();
    }

    public void start(View view){
        viewFlow.flipStart();
        Toast.makeText(this , "start" , Toast.LENGTH_SHORT).show();
    }
    public void stop(View view){
        viewFlow.flipStop();
        Toast.makeText(this , "stop" , Toast.LENGTH_SHORT).show();
    }
}
