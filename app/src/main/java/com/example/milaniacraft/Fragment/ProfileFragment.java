package com.example.milaniacraft.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.milaniacraft.API.ApiClient;
import com.example.milaniacraft.API.ApiInterface;
import com.example.milaniacraft.Activity.Login;
import com.example.milaniacraft.EditProfile;
import com.example.milaniacraft.LihatAlamat;
import com.example.milaniacraft.ModelAkun.ResponseUser;
import com.example.milaniacraft.R;
import com.example.milaniacraft.UbahPassword;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    CardView toAkun, toLogout, toChangePassword, toChangeAlamat;
    TextView nama,email;
    CircleImageView fotoProfil;
    ApiInterface apiInterface;
    public String URI_IMGG = "";
    AlertDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toSettingAkun();
        toLogout();
        setProfile();
        toSettingPassword();
        toSettingAlamat();

    }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void toSettingAkun(){
        toAkun = getActivity().findViewById(R.id.pengaturan_akun);
        toAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id_user = getActivity().getIntent().getStringExtra("id_user");
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SHARED_PREF_ACCOUNT", Context.MODE_PRIVATE);
                id_user = sharedPreferences.getString("KEY_ID", "");
                String namaUser = getActivity().getIntent().getStringExtra("namaUser");
                namaUser = sharedPreferences.getString("KEY_NAMA", "");
                Intent i = new Intent(getContext(), EditProfile.class);
                i.putExtra("id_user", id_user);
                i.putExtra("namaUser", namaUser);
                startActivity(i);
            }
        });
    }

    public void toSettingPassword(){
        toChangePassword = getActivity().findViewById(R.id.ganti_password);
        toChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id_user = getActivity().getIntent().getStringExtra("id_user");
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SHARED_PREF_ACCOUNT", Context.MODE_PRIVATE);
                id_user = sharedPreferences.getString("KEY_ID", "");
                String namaUser = getActivity().getIntent().getStringExtra("namaUser");
                namaUser = sharedPreferences.getString("KEY_NAMA", "");
                Intent i = new Intent(getContext(), UbahPassword.class);
                i.putExtra("id_user", id_user);
                i.putExtra("namaUser", namaUser);
                startActivity(i);
            }
        });
    }

    public void toSettingAlamat(){
        toChangeAlamat = getActivity().findViewById(R.id.ganti_alamat);
        toChangeAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_user = getActivity().getIntent().getStringExtra("id_user");
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SHARED_PREF_ACCOUNT", Context.MODE_PRIVATE);
               id_user = sharedPreferences.getString("KEY_ID", "");
                String namaUser = getActivity().getIntent().getStringExtra("namaUser");
                namaUser = sharedPreferences.getString("KEY_NAMA", "");
                Intent i = new Intent(getContext(), LihatAlamat.class);
                i.putExtra("id_user", id_user);
                i.putExtra("namaUser", namaUser);
                startActivity(i);
            }
        });
    }

    public void setProfile(){
        String id_user = getActivity().getIntent().getStringExtra("id_user");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SHARED_PREF_ACCOUNT", Context.MODE_PRIVATE);
        id_user = sharedPreferences.getString("KEY_ID", "");
        email = (TextView) getView().findViewById(R.id.profile_email);
        nama = (TextView) getView().findViewById(R.id.profile_name);
        fotoProfil = (CircleImageView) getView().findViewById(R.id.profile_user);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseUser> userCall = apiInterface.getDataUser(id_user);
        userCall.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                String m = response.body().getData().getNama();
                String e = response.body().getData().getEmail();
                URI_IMGG = response.body().getData().getImageUser();
                System.out.println("String img == "+URI_IMGG);
                nama.setText(m);
                email.setText(e);
                Picasso.get().load(ApiClient.IMAGES_PROF+URI_IMGG).error(R.drawable.ic_baseline_account_circle_24).into(fotoProfil);
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {

            }
        });

    }

    public  void toLogout(){
        toLogout = getActivity().findViewById(R.id.logout);
        toLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogOut();
                dialog.show();
            }
        });
    }

    public void LogOut(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.alert_logout, null);
        builder.setView(view);
        dialog = builder.create();
        Button Tidak, Ya;

        Tidak = view.findViewById(R.id.tidak);
        Tidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Ya = view.findViewById(R.id.Ya);
        Ya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SHARED_PREF_ACCOUNT", Context.MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();
                Intent i = new Intent(getActivity(), Login.class);
                startActivity(i);
            }
        });
    }
}