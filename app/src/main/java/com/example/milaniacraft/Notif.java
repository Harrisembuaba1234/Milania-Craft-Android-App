package com.example.milaniacraft;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.TextView;
import com.example.milaniacraft.R;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;

public class Notif extends AlertDialog {

    private Context context;
    private TextView nullHead;
    private TextView nullDesc;
    private AppCompatButton nullbterror;
    private AlertDialog alert;

    public Notif(Context context, String textHead, String textDesc) {
        super(context);
        this.context = context;
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        View view = getLayoutInflater().inflate(R.layout.notif_dialog, null);
        builder.setView(view);
        alert = builder.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.show();
        nullbterror = view.findViewById(R.id.nulldialog_btclose);
        nullHead = view.findViewById(R.id.nulldialog_header);
        nullDesc = view.findViewById(R.id.nulldialog_body);
        nullbterror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
            }
        });
        nullHead.setText(textHead);
        nullDesc.setText(textDesc);
    }
    }


