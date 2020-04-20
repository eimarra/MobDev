package com.example.mobdev;
//Name - Richard Sharpe Marshall
//Matric Number - S1829121
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.mobdev.Classes.IncidentParse;
import com.example.mobdev.Classes.ParseFeed;
import com.example.mobdev.Recycler.RecycleAdaptor;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.util.Calendar;

public class SecondActivity extends AppCompatActivity {
    private IncidentParse xmlObject;
    private RecycleAdaptor mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recView);

        mDisplayDate = findViewById(R.id.DatePicker);

        mDisplayDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(SecondActivity.this, mDateSetListener, year, month, day);
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                mDisplayDate.setText(date);
                mAdapter.getFilter().filter(date);
            }
        };

        xmlObject = new IncidentParse();
        try {
            mAdapter = new RecycleAdaptor(xmlObject.fetchXml());
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

    public void onRoadworksClick(View v){
        Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(myIntent);

    }

    public void onIncidentsClick(View v){
        Intent myIntent = new Intent(getBaseContext(), SecondActivity.class);
        startActivity(myIntent);

    }

    public void onPlannedClick(View v){
         Intent myIntent = new Intent(getBaseContext(), ThirdActivity.class);
         startActivity(myIntent);

    }
}
