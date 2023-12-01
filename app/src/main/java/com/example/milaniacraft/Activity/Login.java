package com.example.milaniacraft.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milaniacraft.API.ApiClient;
import com.example.milaniacraft.API.ApiInterface;
import com.example.milaniacraft.ModelLogin.ResponseLogin;
import com.example.milaniacraft.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    TextView Reg, LupaPw;
    EditText getEmail, getPassword;
    Button toLoginHome;
    String email, pass;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        notif(Login.this);

        getEmail = findViewById(R.id.email);
        getPassword = findViewById(R.id.password);

        toLoginHome = findViewById(R.id.button);
        toLoginHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = getEmail.getText().toString();
                pass = getPassword.getText().toString();
                login(email,pass);
            }
        });

        Reg = findViewById(R.id.daftar);
        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);
            }
        });

        LupaPw = findViewById(R.id.lupa_kata_s);
        LupaPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LupaPassword.class);
                startActivity(i);
            }
        });

    }

    public void notif(Activity activity){
        //change color notif bar
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.coklat));
        //set icons notifbar
        View decor = activity.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
    private void login(String email, String pass) {
        if(email.equals("") || pass.equals("")){
            Toast.makeText(Login.this, "Mohon isi semua data",Toast.LENGTH_LONG).show();
        } else {
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponseLogin> loginCall = apiInterface.loginResponse(email,pass);
            loginCall.enqueue(new Callback<ResponseLogin>() {
                @Override
                public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                    if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {
                        String verif = response.body().getData().getStatus();
                        if (!verif.equals("verifikasi")) {
                            Toast.makeText(Login.this, "Akun Belum Verified", Toast.LENGTH_LONG).show();
//                            // Ini untuk menyimpan sesi
//                            sessionManager = new SessionManager(login.this);
//                            DataLogin loginData = response.body().getData();
//                            sessionManager.createLoginSession(loginData);
                            //Ini untuk pindah

                        } else {
                            Toast.makeText(Login.this, response.body().getData().getNama(), Toast.LENGTH_SHORT).show();
                            System.out.println("nama saya adalah" + response.body().getData().getNama());
                            Intent intent = new Intent(Login.this, BottomNavigation.class);
                            intent.putExtra("namaUser", response.body().getData().getNama());
                            intent.putExtra("id_user", response.body().getData().getIdUser());
                            SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREF_ACCOUNT", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("KEY_ID", String.valueOf(response.body().getData().getIdUser()));
                            editor.putString("KEY_NAMA", String.valueOf(response.body().getData().getNama()));
                            editor.apply();
                            startActivity(intent);
                            finish();
                        }
                    }else {
                        Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ResponseLogin> call, Throwable t) {
                    Toast.makeText(Login.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    }
