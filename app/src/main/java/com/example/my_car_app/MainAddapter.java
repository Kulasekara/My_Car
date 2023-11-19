package com.example.my_car_app;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainAddapter  extends ArrayAdapter<Module_table2>{

    private Context context;
    private int resourses;
    List<Module_table2> module_table2List;

    public MainAddapter(@NonNull Context context, int resource,  List<Module_table2> module_table2List) {
        super(context, resource, module_table2List);

        this.context=context;
        this.resourses=resource;
        this.module_table2List=module_table2List;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View row=inflater.inflate(resourses,parent,false);

        TextView event=row.findViewById(R.id.eventtxt);
        TextView name=row.findViewById(R.id.nametxt);
        TextView dates=row.findViewById(R.id.datetxxt);
        TextView todays=row.findViewById(R.id.todaystxt);
        ImageView image=row.findViewById(R.id.alert);


        Module_table2 module_table2=module_table2List.get(position);
        event.setText(module_table2.getNext_event());
        name.setText(module_table2.getNick_name()+" "+module_table2.getNumber());
        dates.setText(module_table2.getDates_of_next());
        image.setVisibility(row.INVISIBLE);
        if(calculate(module_table2.getDates_of_next())>=0){
            String numberto=String.valueOf(calculate(module_table2.getDates_of_next()));
            todays.setText(numberto+" More Days!");
        }
        else{
            String numberto=String.valueOf(calculate(module_table2.getDates_of_next()) * (-1));
            todays.setText("Late "+numberto+" Days!");
            image.setVisibility(View.VISIBLE);
        }


        return row;
    }

    public long calculate(String next_date){

        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        Calendar c=Calendar.getInstance();//today
        Calendar c2=Calendar.getInstance();
        Date nextday;
        Date todays=new Date();//today
        long numbersofdays=0;
        try {
            nextday=formatter.parse(next_date);
            numbersofdays=getUnitBetweenDates(todays,nextday, TimeUnit.DAYS);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return numbersofdays;
    }
    private long getUnitBetweenDates(Date startdate, Date enddate, TimeUnit days) {
        long timedeff= (enddate.getTime()- startdate.getTime());
        return days.convert(timedeff,TimeUnit.MILLISECONDS);
    }


}
