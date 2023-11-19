package com.example.my_car_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class Add_Details5old extends AppCompatActivity {



    EditText timingbelt;
    Button nxtbtn,cancelbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_details5old);

        Intent geti=getIntent();
        String nick_name=geti.getStringExtra("NICK_NAME");
        String vehicle_number=geti.getStringExtra("VEHICLE_NUMBER");
        String owner_name=geti.getStringExtra("OWNER_NAME");
        String type=geti.getStringExtra("TYPE");
        String make=geti.getStringExtra("MAKE");
        String fuel=geti.getStringExtra("FUEL_TYPE");
        String transmission=geti.getStringExtra("TRANSMISSION");
        int year_of_menufacture=geti.getIntExtra("YEAR_OF_MENUFACTURE",0);
        int odo_meter=geti.getIntExtra("ODO_METER",0);
        int milage=geti.getIntExtra("WEEKLY_MILAGE",0);

        timingbelt=findViewById(R.id.txtbelt);
        nxtbtn=findViewById(R.id.btnoldnext);
        cancelbtn=findViewById(R.id.btnoldcancel);

        nxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Add_Details5.class);
                intent.putExtra("NICK_NAME",nick_name);
                intent.putExtra("VEHICLE_NUMBER",vehicle_number);
                intent.putExtra("OWNER_NAME",owner_name);
                intent.putExtra("TYPE", type);
                intent.putExtra("MAKE",make);
                intent.putExtra("FUEL_TYPE",fuel);
                intent.putExtra("TRANSMISSION",transmission);
                intent.putExtra("YEAR_OF_MENUFACTURE",year_of_menufacture);
                intent.putExtra("ODO_METER", odo_meter);
                intent.putExtra("WEEKLY_MILAGE", milage);
                int belt=Integer.valueOf(timingbelt.getText().toString());
                intent.putExtra("TIMING_BELT", belt);
                startActivity(intent);

            }
        });
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Add_Details5old.this.finish();
            }
        });

    }
}