package com.example.my_car_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {


    ImageView btnhome,btnbell,btnlist;
    ImageButton addBtn;
    Context context;
    private List<Module_class> module_classList;
    DbHandler dbHandler;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);
        context=this;


        dbHandler=new DbHandler(context);
        addBtn=findViewById(R.id.btnAdd);
        btnbell=findViewById(R.id.bellbarhome);
        listView=findViewById(R.id.homelist);
        module_classList=new ArrayList<>();
        module_classList=dbHandler.getAlldetails1();

        homeAddapter addapter=new homeAddapter(context,R.layout.singlehome,module_classList);

        listView.setAdapter(addapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Module_class module=module_classList.get(position);
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle(module.getNick_name());
                builder.setMessage(module.getNumber());


                builder.setNeutralButton("UPDATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(context,Update.class);
                        intent.putExtra("id",module.getId());
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.deletecar(module.getId(),module.getNick_name(),module.getNumber());
                        startActivity(new Intent(context,Home.class));
                    }
                });

                builder.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(context,Home.class));
                    }
                });

                builder.show();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent i=new Intent(getApplicationContext(),Add_Details1.class);
                Intent i=new Intent(getApplicationContext(),Add1.class);
                startActivity(i);
            }
        });

        btnbell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });
    }
}