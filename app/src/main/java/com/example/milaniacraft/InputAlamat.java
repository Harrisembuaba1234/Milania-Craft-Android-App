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
import com.example.milaniacraft.Activity.DetailBarang;
import com.example.milaniacraft.Activity.Keranjang;
import com.example.milaniacraft.Activity.Register;
import com.example.milaniacraft.ModelLogin.ResponseRegister;
import com.example.milaniacraft.modelAlamat.ResponseInsertAlamat;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputAlamat extends AppCompatActivity {
    EditText judul, nama, prov, kota, kec, nohp, alamat, kodePos;
    String jdl, nm , pro, kt, kc, no, at, kd, idUser;
    Button btnSave;
    ImageView btnKembali;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_alamat);


        judul = findViewById(R.id.rpxnama);
        nama = findViewById(R.id.rpxalamat);
        prov = findViewById(R.id.rpxnohp);
        kota = findViewById(R.id.rpxusername);
        kec = findViewById(R.id.rpxemail);
        nohp = findViewById(R.id.rpxpassword);
        alamat = findViewById(R.id.rpxAlmtLngkp);
        kodePos = findViewById(R.id.rpxpKodePos);
        btnSave = findViewById(R.id.btnSimpan);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jdl = judul.getText().toString();
                nm = nama.getText().toString();
                pro = prov.getText().toString();
                kt = kota.getText().toString();
                kc = kec.getText().toString();
                no = nohp.getText().toString();
                at = alamat.getText().toString();
                kd = kodePos.getText().toString();
                idUser = getIntent().getStringExtra("id_user");
                TambahAlamat(jdl, pro, kt, kc, at, kd, nm, no, idUser);

            }
        });

        btnKembali = findViewById(R.id.kembalitoLihatAlamat);
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void TambahAlamat(String textJudul, String textProvinsi, String textKabupaten, String textKecamatan, String textAlamatLengkap, String textKodePos, String textNamaPenerima, String textNoTelepon, String textIdUserPembeli){
                if(jdl.equals("") || nm.equals("") || pro.equals("") || kt.equals("") || kc.equals("") || no.equals("") || at.equals("") || kd.equals("")){
                    Toast.makeText(InputAlamat.this, "Mohon Lengkapi Data terlebih dahulu", Toast.LENGTH_LONG).show();
                }else{
                    apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponseInsertAlamat> call = apiInterface.InsertAlamat(textJudul, textProvinsi, textKabupaten, textKecamatan, textAlamatLengkap, textKodePos, textNamaPenerima, textNoTelepon, textIdUserPembeli);
                    call.enqueue(new Callback<ResponseInsertAlamat>() {
                        @Override
                        public void onResponse(Call<ResponseInsertAlamat> call, Response<ResponseInsertAlamat> response) {
                            int kode = response.body().getKode();
                            Toast.makeText(InputAlamat.this, "Kode : "+kode, Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }

                        @Override
                        public void onFailure(Call<ResponseInsertAlamat> call, Throwable t) {

                        }
                    });
                }
    }
}