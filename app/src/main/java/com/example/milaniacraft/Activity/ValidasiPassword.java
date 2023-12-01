package com.example.milaniacraft.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.milaniacraft.JavaMailAPI;
import com.example.milaniacraft.R;

import java.util.Random;

public class ValidasiPassword extends AppCompatActivity {
    PinView kode;
    Button Continue;
    String KodeVerify;
    TextView sendAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validasi_password);
        getKodeEmail();
        submit();
        sendKodeAgain();
    }

    public void sendKodeAgain(){
        sendAgain = (TextView) findViewById(R.id.text1);
        sendAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailLupa = getIntent().getStringExtra("EmailLupa");
                JavaMailAPI javaMailAPI = new JavaMailAPI(ValidasiPassword.this,emailLupa,"LUPA PASSWORD","Kode = "+KodeVerify);
                javaMailAPI.execute();
            }
        });

    }

    public void submit(){
        Continue = (Button) findViewById(R.id.continueSelamat);
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kode = (PinView) findViewById(R.id.firstPinView);
                String mathPw = kode.getText().toString();
                System.out.println("mathPw == "+mathPw);
                System.out.println("GENERATE =="+KodeVerify);
                if (mathPw.equals(KodeVerify)){
                    Toast.makeText(ValidasiPassword.this, "BENAR ", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(),GantiPassword.class);
                    i.putExtra("EmailLupa",getIntent().getStringExtra("EmailLupa"));
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(ValidasiPassword.this, "Salah", Toast.LENGTH_SHORT).show();
                    kode.setText("");
                }
            }
        });
    }

    public void getKodeEmail(){
        String emailLupa = getIntent().getStringExtra("EmailLupa");
        Random r = new Random( System.currentTimeMillis() );
        int x =  10000 + r.nextInt(20000);
        KodeVerify = String.valueOf(x);
        JavaMailAPI javaMailAPI = new JavaMailAPI(ValidasiPassword.this,emailLupa,"LUPA PASSWORD","Kode = "+KodeVerify);
        javaMailAPI.execute();

    }
}