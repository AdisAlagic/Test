package com.adisalagic.test;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.adisalagic.test.api.Api;
import com.adisalagic.test.api.ApiStatus;
import com.adisalagic.test.api.json.ApiClass;
import com.adisalagic.test.api.json.Datum;
import com.adisalagic.test.api.json.Entries;
import com.adisalagic.test.api.json.EntryAdd;
import com.adisalagic.test.api.json.Session;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.Protocol;

public class MainActivity extends AppCompatActivity {

    private Button       mRefresh;
    private Button       mAdd;
    private LinearLayout mList;
    private ProgressBar  bar;

    private Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bar = findViewById(R.id.load);
        mRefresh = findViewById(R.id.refresh);
        mAdd = findViewById(R.id.add);
        mList = findViewById(R.id.list);
        setVis(View.INVISIBLE);
        final ApiHandler apiHandler = new ApiHandler();
        createSession();

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDataDialog dataDialog = new AddDataDialog(MainActivity.this, session, new AddDataDialog.OnRequestListener() {
                    @Override
                    public void onRequestStart() {
                        setVis(View.INVISIBLE);
                    }

                    @Override
                    public void onRequestDone(EntryAdd e, String text) {
                        if (e.getStatus() == ApiStatus.OK){
                            refresh();
                        }else {

                        }
                    }
                });
                dataDialog.show();
            }
        });

        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
    }

    private void setVis(int vis) {
        mAdd.setVisibility(vis);
        mList.setVisibility(vis);
        mRefresh.setVisibility(vis);
        bar.setVisibility(vis == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
    }

    private void addFragments(Data... fragment) {
        FragmentManager     fragmentManager     = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (Data data : fragment) {
            fragmentTransaction.add(mList.getId(), data);
        }
        fragmentTransaction.commit();
    }

    private void refresh() {
        setVis(View.INVISIBLE);
        ApiHandler apiHandler = new ApiHandler();
        apiHandler.getEntries(session, new ApiHandler.OnDoneListener() {
            @Override
            public void onDone(ApiClass apiClass) {
                deleteFragments();
                Entries entries = (Entries) apiClass;
                if (entries.getStatus() == ApiStatus.OK) {
                    for (List<Datum> dataList : entries.getData()) {
                        for (Datum datum : dataList) {
                            Data data = new Data(datum);
                            addFragments(data);
                        }
                    }
                }else {
                    ConnectionDialog connectionDialog = new ConnectionDialog(MainActivity.this, new ConnectionDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface) {
                            refresh();
                        }
                    });
                    connectionDialog.show();
                }
            }
        });
        setVis(View.VISIBLE);
    }

    private void deleteFragments() {
        FragmentManager     fragmentManager     = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (Fragment fragment : fragmentManager.getFragments()) {
            fragmentTransaction.detach(fragment);
        }
        fragmentTransaction.commit();
    }

    private void createSession() {
        ApiHandler apiHandler = new ApiHandler();
        apiHandler.createNewSession(new ApiHandler.OnDoneListener() {
            @Override
            public void onDone(ApiClass apiClass) {
                session = (Session) apiClass;
                if (session.getStatus() == ApiStatus.OK) {
                    setVis(View.VISIBLE);
                } else {
                    ConnectionDialog dialog = new ConnectionDialog(MainActivity.this, new ConnectionDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface) {
                            createSession();
                        }
                    });
                    dialog.show();
                }
            }
        });

    }
}