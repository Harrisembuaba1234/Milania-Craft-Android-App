package com.example.milaniacraft;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.milaniacraft.ModelLogin.DataLogin;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class SaveAccount {
    private static final String LIST_PEMBELI = "list_pembeli";

    public static void writeDataPembeli(Context context, DataLogin dataLogin){
        Gson gson = new Gson();
        String jsonString = gson.toJson(dataLogin);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LIST_PEMBELI, jsonString);
        editor.apply();
    }

    public static DataLogin readDataPembeli(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = pref.getString(LIST_PEMBELI, "");
        Gson gson = new Gson();
        Type type = new TypeToken<DataLogin>() {}.getType();
        DataLogin dataPembeli = gson.fromJson(jsonString, type);
        return dataPembeli;
    }
}
