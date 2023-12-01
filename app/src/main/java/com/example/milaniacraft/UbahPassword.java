package com.example.milaniacraft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.milaniacraft.API.ApiClient;
import com.example.milaniacraft.API.ApiInterface;
import com.example.milaniacraft.Activity.GantiPassword;
import com.example.milaniacraft.Activity.Login;
import com.example.milaniacraft.ModelAkun.ResponseRubahPW;
import com.example.milaniacraft.ModelLogin.UpdatePassword;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahPassword extends AppCompatActivity {
    EditText pw1, pw2;
    Button Rubah;
    ApiInterface apiInterface;
    ImageView btnKembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);
        RubahPassword();

        btnKembali = findViewById(R.id.kembali);
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void RubahPassword() {
        String getid = getIntent().getStringExtra("id_user");
        pw1 = (EditText) findViewById(R.id.passwordBaru);
        pw2 = (EditText) findViewById(R.id.confrimPassword);
        Rubah = (Button) findViewById(R.id.simpanPerubahanPassword);
        Rubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String matchPw1 = pw1.getText().toString();
                String matchPw2 = pw2.getText().toString();
                System.out.println("Password Baru adalah = " + matchPw1);
                if (matchPw1.equals(matchPw2)) {
                    apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponseRubahPW> update = apiInterface.UbahPw(getid, matchPw1);
                    System.out.println("Id Kamu " + getid);
                    System.out.println("Password kamu " + matchPw1);
                    update.enqueue(new Callback<ResponseRubahPW>() {
                        @Override
                        public void onResponse(Call<ResponseRubahPW> call, Response<ResponseRubahPW> response) {
                            if ( response.body().getKode() == 1) {
                                Toast.makeText(UbahPassword.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseRubahPW> call, Throwable t) {

                        }
                    });
                } else {
                    Toast.makeText(UbahPassword.this, "Password Tidak Sama", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}