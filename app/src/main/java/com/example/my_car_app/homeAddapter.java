package com.example.my_car_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class homeAddapter extends ArrayAdapter<Module_class> {

    private Context context;
    private int resourses;
    List<Module_class> module_classList;

    public homeAddapter(@NonNull Context context, int resource, @NonNull List<Module_class> module_classList) {
        super(context, resource, module_classList);

        this.context=context;
        this.resourses=resource;
        this.module_classList=module_classList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View row=inflater.inflate(resourses,parent,false);

        TextView nickname=row.findViewById(R.id.txtnicknamelist);

        Module_class moduleClass=module_classList.get(position);
        nickname.setText(moduleClass.getNick_name());

        return row;
    }
}
