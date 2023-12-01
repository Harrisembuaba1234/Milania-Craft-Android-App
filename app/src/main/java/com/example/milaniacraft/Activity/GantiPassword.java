package com.example.milaniacraft.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.milaniacraft.API.ApiClient;
import com.example.milaniacraft.API.ApiInterface;
import com.example.milaniacraft.ModelLogin.UpdatePassword;
import com.example.milaniacraft.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GantiPassword extends AppCompatActivity {
    EditText pw1, pw2;
    Button Riset;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganti_password);
        setPassword();
    }

    public void setPassword() {
        String getGmail = getIntent().getStringExtra("EmailLupa");
        System.out.println("Email akan dirubah adalah " + getGmail);
        pw1 = (EditText) findViewById(R.id.pw1);
        pw2 = (EditText) findViewById(R.id.pw2);
        Riset = (Button) findViewById(R.id.Reset);
        Riset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String matchPw1 = pw1.getText().toString();
                String matchPw2 = pw2.getText().toString();
                System.out.println("Password Baru adalah = " + matchPw1);
                if (matchPw1.equals(matchPw2)) {
                    apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<UpdatePassword> update = apiInterface.setNewPassword(getGmail, matchPw1);
                    System.out.println("Email Kamu " + getGmail);
                    System.out.println("Password kamu " + matchPw1);
                    update.enqueue(new Callback<UpdatePassword>() {
                        @Override
                        public void onResponse(Call<UpdatePassword> call, Response<UpdatePassword> response) {
                            if ( response.body().getKode() == 1) {
                                Toast.makeText(GantiPassword.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), Login.class);
                                startActivity(i);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdatePassword> call, Throwable t) {

                        }
                    });
                } else {
                    Toast.makeText(GantiPassword.this, "Password Tidak Sama", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}