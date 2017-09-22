package com.izl0gc.ye.payclock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button addWork;
    Button addShift;
    Button viewStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addShift  = (Button) findViewById(R.id.new_shift);
        addWork   = (Button) findViewById(R.id.new_work);
        viewStats = (Button) findViewById(R.id.statistics);

        addShift.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), ShiftActivity.class);
                startActivity(myIntent);
            }
        });

        addWork.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), WorkActivity.class);
                startActivity(myIntent);
            }
        });

        viewStats.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), StatsActivity.class);
                startActivity(myIntent);
            }
        });

    }
}
