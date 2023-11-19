package com.example.my_car_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DbHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "My_Car";
    private static final String TABLE_NAME = "User_Details";
    private static final String TABLE_FINAL="Events";

    //column names
    private static final String ID = "id";
    private static final String NICK_NAME = "nick_name";
    private static final String NUMBER = "number";
    private static final String OWNER = "owner";
    private static final String TYPE = "type";
    private static final String MAKE = "make";
    private static final String FUEL_TYPE = "fuel_type";
    private static final String TRANSMISSION_TYPE = "transmission_type";
    private static final String YEAR_OF_MFD = "year_of_mfd";
    private static final String CURRENT_ODO_METER = "current_odo_meter";
    private static final String NORMAL_WEEKLY_MILAGE = "normal_weekly_milage";
    private static final String ENTER_DATE="enter_date";
    private static final String LAST_OIL_CHANGING_DATE = "last_oil_changing_date";
    private static final String LAST_GEAR_OIL_CHANGING_DATE = "last_gear_oil_changing_date";
    private static final String LAST_FUEL_FILTER_CHANGING_DATE = "last_fuel_filter_changing_date";
    private static final String LAST_LICENSE_RENEWAL_DATE = "last_license_renewal_date";
    private static final String TIRE_MANUFACTURE_CODE = "tire_manufacture_code";
    private static final String LAST_TIMING_BELT_REPLACEMENT_MILAGE = "last_timing_belt_replacement_milage";

    private static final String NEXT_EVENTS="next_events";
    private static final String DATES_OF_NEXT="dates_of_next";

    public DbHandler(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TABLE_CREATE_QUERY = "CREATE TABLE "+TABLE_NAME+" "+
                "( "
                +ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"    
                +NICK_NAME+" TEXT, "
                +NUMBER+" TEXT, "
                +OWNER+" TEXT, "
                +TYPE+" TEXT, "
                +MAKE+" TEXT, "
                +FUEL_TYPE+" TEXT, "
                +TRANSMISSION_TYPE +" TEXT, "
                +YEAR_OF_MFD+" INTEGER, "
                +CURRENT_ODO_METER+" INTEGER, "
                +NORMAL_WEEKLY_MILAGE+" INTEGER, "
                +ENTER_DATE+" TEXT, "
                +LAST_OIL_CHANGING_DATE+" TEXT, "
                +LAST_GEAR_OIL_CHANGING_DATE+" TEXT, "
                +LAST_FUEL_FILTER_CHANGING_DATE+" TEXT, "
                +LAST_LICENSE_RENEWAL_DATE+" TEXT, "
                +TIRE_MANUFACTURE_CODE+" INTEGER, "
                +LAST_TIMING_BELT_REPLACEMENT_MILAGE+" INTEGER "+
                ");";

        String TABLE2_CREATE_QUERY = "CREATE TABLE "+TABLE_FINAL+" "+
                "( "
                +ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +NICK_NAME+" TEXT, "
                +NUMBER+" TEXT, "
                +NEXT_EVENTS+" TEXT, "
                +DATES_OF_NEXT+" TEXT "+
                ");";

        db.execSQL(TABLE_CREATE_QUERY);
        db.execSQL(TABLE2_CREATE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS "+TABLE_NAME;
        String DROP_TABLE2_QUERY = "DROP TABLE IF EXISTS "+TABLE_FINAL;
        //drop older table if existed
        db.execSQL(DROP_TABLE_QUERY);
        db.execSQL(DROP_TABLE2_QUERY);
        //create table again
        onCreate(db);

    }

    public  void addValues(Module_class moduleClass){

        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put(NICK_NAME, moduleClass.getNick_name());
        contentValues.put(NUMBER, moduleClass.getNumber());
        contentValues.put(OWNER, moduleClass.getOwer());
        contentValues.put(TYPE, moduleClass.getType());
        contentValues.put(MAKE, moduleClass.getMake());
        contentValues.put(FUEL_TYPE, moduleClass.getFuel_type());
        contentValues.put(TRANSMISSION_TYPE, moduleClass.getTransmission_type());
        contentValues.put(YEAR_OF_MFD, moduleClass.getYear_of_mfd());
        contentValues.put(CURRENT_ODO_METER, moduleClass.getCurrent_odo_meter());
        contentValues.put(NORMAL_WEEKLY_MILAGE, moduleClass.getNormal_weekly_milage());
        contentValues.put(ENTER_DATE, moduleClass.getEnter_date());
        contentValues.put(LAST_OIL_CHANGING_DATE, moduleClass.getL_oil_change());
        contentValues.put(LAST_GEAR_OIL_CHANGING_DATE, moduleClass.getL_gear_oil_change());
        contentValues.put(LAST_FUEL_FILTER_CHANGING_DATE, moduleClass.getL_fuel_fillter_change());
        contentValues.put(LAST_LICENSE_RENEWAL_DATE, moduleClass.getLicence_renewal());
        contentValues.put(TIRE_MANUFACTURE_CODE, moduleClass.getTire_code());
        contentValues.put(LAST_TIMING_BELT_REPLACEMENT_MILAGE, moduleClass.getTiming_belt());

        //save to table
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);

        ContentValues contentValues1=new ContentValues();
        contentValues1.put(NICK_NAME,moduleClass.getNick_name());
        contentValues1.put(NUMBER,moduleClass.getNumber());
        contentValues1.put(NEXT_EVENTS,"Next oil service: ");
        contentValues1.put(DATES_OF_NEXT,moduleClass.getNext_oil_servise_date(moduleClass.getCurrent_odo_meter(),moduleClass.getNormal_weekly_milage(),moduleClass.getEnter_date(),moduleClass.getL_oil_change()));
        sqLiteDatabase.insert(TABLE_FINAL,null,contentValues1);

        ContentValues contentValues2=new ContentValues();
        contentValues2.put(NICK_NAME,moduleClass.getNick_name());
        contentValues2.put(NUMBER,moduleClass.getNumber());
        contentValues2.put(NEXT_EVENTS,"Next gear oil changing: ");
        contentValues2.put(DATES_OF_NEXT,moduleClass.getNext_gear_oil_servise_date(moduleClass.getCurrent_odo_meter(), moduleClass.getNormal_weekly_milage(),moduleClass.getEnter_date(),moduleClass.getL_gear_oil_change()));
        sqLiteDatabase.insert(TABLE_FINAL,null,contentValues2);

        ContentValues contentValues3=new ContentValues();
        contentValues3.put(NICK_NAME,moduleClass.getNick_name());
        contentValues3.put(NUMBER,moduleClass.getNumber());
        contentValues3.put(NEXT_EVENTS,"Next fuel filter changing: ");
        contentValues3.put(DATES_OF_NEXT,moduleClass.getNext_fuel_filter_changing_date(moduleClass.getCurrent_odo_meter(), moduleClass.getNormal_weekly_milage(), moduleClass.getEnter_date(),moduleClass.getL_fuel_fillter_change()));
        sqLiteDatabase.insert(TABLE_FINAL,null,contentValues3);

        ContentValues contentValues4=new ContentValues();
        contentValues4.put(NICK_NAME,moduleClass.getNick_name());
        contentValues4.put(NUMBER,moduleClass.getNumber());
        contentValues4.put(NEXT_EVENTS,"Next licence renewal date: ");
        contentValues4.put(DATES_OF_NEXT,moduleClass.getNext_license_renewal_date(moduleClass.getLicence_renewal()));
        sqLiteDatabase.insert(TABLE_FINAL,null,contentValues4);

        ContentValues contentValues5=new ContentValues();
        contentValues5.put(NICK_NAME,moduleClass.getNick_name());
        contentValues5.put(NUMBER,moduleClass.getNumber());
        contentValues5.put(NEXT_EVENTS,"Next insurance renewal date: ");
        contentValues5.put(DATES_OF_NEXT,moduleClass.getNext_insurance_renewal_date(moduleClass.getLicence_renewal()));
        sqLiteDatabase.insert(TABLE_FINAL,null,contentValues5);

        ContentValues contentValues6=new ContentValues();
        contentValues6.put(NICK_NAME,moduleClass.getNick_name());
        contentValues6.put(NUMBER,moduleClass.getNumber());
        contentValues6.put(NEXT_EVENTS,"Next Emission testing date: ");
        contentValues6.put(DATES_OF_NEXT,moduleClass.getNext_emission_testing_date(moduleClass.getLicence_renewal()));
        sqLiteDatabase.insert(TABLE_FINAL,null,contentValues6);

        ContentValues contentValues7=new ContentValues();
        contentValues7.put(NICK_NAME,moduleClass.getNick_name());
        contentValues7.put(NUMBER,moduleClass.getNumber());
        contentValues7.put(NEXT_EVENTS,"Next tire change: ");
        contentValues7.put(DATES_OF_NEXT,moduleClass.getNext_tire_changing_date(moduleClass.getTire_code()));
        sqLiteDatabase.insert(TABLE_FINAL,null,contentValues7);

        ContentValues contentValues8=new ContentValues();
        contentValues8.put(NICK_NAME,moduleClass.getNick_name());
        contentValues8.put(NUMBER,moduleClass.getNumber());
        contentValues8.put(NEXT_EVENTS,"Next timing belt replacement: ");
        contentValues8.put(DATES_OF_NEXT,moduleClass.getNext_timing_belt_replacement_date(moduleClass.getCurrent_odo_meter(), moduleClass.getNormal_weekly_milage(), moduleClass.getTiming_belt(),moduleClass.getEnter_date()));
        sqLiteDatabase.insert(TABLE_FINAL,null,contentValues8);

        sqLiteDatabase.close();
    }

    public List<Module_class> getAlldetails1(){

        List<Module_class> module_classe=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        String query="SELECT * FROM "+TABLE_NAME;

        Cursor cursor=db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{
                Module_class module=new Module_class();
                module.setId(cursor.getInt(0));
                module.setNick_name(cursor.getString(1));
                module.setNumber(cursor.getString(2));
                module.setOwer(cursor.getString(3));
                module.setType(cursor.getString(4));
                module.setMake(cursor.getString(5));
                module.setFuel_type(cursor.getString(6));
                module.setTransmission_type(cursor.getString(7));
                module.setYear_of_mfd(cursor.getInt(8));
                module.setCurrent_odo_meter(cursor.getInt(9));
                module.setNormal_weekly_milage(cursor.getInt(10));
                module.setEnter_date(cursor.getString(11));
                module.setL_oil_change(cursor.getString(12));
                module.setL_gear_oil_change(cursor.getString(13));
                module.setL_fuel_fillter_change(cursor.getString(14));
                module.setLicence_renewal(cursor.getString(15));
                module.setTire_code(cursor.getInt(16));
                module.setTiming_belt(cursor.getInt(17));


                module_classe.add(module);
            }while (cursor.moveToNext());
        }
        return  module_classe;
    }

    public List<Module_table2> getAlldetails2(){

        List<Module_table2> moduleTable2List=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        String query="SELECT * FROM "+TABLE_FINAL+" ORDER BY "+DATES_OF_NEXT+" ASC ";

        Cursor cursors=db.rawQuery(query,null);

        if(cursors.moveToFirst()){
            do{
                Module_table2 module2=new Module_table2();
                module2.setId(cursors.getInt(0));
                module2.setNick_name(cursors.getString(1));
                module2.setNumber(cursors.getString(2));
                module2.setNext_event(cursors.getString(3));
                module2.setDates_of_next(cursors.getString(4));

                moduleTable2List.add(module2);
            }while (cursors.moveToNext());
        }
        return  moduleTable2List;
    }

    public void deletecar(int id,String name,String number){
        SQLiteDatabase db= getWritableDatabase();
        db.delete(TABLE_NAME,ID+" = ?",new String[]{String.valueOf(id)});
        db.delete(TABLE_FINAL ,NICK_NAME+" = ? AND "+NUMBER+" = ?",new String[]{name,number});
        db.close();
    }

    public Module_class getSingle(int id){

        SQLiteDatabase db=getWritableDatabase();

        Cursor cursor=db.query(TABLE_NAME,new String[]{ID,NICK_NAME,NUMBER,OWNER,TYPE,MAKE,FUEL_TYPE,
                TRANSMISSION_TYPE,YEAR_OF_MFD,CURRENT_ODO_METER,NORMAL_WEEKLY_MILAGE,ENTER_DATE,LAST_OIL_CHANGING_DATE,
                LAST_GEAR_OIL_CHANGING_DATE,LAST_FUEL_FILTER_CHANGING_DATE,LAST_LICENSE_RENEWAL_DATE,
                TIRE_MANUFACTURE_CODE,LAST_TIMING_BELT_REPLACEMENT_MILAGE},ID+" = ?",new String[]{String.valueOf(id)},null,null,null);
    Module_class moduleClass;
    if (cursor != null){
        cursor.moveToFirst();
        moduleClass=new Module_class(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getInt(8),
                cursor.getInt(9),
                cursor.getInt(10),
                cursor.getString(11),
                cursor.getString(12),
                cursor.getString(13),
                cursor.getString(14),
                cursor.getString(15),
                cursor.getInt(16),
                cursor.getInt(17)
                );
        return moduleClass;
    }
    return null;
    }

    public Module_class getcompleteSingle(String number){
        SQLiteDatabase db=getWritableDatabase();

        Cursor cursor=db.query(TABLE_NAME,new String[]{ID,NICK_NAME,NUMBER,OWNER,TYPE,MAKE,FUEL_TYPE,
                TRANSMISSION_TYPE,YEAR_OF_MFD,CURRENT_ODO_METER,NORMAL_WEEKLY_MILAGE,ENTER_DATE,LAST_OIL_CHANGING_DATE,
                LAST_GEAR_OIL_CHANGING_DATE,LAST_FUEL_FILTER_CHANGING_DATE,LAST_LICENSE_RENEWAL_DATE,
                TIRE_MANUFACTURE_CODE,LAST_TIMING_BELT_REPLACEMENT_MILAGE},NUMBER+" = ?",new String[]{number},null,null,null);
        Module_class moduleClass;
        if (cursor != null){
            cursor.moveToFirst();
            moduleClass=new Module_class(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getInt(8),
                    cursor.getInt(9),
                    cursor.getInt(10),
                    cursor.getString(11),
                    cursor.getString(12),
                    cursor.getString(13),
                    cursor.getString(14),
                    cursor.getString(15),
                    cursor.getInt(16),
                    cursor.getInt(17)
            );
            return moduleClass;
        }
        return null;
    }

    public void updatetable1(Module_class moduleClass){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put(NICK_NAME, moduleClass.getNick_name());
        contentValues.put(NUMBER, moduleClass.getNumber());
        contentValues.put(OWNER, moduleClass.getOwer());
        contentValues.put(TYPE, moduleClass.getType());
        contentValues.put(MAKE, moduleClass.getMake());
        contentValues.put(FUEL_TYPE, moduleClass.getFuel_type());
        contentValues.put(TRANSMISSION_TYPE, moduleClass.getTransmission_type());
        contentValues.put(YEAR_OF_MFD, moduleClass.getYear_of_mfd());
        contentValues.put(CURRENT_ODO_METER, moduleClass.getCurrent_odo_meter());
        contentValues.put(NORMAL_WEEKLY_MILAGE, moduleClass.getNormal_weekly_milage());
        contentValues.put(ENTER_DATE, moduleClass.getEnter_date());
        contentValues.put(LAST_OIL_CHANGING_DATE, moduleClass.getL_oil_change());
        contentValues.put(LAST_GEAR_OIL_CHANGING_DATE, moduleClass.getL_gear_oil_change());
        contentValues.put(LAST_FUEL_FILTER_CHANGING_DATE, moduleClass.getL_fuel_fillter_change());
        contentValues.put(LAST_LICENSE_RENEWAL_DATE, moduleClass.getLicence_renewal());
        contentValues.put(TIRE_MANUFACTURE_CODE, moduleClass.getTire_code());
        contentValues.put(LAST_TIMING_BELT_REPLACEMENT_MILAGE, moduleClass.getTiming_belt());

        sqLiteDatabase.update(TABLE_NAME,contentValues,ID+" = ?",
                new String[]{String.valueOf(moduleClass.getId())});

        sqLiteDatabase.close();
    }

    public void completetable1(Module_class moduleClass){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put(NICK_NAME, moduleClass.getNick_name());
        contentValues.put(NUMBER, moduleClass.getNumber());
        contentValues.put(OWNER, moduleClass.getOwer());
        contentValues.put(TYPE, moduleClass.getType());
        contentValues.put(MAKE, moduleClass.getMake());
        contentValues.put(FUEL_TYPE, moduleClass.getFuel_type());
        contentValues.put(TRANSMISSION_TYPE, moduleClass.getTransmission_type());
        contentValues.put(YEAR_OF_MFD, moduleClass.getYear_of_mfd());
        contentValues.put(CURRENT_ODO_METER, moduleClass.getCurrent_odo_meter());
        contentValues.put(NORMAL_WEEKLY_MILAGE, moduleClass.getNormal_weekly_milage());
        contentValues.put(ENTER_DATE, moduleClass.getEnter_date());
        contentValues.put(LAST_OIL_CHANGING_DATE, moduleClass.getL_oil_change());
        contentValues.put(LAST_GEAR_OIL_CHANGING_DATE, moduleClass.getL_gear_oil_change());
        contentValues.put(LAST_FUEL_FILTER_CHANGING_DATE, moduleClass.getL_fuel_fillter_change());
        contentValues.put(LAST_LICENSE_RENEWAL_DATE, moduleClass.getLicence_renewal());
        contentValues.put(TIRE_MANUFACTURE_CODE, moduleClass.getTire_code());
        contentValues.put(LAST_TIMING_BELT_REPLACEMENT_MILAGE, moduleClass.getTiming_belt());

        sqLiteDatabase.update(TABLE_NAME,contentValues,NUMBER+" = ?",
                new String[]{moduleClass.getNumber()});

        sqLiteDatabase.close();
    }

    public void updatetable2(Module_table2 module_table2){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put(NICK_NAME, module_table2.getNick_name());
        contentValues.put(NUMBER, module_table2.getNumber());
        contentValues.put(NEXT_EVENTS,module_table2.getNext_event());
        contentValues.put(DATES_OF_NEXT,module_table2.getDates_of_next());

        sqLiteDatabase.update(TABLE_FINAL,contentValues,NUMBER+" = ? AND "+NEXT_EVENTS+" = ?",new String[]{module_table2.getNumber(), module_table2.getNext_event()});


        sqLiteDatabase.close();
    }


    /*
    public long calcultedays(String dates){

        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");

        Date nextdate ;
        Date newdate=new Date();//today
        Calendar c=Calendar.getInstance();
        c.setTime(newdate);
        Date todate=c.getTime();

        Calendar c1=Calendar.getInstance();

        long numbersofdays=0;

        try {
            nextdate=formatter.parse(dates);
            c1.setTime(nextdate);
            Date nextdatepassing=c1.getTime();
            numbersofdays=getUnitBetweenDates(todate,nextdatepassing, TimeUnit.DAYS);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  numbersofdays+1;
    }

    private long getUnitBetweenDates(Date startdate, Date enddate, TimeUnit days) {
        long timedeff= (enddate.getTime()- startdate.getTime());
        return  days.convert(timedeff,TimeUnit.MILLISECONDS);
    }
*/
}
