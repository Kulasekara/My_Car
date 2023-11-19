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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Complete_belt extends AppCompatActivity {


    Context context;
    ImageButton completebtn;
    EditText milagebelt;
    DbHandler dbHandler;
    Module_class moduleClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_complete_belt);

        completebtn = findViewById(R.id.btnbeltcomplete);
        milagebelt=findViewById(R.id.txtbeltcomplete);
        context = this;
        dbHandler = new DbHandler(context);


        Intent geti = getIntent();
        String number = geti.getStringExtra("Vehicle_number");
        moduleClass = dbHandler.getcompleteSingle(number);


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
        String l_oil_change = moduleClass.getL_oil_change();
        String l_gear_oil_change = moduleClass.getL_gear_oil_change();
        String l_fuel_fillter_change = moduleClass.getL_fuel_fillter_change();
        String licence_renewal = moduleClass.getLicence_renewal();

        completebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String vehicale_number = number;
                int timing_belt = Integer.valueOf(milagebelt.getText().toString());;

                Module_class moduleClass = new Module_class(id, nickname, vehicale_number, ower, type,
                        make, fuel_type, transmission_type, year_of_mfd, odometer, milage, enterdate,
                        l_oil_change, l_gear_oil_change, l_fuel_fillter_change, licence_renewal, tire_code, timing_belt);
                dbHandler.completetable1(moduleClass);

                Module_table2 module_table9=new Module_table2(nickname,vehicale_number,"Next timing belt replacement: ",
                        getNext_timing_belt_replacement_date(odometer,milage,timing_belt,enterdate ));
                dbHandler.updatetable2(module_table9);

                startActivity(new Intent(context,MainActivity.class));
            }
        });


    }


    public String getNext_timing_belt_replacement_date(int odo_meter,int milage,int last_milage,String enterdate) {

        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        Calendar c=Calendar.getInstance();//today
        Date enddate;//today

        try {
            enddate=formatter.parse(enterdate);
            c.setTime(enddate);//today
        } catch (ParseException e) {
            e.printStackTrace();
        }


        int daymilage=milage/7;
        int nextodometer=last_milage+75000;
        int daysfornext=(nextodometer-odo_meter)/daymilage;
        c.add(Calendar.DATE, daysfornext);//today
        Date d1=c.getTime();//today

        String nextday=formatter.format(d1);
        return nextday;
    }

}