package com.adisalagic.test;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class ConnectionDialog {
    public interface OnClickListener{
        void onClick(DialogInterface dialogInterface);
    }

    Context context;
    AlertDialog.Builder builder;
    AlertDialog dialog;


    ConnectionDialog(final Context context, final OnClickListener listener){
        this.context = context;
        builder = new AlertDialog.Builder(context)
                .setTitle("Подключение разорвано")
                .setCancelable(false)
                .setMessage("Повторить?")
                .setPositiveButton("Обновить данные", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onClick(dialog);
                    }
                });

    }

    public void show(){
        dialog = builder.show();
    }

    public void hide(){
        if (dialog != null) dialog.hide();
    }


}
