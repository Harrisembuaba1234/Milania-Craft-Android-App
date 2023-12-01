package com.example.milaniacraft.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.milaniacraft.API.ApiClient;
import com.example.milaniacraft.API.ApiInterface;
import com.example.milaniacraft.JavaMailAPI;
import com.example.milaniacraft.ModelLogin.ResponseEmail;
import com.example.milaniacraft.ModelLogin.VerifEmail;
import com.example.milaniacraft.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValidasiRegister extends AppCompatActivity {
    TextView desc, sendCodeAgain;
    Button btnContinue;
    String id_user,kodeVerif;
    ApiInterface apiInterface;
    PinView getCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validasi_register);
        LoginToConfirm();
        setDesc();
        showDataEmail();
        sendEmailAgain();

    }

    public void setDesc(){
        String emaile = getIntent().getStringExtra("EmailUser");
        desc = (TextView) findViewById(R.id.deskripsi);
        String text = "Sebuah kode autentikasi telah dikirim ke alamat email kamu <b>"+emaile+"</b>";
        desc.setText(Html.fromHtml(text));
    }

    public void sendEmailAgain(){
        sendCodeAgain = (TextView) findViewById(R.id.text1);
        String text = "Belum menerima kode? <font color=#2f80ed><b>Kirim ulang</b></font>";
        sendCodeAgain.setText(Html.fromHtml(text));
        sendCodeAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendEmail();
            }
        });
    }

    public void LoginToConfirm(){
        btnContinue = (Button) findViewById(R.id.continueSelamat);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerifyEMail();
            }
        });
    }

    public void SendEmail(){
        String emaile = getIntent().getStringExtra("EmailUser");
        String email = emaile;
        String header = "VERIFIKASI EMAIL TO ACCES APP MILANIA CRAFT";
        String pesan = "Kode Verifikasi Kamu = "+kodeVerif;
        JavaMailAPI javaMailAPI = new JavaMailAPI(ValidasiRegister.this,email,header,pesan);
        javaMailAPI.execute();
    }

    public void showDataEmail(){
        id_user = getIntent().getStringExtra("Userid");
        System.out.println("ID USernya adalah = "+id_user);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseEmail> show = apiInterface.getVerifEmail(id_user);
        show.enqueue(new Callback<ResponseEmail>() {
            @Override
            public void onResponse(Call<ResponseEmail> call, Response<ResponseEmail> response) {
                kodeVerif = response.body().getData().getKodeVerifikasi();
                System.out.println(kodeVerif);
                SendEmail();
            }

            @Override
            public void onFailure(Call<ResponseEmail> call, Throwable t) {

            }
        });

    }

    public void VerifyEMail(){
        id_user = getIntent().getStringExtra("Userid");
        getCode = (PinView) findViewById(R.id.firstPinView);

        String inputKodeUSer = getCode.getText().toString();
        System.out.println("Input Usernya adalah "+inputKodeUSer);

        if (inputKodeUSer.equals(kodeVerif)){

            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<VerifEmail> verifEmailCall = apiInterface.setUpdateEmail(id_user);
            verifEmailCall.enqueue(new Callback<VerifEmail>() {
                @Override
                public void onResponse(Call<VerifEmail> call, Response<VerifEmail> response) {
                    int kode = response.body().getKode();
                    if (kode == 1){
                        Toast.makeText(ValidasiRegister.this, "Berhasil Verif", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), Login.class);
                        i.putExtra("UserId",id_user);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(ValidasiRegister.this, "Gagal Verif", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<VerifEmail> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(this, "Kode Salah", Toast.LENGTH_SHORT).show();
            getCode.setText("");
        }
    }
}