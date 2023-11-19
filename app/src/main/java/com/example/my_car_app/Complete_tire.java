package com.example.my_car_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Complete_tire extends AppCompatActivity {

    Context context;
    ImageButton completebtn;
    EditText code;
    DbHandler dbHandler;
    Module_class moduleClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_complete_tire);

        completebtn = findViewById(R.id.btntirecomplete);
        code=findViewById(R.id.txttirecomplete);
        context = this;
        dbHandler = new DbHandler(context);


        Intent geti = getIntent();
        String number = geti.getStringExtra("Vehicle_number");
        moduleClass = dbHandler.getcompleteSingle(number);


        int id = moduleClass.getId();
        String nickname = moduleClass.getNick_name();
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
        int timing_belt = moduleClass.getTiming_belt();


        completebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String vehicale_number = number;
                int tire_code = Integer.valueOf(code.getText().toString());;

                Module_class moduleClass = new Module_class(id, nickname, vehicale_number, ower, type,
                        make, fuel_type, transmission_type, year_of_mfd, odometer, milage, enterdate,
                        l_oil_change, l_gear_oil_change, l_fuel_fillter_change, licence_renewal, tire_code, timing_belt);
                dbHandler.completetable1(moduleClass);

                Module_table2 module_table8=new Module_table2(nickname,vehicale_number,"Next tire change: ",
                        getNext_tire_changing_date(tire_code));
                dbHandler.updatetable2(module_table8);

                startActivity(new Intent(context,MainActivity.class));
            }
        });

    }

    public String getNext_tire_changing_date(int number1) {

        String number2=String.valueOf(number1);
        String weeks=number2.substring(0,2);
        int weeknum=Integer.valueOf(weeks)-1;
        String years= number2.substring(2);
        int yearnum=Integer.valueOf(years);

        String startyear=String.valueOf(2000+yearnum);
        String startdate="01/01/"+startyear;

        SimpleDateFormat formats=new SimpleDateFormat("yyyy-MM-dd");
        Calendar c=Calendar.getInstance();
        try {
            c.setTime(formats.parse(startdate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.add(Calendar.WEEK_OF_YEAR,weeknum);
        c.add(Calendar.YEAR,5);

        String newDate=formats.format(c.getTime());

        return newDate;
    }

}