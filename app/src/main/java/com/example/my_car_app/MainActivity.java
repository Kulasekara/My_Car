package com.example.my_car_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.os.Build;
import android.view.Window;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView btnHome,btnlist;
    ListView listdates;
    private DbHandler dbHandler;
    private List<Module_table2> module_table2List;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        context=this;
        dbHandler=new DbHandler(context);
        btnHome=findViewById(R.id.homebar);
        listdates=findViewById(R.id.mainList);
        module_table2List=new ArrayList<>();
        module_table2List=dbHandler.getAlldetails2();


        MainAddapter addapter=new MainAddapter(context,R.layout.singlemain,module_table2List);
        listdates.setAdapter(addapter);



        listdates.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Module_table2 module=module_table2List.get(position);
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle(module.getNext_event()+" "+module.getDates_of_next());
                builder.setMessage(module.getNick_name()+"-"+module.getNumber());

                if(module.getNext_event().equals("Next oil service: ")){
                    notification();
                }


                if(module.getNext_event().equals("Next oil service: ")) {
                    builder.setNeutralButton("COMPLETE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(context,Complete_Oil_Service.class);
                            intent.putExtra("Vehicle_number",module.getNumber());
                            intent.putExtra("Next_date",module.getDates_of_next());
                            startActivity(intent);
                        }
                    });
                }
                else if(module.getNext_event().equals("Next gear oil changing: ")) {
                    builder.setNeutralButton("COMPLETE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(context,Complete_gear.class);
                            intent.putExtra("Vehicle_number",module.getNumber());
                            intent.putExtra("Next_date",module.getDates_of_next());
                            startActivity(intent);
                        }
                    });
                }
                else if(module.getNext_event().equals("Next fuel filter changing: ")) {
                    builder.setNeutralButton("COMPLETE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(context,Complete_fuel.class);
                            intent.putExtra("Vehicle_number",module.getNumber());
                            intent.putExtra("Next_date",module.getDates_of_next());
                            startActivity(intent);
                        }
                    });
                }
                else if(module.getNext_event().equals("Next licence renewal date: ")) {
                    builder.setNeutralButton("COMPLETE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(context,Complete_licence.class);
                            intent.putExtra("Vehicle_number",module.getNumber());
                            intent.putExtra("Next_date",module.getDates_of_next());
                            startActivity(intent);
                        }
                    });
                }
                else if(module.getNext_event().equals("Next insurance renewal date: ")) {
                    builder.setNeutralButton("COMPLETE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(context,Complete_insurance.class);
                            intent.putExtra("Vehicle_number",module.getNumber());
                            intent.putExtra("Next_date",module.getDates_of_next());
                            startActivity(intent);
                        }
                    });
                }

                 else if(module.getNext_event().equals("Next Emission testing date: ")) {
                    builder.setNeutralButton("COMPLETE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(context,Complete_emission.class);
                            intent.putExtra("Vehicle_number",module.getNumber());
                            intent.putExtra("Next_date",module.getDates_of_next());
                            startActivity(intent);
                        }
                    });
                }
                else if(module.getNext_event().equals("Next tire change: ")) {
                    builder.setNeutralButton("COMPLETE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(context,Complete_tire.class);
                            intent.putExtra("Vehicle_number",module.getNumber());
                            startActivity(intent);
                        }
                    });
                }

                  else if(module.getNext_event().equals("Next timing belt replacement: ")) {
                    builder.setNeutralButton("COMPLETE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(context,Complete_belt.class);
                            intent.putExtra("Vehicle_number",module.getNumber());
                            startActivity(intent);
                        }
                    });
                }

                builder.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(context,MainActivity.class));
                    }
                });

                builder.show();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Home.class);
                startActivity(i);
            }
        });
    }

    private void notification(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"n")
                .setContentText("hello").setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true).setContentText("14 days for service");

        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());
    }
}