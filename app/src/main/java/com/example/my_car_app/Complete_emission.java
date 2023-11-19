
package com.example.my_car_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Complete_emission extends AppCompatActivity {

    Context context;
    ImageButton completebtn;
    DbHandler dbHandler;
    Module_class moduleClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_complete_emission);

        completebtn = findViewById(R.id.btngEmissioncomplete);
        context = this;
        dbHandler = new DbHandler(context);


        Intent geti = getIntent();
        String number = geti.getStringExtra("Vehicle_number");
        String dateofenter =geti.getStringExtra("Next_date");
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
        int timing_belt = moduleClass.getTiming_belt();

        completebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String vehicale_number = number;
                String licence_renewal = dateofenter;

                Module_class moduleClass = new Module_class(id, nickname, vehicale_number, ower, type,
                        make, fuel_type, transmission_type, year_of_mfd, odometer, milage, enterdate,
                        l_oil_change, l_gear_oil_change, l_fuel_fillter_change, licence_renewal, tire_code, timing_belt);
                dbHandler.completetable1(moduleClass);


                Module_table2 module_table7=new Module_table2(nickname,vehicale_number,"Next Emission testing date: ",
                        getNext_emission_testing_date(licence_renewal));
                dbHandler.updatetable2(module_table7);


                startActivity(new Intent(context,MainActivity.class));
            }
        });

    }

    public String getNext_emission_testing_date(String licence_renewal) {

        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        Calendar c2=Calendar.getInstance();
        Date startdate;

        try {
            startdate=formatter.parse(licence_renewal);
            c2.setTime(startdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        c2.add(Calendar.YEAR,1);

        Date d2=c2.getTime();

        String nextday=formatter.format(d2);
        return nextday;
    }
}