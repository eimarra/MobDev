package com.example.mobdev;
//Name - Richard Sharpe Marshall
//Matric Number - S1829121
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mobdev.Classes.ParseFeed;
import com.example.mobdev.Classes.set_items;
import com.example.mobdev.Recycler.RecycleAdaptor;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.xmlpull.v1.XmlPullParserException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity{
    private ParseFeed xmlObject;
    private RecycleAdaptor mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mDisplayDate = findViewById(R.id.DatePicker);
        mRecyclerView = findViewById(R.id.recView);






        xmlObject = new ParseFeed();

        try {
            mAdapter = new RecycleAdaptor(xmlObject.fetchXml());
            mAdapter.notifyDataSetChanged();

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        month = month+1;
        String today_date = day + "/" + month + "/" +year;
        mDisplayDate.setText(today_date);





        mDisplayDate.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View view){
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);



                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, mDateSetListener, year, month, day);
                dialog.show();
                mAdapter.notifyDataSetChanged();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;

                String filt_date = dayOfMonth + "/" + month + "/" + year;

                SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM");

                Calendar cal2 = Calendar.getInstance();

                cal2.set(year, month, dayOfMonth);

                String FormattedDate = sdf.format(cal2.getTime());

                mDisplayDate.setText(filt_date);

                mAdapter.getFilter().filter(FormattedDate);

                mAdapter.notifyDataSetChanged();
            }
        };




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




} // End of MainActivity