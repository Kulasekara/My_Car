package com.example.my_car_app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Module_class {

    private int id;
    private int current_odo_meter,normal_weekly_milage,tire_code,year_of_mfd,timing_belt;
    private String nick_name,number,ower,type,make,fuel_type,transmission_type;
    private String enter_date,l_oil_change,l_gear_oil_change,l_fuel_fillter_change,licence_renewal;

    //constructers

    public Module_class(){
    }

    public Module_class(int id, String nick_name, String number, String ower, String type, String make,
                        String fuel_type, String transmission_type, int year_of_mfd, int current_odo_meter,
                        int normal_weekly_milage,String enter_date, String l_oil_change, String l_gear_oil_change, String l_fuel_fillter_change,
                        String licence_renewal, int tire_code, int timing_belt) {
        this.id = id;
        this.current_odo_meter = current_odo_meter;
        this.normal_weekly_milage = normal_weekly_milage;
        this.tire_code = tire_code;
        this.nick_name = nick_name;
        this.number = number;
        this.ower = ower;
        this.type = type;
        this.make = make;
        this.fuel_type = fuel_type;
        this.transmission_type = transmission_type;
        this.year_of_mfd = year_of_mfd;
        this.enter_date=enter_date;
        this.l_oil_change = l_oil_change;
        this.l_gear_oil_change = l_gear_oil_change;
        this.l_fuel_fillter_change = l_fuel_fillter_change;
        this.licence_renewal = licence_renewal;
        this.timing_belt = timing_belt;
    }

    public Module_class(String nick_name, String number, String ower, String type, String make,
                        String fuel_type, String transmission_type, int year_of_mfd, int current_odo_meter,
                        int normal_weekly_milage,String enter_date, String l_oil_change, String l_gear_oil_change, String l_fuel_fillter_change,
                        String licence_renewal, int tire_code, int timing_belt) {
        this.current_odo_meter = current_odo_meter;
        this.normal_weekly_milage = normal_weekly_milage;
        this.tire_code = tire_code;
        this.nick_name = nick_name;
        this.number = number;
        this.ower = ower;
        this.type = type;
        this.make = make;
        this.fuel_type = fuel_type;
        this.transmission_type = transmission_type;
        this.year_of_mfd = year_of_mfd;
        this.enter_date=enter_date;
        this.l_oil_change = l_oil_change;
        this.l_gear_oil_change = l_gear_oil_change;
        this.l_fuel_fillter_change = l_fuel_fillter_change;
        this.licence_renewal = licence_renewal;
        this.timing_belt = timing_belt;
    }

    //getter
    public int getId() {
        return id;
    }

    public int getCurrent_odo_meter() {
        return current_odo_meter;
    }

    public int getNormal_weekly_milage() {
        return normal_weekly_milage;
    }

    public int getTire_code() {
        return tire_code;
    }

    public String getNick_name() {
        return nick_name;
    }

    public String getNumber() {
        return number;
    }

    public String getOwer() {
        return ower;
    }

    public String getType() {
        return type;
    }

    public String getMake() {
        return make;
    }

    public String getFuel_type() {
        return fuel_type;
    }

    public String getTransmission_type() {
        return transmission_type;
    }

    public int getYear_of_mfd() {
        return year_of_mfd;
    }

    public String getL_oil_change() {
        return l_oil_change;
    }

    public String getL_gear_oil_change() {
        return l_gear_oil_change;
    }

    public String getL_fuel_fillter_change() {
        return l_fuel_fillter_change;
    }

    public String getLicence_renewal() {
        return licence_renewal;
    }

    public int getTiming_belt() {
        return timing_belt;
    }

    public String getEnter_date() {
        return enter_date;
    }

    public void setEnter_date(String enter_date) {
        this.enter_date = enter_date;
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


    //setter

    public void setId(int id) {
        this.id = id;
    }

    public void setCurrent_odo_meter(int current_odo_meter) {
        this.current_odo_meter = current_odo_meter;
    }

    public void setNormal_weekly_milage(int normal_weekly_milage) {
        this.normal_weekly_milage = normal_weekly_milage;
    }

    public void setTire_code(int tire_code) {
        this.tire_code = tire_code;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setOwer(String ower) {
        this.ower = ower;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setFuel_type(String fuel_type) {
        this.fuel_type = fuel_type;
    }

    public void setTransmission_type(String transmission_type) {
        this.transmission_type = transmission_type;
    }

    public void setYear_of_mfd(int year_of_mfd) {
        this.year_of_mfd = year_of_mfd;
    }

    public void setL_oil_change(String l_oil_change) {
        this.l_oil_change = l_oil_change;
    }

    public void setL_gear_oil_change(String l_gear_oil_change) {
        this.l_gear_oil_change = l_gear_oil_change;
    }

    public void setL_fuel_fillter_change(String l_fuel_fillter_change) {
        this.l_fuel_fillter_change = l_fuel_fillter_change;
    }

    public void setLicence_renewal(String licence_renewal) {
        this.licence_renewal = licence_renewal;
    }


    public void setTiming_belt(int timing_belt) {
        this.timing_belt = timing_belt;
    }
}

