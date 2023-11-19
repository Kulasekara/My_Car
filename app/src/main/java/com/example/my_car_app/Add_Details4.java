package com.example.my_car_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class Add_Details4 extends AppCompatActivity {

    EditText odometer,milage;
    Button oldbtn,newbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_details4);

        Intent geti=getIntent();
        String nick_name=geti.getStringExtra("NICK_NAME");
        String vehicle_number=geti.getStringExtra("VEHICLE_NUMBER");
        String owner_name=geti.getStringExtra("OWNER_NAME");
        String type=geti.getStringExtra("TYPE");
        String make=geti.getStringExtra("MAKE");
        String fuel=geti.getStringExtra("FUEL_TYPE");
        String transmission=geti.getStringExtra("TRANSMISSION");
        int year_of_menufacture=geti.getIntExtra("YEAR_OF_MENUFACTURE",0);

        odometer=findViewById(R.id.txtodometer);
        milage=findViewById(R.id.txtweeklymilage);
        oldbtn=findViewById(R.id.oldbutton);
        newbtn=findViewById(R.id.newbutton);

        newbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Add_Details5new.class);
                intent.putExtra("NICK_NAME",nick_name);
                intent.putExtra("VEHICLE_NUMBER",vehicle_number);
                intent.putExtra("OWNER_NAME",owner_name);
                intent.putExtra("TYPE", type);
                intent.putExtra("MAKE",make);
                intent.putExtra("FUEL_TYPE",fuel);
                intent.putExtra("TRANSMISSION",transmission);
                intent.putExtra("YEAR_OF_MENUFACTURE",year_of_menufacture);
                int odo_meter=Integer.valueOf(odometer.getText().toString());
                intent.putExtra("ODO_METER", odo_meter);
                int weekly_milage=Integer.valueOf(milage.getText().toString());
                intent.putExtra("WEEKLY_MILAGE", weekly_milage);
                startActivity(intent);

            }
        });
        oldbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Add_Details5old.class);
                intent.putExtra("NICK_NAME",nick_name);
                intent.putExtra("VEHICLE_NUMBER",vehicle_number);
                intent.putExtra("OWNER_NAME",owner_name);
                intent.putExtra("TYPE", type);
                intent.putExtra("MAKE",make);
                intent.putExtra("FUEL_TYPE",fuel);
                intent.putExtra("TRANSMISSION",transmission);
                intent.putExtra("YEAR_OF_MENUFACTURE",year_of_menufacture);
                int odo_meter=Integer.valueOf(odometer.getText().toString());
                intent.putExtra("ODO_METER", odo_meter);
                int weekly_milage=Integer.valueOf(milage.getText().toString());
                intent.putExtra("WEEKLY_MILAGE", weekly_milage);
                startActivity(intent);

            }
        });
    }
}