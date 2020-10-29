package com.adisalagic.test;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.adisalagic.test.api.ApiStatus;
import com.adisalagic.test.api.json.ApiClass;
import com.adisalagic.test.api.json.EntryAdd;
import com.adisalagic.test.api.json.Session;

public class AddDataDialog {
    public interface OnRequestListener {
        void onRequestStart();
        void onRequestDone(EntryAdd e, String text);
    }

    AlertDialog.Builder builder;
    EditText            editText;


    AddDataDialog(Context context, final Session session, final OnRequestListener listener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View           root     = inflater.inflate(R.layout.dialog_create_view, null);
        editText = root.findViewById(R.id.data);
        builder = new AlertDialog.Builder(context);
        builder
                .setTitle("Добавить данные")
                .setView(root)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendRequest(session, listener);
                        
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
    }

    public void show() {
        builder.show();
    }

    public void sendRequest(final Session session, final OnRequestListener listener){
        if (editText.getText().toString().isEmpty()) {
            return;
        }
        listener.onRequestStart();
        ApiHandler apiHandler = new ApiHandler();
        apiHandler.addNewEntry(session, editText.getText().toString(), new ApiHandler.OnDoneListener() {
            @Override
            public void onDone(ApiClass apiClass) {
                EntryAdd add = (EntryAdd) apiClass;
                if (add.getStatus() == ApiStatus.FAILED){
                    ConnectionDialog dialog = new ConnectionDialog(builder.getContext(), new ConnectionDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface) {
                            sendRequest(session, listener);
                        }
                    });
                    dialog.show();
                }
                listener.onRequestDone(add, editText.getText().toString());
            }
        });
    }

}
