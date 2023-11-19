package com.example.my_car_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Add1 extends AppCompatActivity {

    TextView cartext,maketext,fueltext,transmissiontext;
    EditText nickname,vehiclenumber,ownername,year;
    Button nextbtn;
    String clickmake,clicktransmission,clickfuel,clickcar;
    Context context;
    int yearofmanufacture=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add1);


        cartext=findViewById(R.id.selectcar);
        maketext=findViewById(R.id.selectmake);
        fueltext=findViewById(R.id.selectfuel);
        transmissiontext=findViewById(R.id.selecttrans);
        nickname=findViewById(R.id.textnickname);
        vehiclenumber=findViewById(R.id.textnumber);
        ownername=findViewById(R.id.textowner);
        year=findViewById(R.id.textyear);
        nextbtn=findViewById(R.id.btnnext);
        context=this;

        ListView listcar=new ListView(this);
        List<String> car=new ArrayList<>();
        car.add("Car");
        car.add("Van");
        car.add("SUV");

        ArrayAdapter<String> caradapter= new ArrayAdapter<String>(this,R.layout.listitem,car);
        listcar.setAdapter(caradapter);

        AlertDialog.Builder buildcar=new AlertDialog.Builder(Add1.this);
        buildcar.setCancelable(true);
        buildcar.setView(listcar);
        final AlertDialog diaogcar=buildcar.create();

        cartext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaogcar.show();
            }
        });

        listcar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickcar=String.valueOf(parent.getItemAtPosition(position));
                cartext.setText(clickcar);
                diaogcar.dismiss();
            }
        });

        ListView listmake=new ListView(this);
        List<String> make=new ArrayList<>();
        make.add("BMW");
        make.add("Toyota");
        make.add("Nisan");

        ArrayAdapter<String> makeadapter= new ArrayAdapter<String>(this,R.layout.listitem,make);
        listmake.setAdapter(makeadapter);

        AlertDialog.Builder buildmake=new AlertDialog.Builder(Add1.this);
        buildmake.setCancelable(true);
        buildmake.setView(listmake);
        final AlertDialog diaogmake=buildmake.create();

        maketext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaogmake.show();
            }
        });

        listmake.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickmake=String.valueOf(parent.getItemAtPosition(position));
                maketext.setText(clickmake);
                diaogmake.dismiss();
            }
        });

        ListView listtrans=new ListView(this);
        List<String> trans=new ArrayList<>();
        trans.add("Auto");
        trans.add("Manual");


        ArrayAdapter<String> transadapter= new ArrayAdapter<String>(this,R.layout.listitem,trans);
        listtrans.setAdapter(transadapter);

        AlertDialog.Builder buildtrans=new AlertDialog.Builder(Add1.this);
        buildtrans.setCancelable(true);
        buildtrans.setView(listtrans);
        final AlertDialog diaogtrans=buildtrans.create();

        transmissiontext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaogtrans.show();
            }
        });

        listtrans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clicktransmission=String.valueOf(parent.getItemAtPosition(position));
                transmissiontext.setText(clicktransmission);
                diaogtrans.dismiss();
            }
        });

        ListView listfuel=new ListView(this);
        List<String> fuel=new ArrayList<>();
        fuel.add("Petrol");
        fuel.add("Diesel");
        fuel.add("Protrol hybrid");
        fuel.add("Diesel hybrid");

        ArrayAdapter<String> fueladapter= new ArrayAdapter<String>(this,R.layout.listitem,fuel);
        listfuel.setAdapter(fueladapter);

        AlertDialog.Builder buildfuel=new AlertDialog.Builder(Add1.this);
        buildfuel.setCancelable(true);
        buildfuel.setView(listfuel);
        final AlertDialog diaogfuel=buildfuel.create();

        fueltext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaogfuel.show();
            }
        });

        listfuel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickfuel=String.valueOf(parent.getItemAtPosition(position));
                fueltext.setText(clickfuel);
                diaogfuel.dismiss();
            }
        });


        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext() ,Add_Details4.class);
                String nick_name=nickname.getText().toString();
                String vehicle_number=vehiclenumber.getText().toString();
                String owner_name=ownername.getText().toString();
                yearofmanufacture=Integer.valueOf(year.getText().toString());
                String cartype=cartext.getText().toString();
                String maketype=maketext.getText().toString();
                String fueltype=fueltext.getText().toString();
                String transtype=transmissiontext.getText().toString();

                if(nick_name.length()==0 || vehicle_number.length()==0 || owner_name.length()==0){
                    Toast.makeText(getApplicationContext(), "Please filling the blanks", Toast.LENGTH_SHORT).show();
                }
                else if(cartype.equals("Select") || maketype.equals("Select") || fueltype.equals("Selcet") || transtype.equals("Select")){
                    Toast.makeText(getApplicationContext(), "Please Select a type", Toast.LENGTH_SHORT).show();
                }
                else if(yearofmanufacture==0){
                    Toast.makeText(getApplicationContext(), "Please filling the blanks", Toast.LENGTH_SHORT).show();

                }

                else{
                    intent.putExtra("NICK_NAME", nick_name);
                    intent.putExtra("VEHICLE_NUMBER", vehicle_number);
                    intent.putExtra("OWNER_NAME", owner_name);
                    intent.putExtra("YEAR_OF_MANUFACTURE",yearofmanufacture);
                    intent.putExtra("TYPE", cartype);
                    intent.putExtra("MAKE",maketype);
                    intent.putExtra("FUEL_TYPE",fueltype);
                    intent.putExtra("TRANSMISSION",transtype);
                    startActivity(intent);
                }
            }
        });

    }
}