package com.example.makemycall.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.makemycall.Adapter.HomeAdapter;
import com.example.makemycall.R;
import com.example.makemycall.Utill.SaveAndLoadContact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    private Context context;
    private ListView myContactListView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.home,container,false);
        myContactListView=view.findViewById(R.id.contactList);
        ArrayList<String> contacts=getContact();
        myContactListView.setAdapter(new HomeAdapter(getContext(),contacts));

        return view;
    }

    private ArrayList<String> getContact() {

        SaveAndLoadContact saveAndLoadContact=new SaveAndLoadContact(getContext());
        Map<String, String> conactHashMap=saveAndLoadContact.loadMap();
        ArrayList<String> arrayList=new ArrayList<>();
        for(String key:conactHashMap.keySet()){
            arrayList.add(key+":"+conactHashMap.get(key));
        }
        return arrayList;
    }
}
