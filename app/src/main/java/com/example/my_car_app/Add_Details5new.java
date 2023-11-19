package com.example.my_car_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class Add_Details5new extends AppCompatActivity {

    Button nxtBtn,cancelBtn;
    TextView startdate;
    DatePickerDialog.OnDateSetListener setListenerstart;
    String date_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_details5new);

        Intent geti=getIntent();
        String nick_name=geti.getStringExtra("NICK_NAME");
        String vehicle_number=geti.getStringExtra("VEHICLE_NUMBER");
        String owner_name=geti.getStringExtra("OWNER_NAME");
        String type=geti.getStringExtra("TYPE");
        String make=geti.getStringExtra("MAKE");
        String fuel=geti.getStringExtra("FUEL_TYPE");
        String transmission=geti.getStringExtra("TRANSMISSION");
        int year_of_menufacture=geti.getIntExtra("YEAR_OF_MENUFACTURE",0);
        int odo_meter=geti.getIntExtra("ODO_METER",0);
        int milage=geti.getIntExtra("WEEKLY_MILAGE",0);


        nxtBtn=findViewById(R.id.btnnewnext);
        cancelBtn=findViewById(R.id.btnnewcancel);
        startdate=findViewById(R.id.txtstart);
        int belt=0;

        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        Add_Details5new.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,setListenerstart,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(37,33,152)));
                datePickerDialog.show();
            }
        });

        setListenerstart=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                date_start=year+"-"+month+"-"+dayOfMonth;
                startdate.setText(date_start);
            }
        };

        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AddFinish.class);
                intent.putExtra("NICK_NAME",nick_name);
                intent.putExtra("VEHICLE_NUMBER",vehicle_number);
                intent.putExtra("OWNER_NAME",owner_name);
                intent.putExtra("TYPE", type);
                intent.putExtra("MAKE",make);
                intent.putExtra("FUEL_TYPE",fuel);
                intent.putExtra("TRANSMISSION",transmission);
                intent.putExtra("YEAR_OF_MENUFACTURE",year_of_menufacture);
                intent.putExtra("ODO_METER",odo_meter);
                intent.putExtra("WEEKLY_MILAGE",milage);
                intent.putExtra("TIMING_BELT",belt);
                intent.putExtra("Date_OIL",date_start);
                intent.putExtra("DATE_GEAR",date_start);
                intent.putExtra("DATE_FUEL_FILTER",date_start);

                startActivity(intent);

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Add_Details5new.this.finish();
            }
        });


    }
}