package com.example.milaniacraft.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.milaniacraft.API.ApiClient;
import com.example.milaniacraft.API.ApiInterface;
import com.example.milaniacraft.ModelLogin.CheckEmail;
import com.example.milaniacraft.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LupaPassword extends AppCompatActivity {
    private EditText emailedittext;
    private Button resetpasswordbutton, kembali;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);

        notif(LupaPassword.this);
        kembali();
        getEmailUser();

    }

    public void getEmailUser(){
        emailedittext = (EditText) findViewById(R.id.email2);
        resetpasswordbutton = (Button) findViewById(R.id.kirim);
        resetpasswordbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eml = emailedittext.getText().toString();
                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<CheckEmail> check = apiInterface.getCheckEmail(eml);
                check.enqueue(new Callback<CheckEmail>() {
                    @Override
                    public void onResponse(Call<CheckEmail> call, Response<CheckEmail> response) {
                        int kode = response.body().getKode();
                        if (kode == 1){
                            Intent i = new Intent(getApplicationContext(), ValidasiPassword.class);
                            i.putExtra("EmailLupa",eml);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(LupaPassword.this,"Email Tidak Terdaftar",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckEmail> call, Throwable t) {

                    }
                });
            }
        });
    }
    public void kembali(){
        kembali = (Button) findViewById(R.id.back);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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
    }
