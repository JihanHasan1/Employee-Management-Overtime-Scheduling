package com.example.overtime_scheduling;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;


public class CustomModeratorAdapter extends ArrayAdapter<Moderator> {

    private final Context context;
    private final ArrayList<Moderator> values;


    public CustomModeratorAdapter(@NonNull Context context, @NonNull ArrayList<Moderator> objects) {
        super(context, -1, objects);
        this.context = context;
        this.values = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.row_moderator, parent, false); //get template

        //Access Elements
        TextView ModeratorName = rowView.findViewById(R.id.tvModeratorName);
        TextView ModeratorContact = rowView.findViewById(R.id.tvContactNumber);
        ImageView ModeratorImage = rowView.findViewById(R.id.imageView);

        //Position = Mobile's Position
        ModeratorName.setText(values.get(position).name);
        ModeratorContact.setText(values.get(position).contact);
        String imgString = values.get(position).imageString;

        byte[] bytes= Base64.decode(imgString,Base64.DEFAULT);
        // Initialize bitmap
        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        // set bitmap on imageView
        ModeratorImage.setImageBitmap(bitmap);

        return rowView;
    }
}
