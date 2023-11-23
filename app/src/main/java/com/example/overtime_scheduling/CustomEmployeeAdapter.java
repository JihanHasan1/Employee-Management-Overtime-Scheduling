package com.example.overtime_scheduling;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;


public class CustomEmployeeAdapter extends ArrayAdapter<Employee> implements Filterable {

    private final Context context;
    private ArrayList<Employee> values;


    public CustomEmployeeAdapter(@NonNull Context context, @NonNull ArrayList<Employee> objects) {
        super(context, -1, objects);
        this.context = context;
        this.values = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.row_employee, parent, false); //get template

        //Access Elements
        TextView EmployeeName = rowView.findViewById(R.id.tvEmployeeName);
        TextView EmployeeEmail = rowView.findViewById(R.id.tvEmailHere);
        TextView EmployeeSlotId = rowView.findViewById(R.id.tvSlotId);
        ImageView EmployeeImage = rowView.findViewById(R.id.imageView);

        //Position = Mobile's Position
        EmployeeName.setText(values.get(position).name);
        EmployeeEmail.setText(values.get(position).email);
        EmployeeSlotId.setText(String.valueOf(values.get(position).slot_id));
        String imgString = values.get(position).imageString;

        byte[] bytes= Base64.decode(imgString,Base64.DEFAULT);
        // Initialize bitmap
        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        // set bitmap on imageView
        EmployeeImage.setImageBitmap(bitmap);

        return rowView;
    }

    public void searchDataList(ArrayList<Employee> searchList){
        values = searchList;
        notifyDataSetChanged();
    }
}
