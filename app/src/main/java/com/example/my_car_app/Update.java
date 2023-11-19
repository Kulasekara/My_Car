 package com.example.my_car_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

 public class Update extends AppCompatActivity {


     Context context;
     EditText nick_name,odo_meter,normal_milage;
     Button update_btn;
     DbHandler dbHandler;
     Module_class moduleClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_update);

        context=this;

        dbHandler=new DbHandler(context);
        nick_name=findViewById(R.id.editnickname);
        odo_meter=findViewById(R.id.editodometer);
        normal_milage=findViewById(R.id.editmilage);
        update_btn=findViewById(R.id.btnupdate);

        final int id=getIntent().getIntExtra("id",0);
        moduleClass=dbHandler.getSingle(id);

        SimpleDateFormat formats=new SimpleDateFormat("yyyy-MM-dd");
        Date currentdate=new Date();
        Calendar c=Calendar.getInstance();
        c.setTime(currentdate);
        String newenterdate=formats.format(c.getTime());

        nick_name.setText(moduleClass.getNick_name());
        odo_meter.setText(String.valueOf(calculate(moduleClass.getEnter_date(), newenterdate, moduleClass.getCurrent_odo_meter(), moduleClass.getNormal_weekly_milage())));
        normal_milage.setText(String.valueOf(moduleClass.getNormal_weekly_milage()));

        int tire_code = moduleClass.getTire_code();
        String number = moduleClass.getNumber();
        String ower = moduleClass.getOwer();
        String type = moduleClass.getType();
        String make = moduleClass.getMake();
        String fuel_type = moduleClass.getFuel_type();
        String transmission_type = moduleClass.getTransmission_type();
        int year_of_mfd = moduleClass.getYear_of_mfd();
        String l_oil_change = moduleClass.getL_oil_change();
        String l_gear_oil_change = moduleClass.getL_gear_oil_change();
        String l_fuel_fillter_change = moduleClass.getL_fuel_fillter_change();
        String licence_renewal = moduleClass.getLicence_renewal();
        int timing_belt = moduleClass.getTiming_belt();


        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newnickname=nick_name.getText().toString();
                int newodometer=Integer.valueOf(odo_meter.getText().toString());
                int newmilgae=Integer.valueOf(normal_milage.getText().toString());

                Module_class moduleClass=new Module_class(id,newnickname,number,ower,type,
                        make,fuel_type,transmission_type,year_of_mfd,newodometer,newmilgae,newenterdate,
                        l_oil_change,l_gear_oil_change,l_fuel_fillter_change,licence_renewal,tire_code,timing_belt);
                dbHandler.updatetable1(moduleClass);

                Module_table2 module_table2=new Module_table2(newnickname,number,"Next oil service: ",
                        getNext_oil_servise_date(newodometer,newmilgae,newenterdate,l_oil_change));
                dbHandler.updatetable2(module_table2);

                Module_table2 module_table3=new Module_table2(newnickname,number,"Next gear oil changing: ",
                        getNext_gear_oil_servise_date(newodometer,newmilgae,newenterdate,l_gear_oil_change));
                dbHandler.updatetable2(module_table3);

                Module_table2 module_table4=new Module_table2(newnickname,number,"Next fuel filter changing: ",
                        getNext_fuel_filter_changing_date(newodometer,newmilgae,newenterdate,l_fuel_fillter_change));
                dbHandler.updatetable2(module_table4);

                Module_table2 module_table5=new Module_table2(newnickname,number,"Next licence renewal date: ",
                        getNext_license_renewal_date(licence_renewal));
                dbHandler.updatetable2(module_table5);

                Module_table2 module_table6=new Module_table2(newnickname,number,"Next insurance renewal date: ",
                        getNext_insurance_renewal_date(licence_renewal));
                dbHandler.updatetable2(module_table6);

                Module_table2 module_table7=new Module_table2(newnickname,number,"Next Emission testing date: ",
                        getNext_emission_testing_date(licence_renewal));
                dbHandler.updatetable2(module_table7);

                Module_table2 module_table8=new Module_table2(newnickname,number,"Next tire change: ",
                        getNext_tire_changing_date(tire_code));
                dbHandler.updatetable2(module_table8);

                Module_table2 module_table9=new Module_table2(newnickname,number,"Next timing belt replacement: ",
                        getNext_timing_belt_replacement_date(newodometer,newmilgae,timing_belt,newenterdate ));
                dbHandler.updatetable2(module_table9);

                startActivity(new Intent(context,MainActivity.class));
            }
        });
    }

    public int calculate(String enterdate,String newdate,int odo,int milage){

        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        Date startdate;
        Date enddate;//today

        double numbersofdays=0;
        try {
            startdate=formatter.parse(enterdate);
            enddate=formatter.parse(newdate);
            numbersofdays=getUnitBetweenDates(startdate,enddate, TimeUnit.DAYS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int daymilage=milage/7;
        int milagetotoday= (int) (daymilage*numbersofdays);
        int newodo=odo+milagetotoday;

        return newodo;
    }

     private long getUnitBetweenDates(Date startdate, Date enddate, TimeUnit days) {
         long timedeff=  (enddate.getTime()- startdate.getTime());
         return  days.convert(timedeff,TimeUnit.MILLISECONDS);
     }

     public String getNext_gear_oil_servise_date(int odo_meter,int milage,String enterdate,String last_gearoil_changing) {

         SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
         Calendar c=Calendar.getInstance();//today
         Calendar c2=Calendar.getInstance();
         Date startdate;
         Date enddate;//today

         double numbersofdays=0;
         try {
             startdate=formatter.parse(last_gearoil_changing);
             enddate=formatter.parse(enterdate);
             numbersofdays=getUnitBetweenDates(startdate,enddate, TimeUnit.DAYS);
             c.setTime(enddate);//today
             c2.setTime(startdate);
         } catch (ParseException e) {
             e.printStackTrace();
         }

         int daymilage=milage/7;
         int tolastdaymilage= (int) (daymilage*numbersofdays);
         int lastodometer=odo_meter-tolastdaymilage;
         long nextodometer=lastodometer+20000;
         int daysfornext= (int) ((nextodometer-odo_meter)/daymilage);
         c.add(Calendar.DATE, daysfornext);//today
         c2.add(Calendar.YEAR,1);

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

     public String getNext_fuel_filter_changing_date(int odo_meter,int milage,String enterdate,String last_fuel_filter) {

         SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
         Calendar c=Calendar.getInstance();//today
         Calendar c2=Calendar.getInstance();
         Date startdate;
         Date enddate;//today

         double numbersofdays=0;
         try {
             startdate=formatter.parse(last_fuel_filter);
             enddate=formatter.parse(enterdate);
             numbersofdays=getUnitBetweenDates(startdate,enddate, TimeUnit.DAYS);
             c.setTime(enddate);//today
             c2.setTime(startdate);
         } catch (ParseException e) {
             e.printStackTrace();
         }

         int daymilage=milage/7;
         int tolastdaymilage= (int) (daymilage*numbersofdays);
         int lastodometer=odo_meter-tolastdaymilage;
         int nextodometer=lastodometer+20000;
         int daysfornext=(nextodometer-odo_meter)/daymilage;
         c.add(Calendar.DATE, daysfornext);//today
         c2.add(Calendar.YEAR,1);

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

     public String getNext_license_renewal_date(String licence_renewal) {

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

     public String getNext_insurance_renewal_date(String licence_renewal) {

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
         c2.add(Calendar.DATE,-7);

         Date d2=c2.getTime();

         String nextday=formatter.format(d2);
         return nextday;
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
         c2.add(Calendar.DATE,-7);

         Date d2=c2.getTime();

         String nextday=formatter.format(d2);
         return nextday;
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