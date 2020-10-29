package com.adisalagic.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.adisalagic.test.api.json.Datum;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Data extends Fragment {
    private View rootView;
    private TextView textView;
    String text = "";
    Datum datum;

    Data(Datum data){
        if (data.getBody().length() > 200){
            text = data.getBody().substring(0, 200);
        }else {
            text = data.getBody();
        }
        text += "\nДата создания: " + toNormalDate(data.getDa());
        if (!data.getDm().equals(data.getDa())){
            text += "\nДата изменения: " + toNormalDate(data.getDm());
        }
        datum = data;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_data, container, false);
        textView = rootView.findViewById(R.id.text);
        textView.setText(text);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ActivityView.createIntentForClass(rootView.getContext(), datum));
            }
        });
        return rootView;
    }


    public static String toNormalDate(String d){
        long mil = 0;
        try {
            mil = Long.parseLong(d + "000");
        }catch (Exception e){
            return d;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mil);
        SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance();
        return dateFormat.format(calendar.getTime());
    }
}