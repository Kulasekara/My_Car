package com.example.my_car_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddFinish extends AppCompatActivity {

    Button finishBtn,cancelBtn;
    TextView licensedate;
    DatePickerDialog.OnDateSetListener setListenerlicense;
    EditText tirecode;
    String date_license;
    DbHandler dbHandler;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_finish);

        Intent geti=getIntent();
        String nick_name=geti.getStringExtra("NICK_NAME");
        String vehicle_number=geti.getStringExtra("VEHICLE_NUMBER");
        String owner_name=geti.getStringExtra("OWNER_NAME");
        String type=geti.getStringExtra("TYPE");
        String make=geti.getStringExtra("MAKE");
        String fuel=geti.getStringExtra("FUEL_TYPE");
        String transmission=geti.getStringExtra("TRANSMISSION");
        int year_of_menufacture=(geti.getIntExtra("YEAR_OF_MENUFACTURE",0));
        int odo_meter=(geti.getIntExtra("ODO_METER",0));
        int milage=(geti.getIntExtra("WEEKLY_MILAGE",0));
        int belt=(geti.getIntExtra("TIMING_BELT",0));
        String date_oil=geti.getStringExtra("Date_OIL");
        String date_gear=geti.getStringExtra("DATE_GEAR");
        String date_fuel=geti.getStringExtra("DATE_FUEL_FILTER");


        finishBtn=findViewById(R.id.btnfinish);
        cancelBtn=findViewById(R.id.btnnewcancel);
        licensedate=findViewById(R.id.txtoil);
        tirecode=findViewById(R.id.txttire);
        context=this;


        dbHandler=new DbHandler(context);



        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        licensedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        AddFinish.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,setListenerlicense,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(37,33,152)));
                datePickerDialog.show();
            }
        });

        setListenerlicense=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                date_license=year+"-"+month+"-"+dayOfMonth;
                licensedate.setText(date_license);
            }
        };

        SimpleDateFormat formats=new SimpleDateFormat("yyyy-MM-dd");
        Date currentdate=new Date();
        Calendar c=Calendar.getInstance();
        c.setTime(currentdate);
        String enterdate=formats.format(c.getTime());




        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int tire_code=Integer.valueOf(tirecode.getText().toString());


                Module_class moduleClass = new Module_class(nick_name,vehicle_number,owner_name,
                        type,make,fuel,transmission,year_of_menufacture,odo_meter,milage,enterdate,date_oil,
                        date_gear,date_fuel, date_license, tire_code,belt);

              dbHandler.addValues(moduleClass);

              Intent intent=new Intent(getApplicationContext(),MainActivity.class);
              startActivity(intent);

              AddFinish.this.finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddFinish.this.finish();
            }
        });

    }
}