package com.izl0gc.ye.payclock;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.izl0gc.ye.payclock.db.contracts.WorkContract;
import com.izl0gc.ye.payclock.db.dao.ShiftDAO;
import com.izl0gc.ye.payclock.db.dao.WorkDAO;
import com.izl0gc.ye.payclock.time.DateTimeFormatter;
import java.util.Calendar;


public class StatsActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText start, end;
    private Spinner workSpinner;
    private Double rate;
    private ShiftDAO shiftDAO;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        start = (EditText) findViewById(R.id.from);
        end   = (EditText) findViewById(R.id.to);

        start.setOnClickListener(this);
        end.setOnClickListener(this);


        workSpinner = (Spinner) findViewById(R.id.work_names);
        loadSpinnerData();

        Button result = (Button) findViewById(R.id.result);
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayResult();
            }
        });

        shiftDAO = new ShiftDAO(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == start.getId() || id == end.getId()){
            setDate(view);
        }

    }

    public void setDate(View view){
        final EditText date = (EditText) findViewById(view.getId());
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        final int dayofmonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datedialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.setText(DateTimeFormatter.DateToString(year, monthOfYear, dayOfMonth));
            }
        }, year, month, dayofmonth);
        datedialog.show();
    }

    private void loadSpinnerData(){
        WorkDAO works = new WorkDAO(this);

        SimpleCursorAdapter scAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, works.getAll(), new String[] { WorkContract.Work.COLUMN_NAME,WorkContract.Work._ID,  WorkContract.Work.COLUMN_RATE}, new int[] {android.R.id.text1});

        scAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        workSpinner.setAdapter(scAdapter);
    }


    private void DisplayResult(){
        TextView totalBreak = (TextView) findViewById(R.id.time_rested);
        TextView totalWork = (TextView) findViewById(R.id.time_worked);
        TextView totalPay = (TextView) findViewById(R.id.total_earning);

        if(TextViewIsNullOrEmpty(totalBreak) || TextViewIsNullOrEmpty(totalWork) || TextViewIsNullOrEmpty(totalPay)){
            Toast.makeText(this,"You must fill all the field" ,Toast.LENGTH_LONG).show();
            return;
        }

        double restedtime = getTimeRested();
        double timeworked = getTimeWorked() - restedtime;

        rate = ((Cursor)workSpinner.getSelectedItem()).getDouble(2);

        totalBreak.setText(DateTimeFormatter.TimeToString(restedtime));
        totalWork.setText(DateTimeFormatter.TimeToString(timeworked));

        totalPay.setText(String.format("$%.2f", rate * (timeworked)));

    }

    private boolean TextViewIsNullOrEmpty(TextView textview){
        return (textview.getText().toString() == null || textview.getText().toString().isEmpty());
    }

    private double getTimeRested(){
        return shiftDAO.getBreakTime( DateTimeFormatter.DateToLong(start.getText().toString()), DateTimeFormatter.DateToLong(end.getText().toString()), ((Cursor)workSpinner.getSelectedItem()).getInt(0) );
    }

    private double getTimeWorked(){

        Cursor times = shiftDAO.getWorkDates(DateTimeFormatter.DateToLong(start.getText().toString()),DateTimeFormatter.DateToLong(end.getText().toString()), ((Cursor)workSpinner.getSelectedItem()).getInt(0));

        //String worked = "";
        double timeworked = 0;


        if(times != null){

            while(times.moveToNext()){
                timeworked += (times.getDouble(1) - times.getDouble(0));
                Log.i("timeworked", String.valueOf(timeworked));
                //worked += timeworked;
            }
        }

        return timeworked;
    }

}
