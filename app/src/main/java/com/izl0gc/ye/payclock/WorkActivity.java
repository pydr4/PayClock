package com.izl0gc.ye.payclock;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.izl0gc.ye.payclock.db.dao.WorkDAO;
import com.izl0gc.ye.payclock.models.Work;

public class WorkActivity extends AppCompatActivity {

    private EditText name;
    private EditText rate;
    private WorkDAO workDAO;
    private Work work;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        workDAO = new WorkDAO(this);

        //get input boxes
        name = (EditText) findViewById(R.id.work_names);
        rate = (EditText) findViewById(R.id.rate);

        //add saveToDB onclick handler to save button
        Button save = (Button) findViewById(R.id.save_work);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    saveToDB();

            }
        });
    }

    private void saveToDB(){
        String nameText = name.getText().toString(), rateText = rate.getText().toString();
        if(nameText == null || nameText.isEmpty() || rateText == null || rateText.isEmpty() || Double.isNaN(Double.parseDouble(rate.getText().toString()))){
            Toast.makeText(this, "Work needs a name and a wage.", Toast.LENGTH_LONG).show();
            return;
        }
        work = new Work(name.getText().toString(),Double.parseDouble(rate.getText().toString()) );


        work = workDAO.create(work);

        Toast.makeText(this, "New Work Successfully added", Toast.LENGTH_LONG).show();
    }
}
