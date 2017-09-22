package com.izl0gc.ye.payclock;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.izl0gc.ye.payclock.db.DAOInterface;
import com.izl0gc.ye.payclock.db.contracts.WorkContract;
import com.izl0gc.ye.payclock.db.dao.ShiftDAO;
import com.izl0gc.ye.payclock.db.dao.WorkDAO;
import com.izl0gc.ye.payclock.models.Shift;
import com.izl0gc.ye.payclock.time.DateTimeFormatter;

import java.util.Calendar;


public class ShiftActivity extends AppCompatActivity implements View.OnClickListener{
    private Spinner workSpinner;
    private EditText start, end, date;
    private CheckBox breakStatus;
    private double st,et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift);

        workSpinner = (Spinner) findViewById(R.id.spinner);

        start = (EditText) findViewById(R.id.start_time);
        end   = (EditText) findViewById(R.id.end_time);
        date  = (EditText) findViewById(R.id.date);

        start.setInputType(InputType.TYPE_NULL);
        date.setInputType(InputType.TYPE_NULL);
        end.setInputType(InputType.TYPE_NULL);

        date.setOnClickListener(this);
        start.setOnClickListener(this);
        end.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                    Calendar calendar = Calendar.getInstance();
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);
                    TimePickerDialog dialog = new TimePickerDialog(
                            view.getContext(),
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    view.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
                                    Log.d("test", String.format("%02d:%02d", hourOfDay, minute));
                                    end.setText(String.format("%02d:%02d", hourOfDay, minute));
                                    et = DateTimeFormatter.TimeToNumber(hourOfDay,minute);

                                }
                            },
                            hour, minute, true);
                    dialog.show();
            }

        });
        //rate = (EditText) findViewById(R.id.rate);

        breakStatus = (CheckBox) findViewById(R.id.checkBox);

        loadSpinnerData();

        Button submit = (Button) findViewById(R.id.save_shift);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveToDB();
            }
        });

    }

    @Override
    public void onClick(View view) {
       if(view.getId() == R.id.date){
           setDate(view);
       }else{
           setTime(view);
       }

    }

    public void setDate(View view){
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

    public void setTime(View view){
        final EditText textBox = (EditText) findViewById(view.getId());
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog dialog;
            dialog = new TimePickerDialog(
                    view.getContext(),
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            view.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
                            Log.d("test", String.format("%02d:%02d", hourOfDay, minute));
                            textBox.setText(String.format("%02d:%02d", hourOfDay, minute));
                            if(hourOfDay == 0.00d){
                                hourOfDay = 24;
                            }

                            if(textBox.getId() == R.id.start_time) {
                                st = DateTimeFormatter.TimeToNumber(hourOfDay, minute);
                            }else{
                                et = DateTimeFormatter.TimeToNumber(hourOfDay, minute);
                            }
                        }
                    },
                    hour, minute, true);


        dialog.show();
    }




    private void loadSpinnerData(){
        WorkDAO works = new WorkDAO(this);

        SimpleCursorAdapter scAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, works.getAll(), new String[] { WorkContract.Work.COLUMN_NAME,WorkContract.Work._ID,  WorkContract.Work.COLUMN_RATE}, new int[] {android.R.id.text1});

        scAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        workSpinner.setAdapter(scAdapter);
    }

    private void saveToDB(){
        if(st == 0.0d || et == 0.0d || date.getText().toString().isEmpty() ){
            Toast.makeText(this,"all field must be set", Toast.LENGTH_LONG).show();
            return;
        }
        Shift s = new Shift( ((Cursor)workSpinner.getSelectedItem()).getInt(0), st, et,((Cursor)workSpinner.getSelectedItem()).getDouble(2), DateTimeFormatter.DateToLong(date.getText().toString()), breakStatus.isChecked() );

        DAOInterface shiftDao = new ShiftDAO(this);

        shiftDao.create(s);

        Toast.makeText(this, "New Shift Successfully added", Toast.LENGTH_LONG).show();
    }


}
