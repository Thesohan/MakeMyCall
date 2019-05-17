package com.example.makemycall.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telecom.TelecomManager;
import android.telephony.PhoneStateListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makemycall.R;
import com.example.makemycall.Utill.SaveAndLoadContact;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CallNow extends Fragment {
    private Context context;
    private TextView counterTextView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context=getContext();
        View view=LayoutInflater.from(context).inflate(R.layout.call_now,container,false);
        counterTextView=view.findViewById(R.id.counterTextView);

        SaveAndLoadContact saveAndLoadContact=new SaveAndLoadContact(context);
        Map<String,String> myContactMap=saveAndLoadContact.loadMap();
        final ArrayList<String> contactList=new ArrayList<>();
        for(String key:myContactMap.keySet()){
            contactList.add(myContactMap.get(key));
        }

        //since int can't modified inside the implemented method of an interface.
        //this time is just for desplay purpose
        final int[] i = {0};
        final long milisInFutre=contactList.size()*1000*5+1000;
        new CountDownTimer(milisInFutre, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                long millis=millisUntilFinished;
                @SuppressLint("DefaultLocale") String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                        TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                        TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
                counterTextView.setText(hms);

            }

            @Override
            public void onFinish() {

            }
        }.start();

        //this time shcedule the calls and disconnect the calls if it's are currently  active.
        new CountDownTimer(milisInFutre, 1000*5) {
            @Override
            public void onTick(long millisUntilFinished) {
                TelecomManager tm = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    tm = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
                }

                if (tm != null) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                        @SuppressLint("MissingPermission") boolean success = tm.endCall();
                    }
                    // success == true if call was terminated.
                }

                if(i[0]<contactList.size()) {
                    String number = contactList.get(i[0]);

                    i[0]++;
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + number));
                    startActivity(callIntent);
                    Log.d("d", "" + millisUntilFinished);
                }
                }

            @Override
            public void onFinish() {
                Log.d("d","finished");
            }
        }.start();

        Log.d("outside","yeah it's outside");
        return view;
    }
}
