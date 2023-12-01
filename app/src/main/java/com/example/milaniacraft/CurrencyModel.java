package com.example.milaniacraft;

import android.widget.TextView;

import java.text.DecimalFormat;

public class CurrencyModel {
    private String rupiah;
    private TextView textViewset;

    public CurrencyModel(String rupiah, TextView textViewset) {
        this.rupiah = rupiah;
        this.textViewset = textViewset;
        String S_angka_normal = rupiah.replaceAll("\\,", "");
        double D_angka_Normal = Double.parseDouble(S_angka_normal);
        DecimalFormat DF = new DecimalFormat("#,###,###");
        textViewset.setText("Rp " + DF.format(D_angka_Normal));
    }
}
