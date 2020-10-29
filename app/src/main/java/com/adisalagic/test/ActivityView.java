package com.adisalagic.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.adisalagic.test.api.json.Datum;

public class ActivityView extends AppCompatActivity {

    public static final String D_DA   = "d_da";
    public static final String D_DM   = "d_dm";
    public static final String D_TEXT = "d_text";


    private TextView mCreated;
    private TextView mModified;
    private TextView mText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_view);
        mCreated = findViewById(R.id.created);
        mModified = findViewById(R.id.modified);
        mText = findViewById(R.id.text);
        fillData();
    }

    public static Intent createIntentForClass(Context context, Datum datum) {
        Intent intent = new Intent();
        intent.setClass(context, ActivityView.class);
        intent.putExtra(D_DA, datum.getDa());
        intent.putExtra(D_DM, datum.getDm());
        intent.putExtra(D_TEXT, datum.getBody());
        return intent;
    }

    private void fillData(){
        Intent intent = getIntent();
        String da = "Дата создания: " + Data.toNormalDate(intent.getStringExtra(D_DA));
        mCreated.setText(da);
        String dm = intent.getStringExtra(D_DM);
        if (!dm.equals(intent.getStringExtra(D_DA))){
            mModified.setText("Дата изменения: " + Data.toNormalDate(intent.getStringExtra(D_DM)));
        }
        mText.setText(intent.getStringExtra(D_TEXT));
    }
}
