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
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Complete_Oil_Service extends AppCompatActivity {


    Context context;
    ImageButton completebtn;
    TextView completedate;
    DatePickerDialog.OnDateSetListener setListener;
    String dateofenter;
    DbHandler dbHandler;
    Module_class moduleClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_complete_oil_service);


        completebtn = findViewById(R.id.btnoilcomplete);
        completedate = findViewById(R.id.datecompeteoil);
        context = this;
        dbHandler = new DbHandler(context);


        Intent geti = getIntent();
        String number = geti.getStringExtra("Vehicle_number");
        dateofenter =geti.getStringExtra("Next_date");
        moduleClass = dbHandler.getcompleteSingle(number);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        completedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Complete_Oil_Service.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(37, 33, 152)));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                dateofenter = year + "-" + month + "-" + dayOfMonth;
            }
        };

        completedate.setText(dateofenter);

        int id = moduleClass.getId();
        String nickname = moduleClass.getNick_name();
        int tire_code = moduleClass.getTire_code();
        String ower = moduleClass.getOwer();
        String type = moduleClass.getType();
        String make = moduleClass.getMake();
        String fuel_type = moduleClass.getFuel_type();
        String transmission_type = moduleClass.getTransmission_type();
        int odometer = moduleClass.getCurrent_odo_meter();
        int milage = moduleClass.getNormal_weekly_milage();
        int year_of_mfd = moduleClass.getYear_of_mfd();
        String enterdate = moduleClass.getEnter_date();
        String l_gear_oil_change = moduleClass.getL_gear_oil_change();
        String l_fuel_fillter_change = moduleClass.getL_fuel_fillter_change();
        String licence_renewal = moduleClass.getLicence_renewal();
        int timing_belt = moduleClass.getTiming_belt();

        completebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String vehicale_number = number;
                String l_oil_change = dateofenter;

                Module_class moduleClass = new Module_class(id, nickname, vehicale_number, ower, type,
                        make, fuel_type, transmission_type, year_of_mfd, odometer, milage, enterdate,
                        l_oil_change, l_gear_oil_change, l_fuel_fillter_change, licence_renewal, tire_code, timing_belt);
                dbHandler.completetable1(moduleClass);

                Module_table2 module_table2=new Module_table2(nickname,vehicale_number,"Next oil service: ",
                        getNext_oil_servise_date(odometer,milage,enterdate,l_oil_change));
                dbHandler.updatetable2(module_table2);

                startActivity(new Intent(context,MainActivity.class));
            }
        });
    }


        private long getUnitBetweenDates(Date startdate, Date enddate, TimeUnit days) {
            long timedeff=  (enddate.getTime()- startdate.getTime());
            return  days.convert(timedeff,TimeUnit.MILLISECONDS);
        }
        public String getNext_oil_servise_date(int odo_meter, int milage,String enterdate, String last_oil_changing) {

            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
            Calendar c=Calendar.getInstance();//today
            Calendar c2=Calendar.getInstance();
            Date startdate;
            Date enddate;//today
            long numbersofdays=0;
            try {
                startdate=formatter.parse(last_oil_changing);
                enddate=formatter.parse(enterdate);
                numbersofdays=getUnitBetweenDates(startdate,enddate, TimeUnit.DAYS);
                c.setTime(enddate);//today
                c2.setTime(startdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long daymilage=milage/7;
            long tolastdaymilage= (int) (daymilage*numbersofdays);
            long lastodometer=odo_meter-tolastdaymilage;
            long nextodometer=lastodometer+6000;
            int daysfornext= (int) ((nextodometer-odo_meter)/daymilage);
            c.add(Calendar.DATE, daysfornext);//today
            c2.add(Calendar.MONTH,6);
            Date d1=c.getTime();//today
            Date d2=c2.getTime();
            long daysformonth=getUnitBetweenDates(d1,d2,TimeUnit.DAYS);
            if(daysformonth>=0){
                String nextday=formatter.format(d1);
                return nextday;
            }
            else{
                String nextday=formatter.format(d2);
                return nextday;
            }


    }
}