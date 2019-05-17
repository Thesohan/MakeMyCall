package com.example.makemycall.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.makemycall.R;

import java.util.ArrayList;

public class HomeAdapter extends ArrayAdapter<ArrayList<String>> {

    private Context context;
    private ArrayList<String> myContact;
    public HomeAdapter(Context context, ArrayList<String> myContact) {
        super(context,0);
        this.context=context;
        this.myContact=myContact;
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View view=layoutInflater.inflate(R.layout.home_row,parent,false);
        TextView nameTextView=view.findViewById(R.id.nameTextView);
        TextView noTextView=view.findViewById(R.id.noTextView);
        String string=myContact.get(position);
        String keyValue[]=string.split(":");
        nameTextView.setText(keyValue[0]);
        noTextView.setText(keyValue[1]);
        return view;
    }

    @Override
    public int getCount() {
        return myContact.size();
    }
}
