package com.example.milaniacraft;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.milaniacraft.API.ApiClient;
import com.example.milaniacraft.API.ApiInterface;
import com.example.milaniacraft.modelAlamat.ResponseEditAlamat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAlamat extends AppCompatActivity {
    EditText judulAlamat, namaPenerima, Provinsi, Kabupaten, Kecamatan, noHp, alamatLengkap, kodPos;
    String getAlamat, getNama, getProv, getKab, getKec, getNo, getLengkap, getKodePos, getIdAlamat, getIdPembeli;
    Button btnSimpan;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alamat);

        judulAlamat = findViewById(R.id.rpxnama);
        namaPenerima = findViewById(R.id.rpxalamat);
        Provinsi = findViewById(R.id.rpxnohp);
        Kabupaten = findViewById(R.id.rpxusername);
        Kecamatan = findViewById(R.id.rpxemail);
        noHp = findViewById(R.id.rpxpassword);
        alamatLengkap = findViewById(R.id.rpxAlmtLngkp);
        kodPos = findViewById(R.id.rpxpKodePos);

        getAlamat = getIntent().getStringExtra("judul_alamat");
        getNama = getIntent().getStringExtra("nama_penerima");
        getProv = getIntent().getStringExtra("provinsi");
        getKab = getIntent().getStringExtra("kabupaten");
        getKec = getIntent().getStringExtra("kecamatan");
        getNo = getIntent().getStringExtra("no_telepon");
        getLengkap = getIntent().getStringExtra("alamat_lengkap");
        getKodePos = getIntent().getStringExtra("kode_pos");
        getIdAlamat = getIntent().getStringExtra("id_alamat");
        getIdPembeli = getIntent().getStringExtra("id_user");

        judulAlamat.setText(getAlamat);
        namaPenerima.setText(getNama);
        Provinsi.setText(getProv);
        Kabupaten.setText(getKab);
        Kecamatan.setText(getKec);
        noHp.setText(getNo);
        alamatLengkap.setText(getLengkap);
        kodPos.setText(getKodePos);

        btnSimpan = findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetAlamat();

            }
        });
    }

   public void SetAlamat(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
       Call<ResponseEditAlamat> call = apiInterface.SetAlamat(judulAlamat.getText().toString(), Provinsi.getText().toString(), Kabupaten.getText().toString(), Kecamatan.getText().toString(), alamatLengkap.getText().toString(), kodPos.getText().toString(), namaPenerima.getText().toString(), noHp.getText().toString(), getIntent().getStringExtra("id_alamat"));
       call.enqueue(new Callback<ResponseEditAlamat>() {
           @Override
           public void onResponse(Call<ResponseEditAlamat> call, Response<ResponseEditAlamat> response) {
               if (response.body().getKode() == 1){
                   Toast.makeText(EditAlamat.this, "DATA BERHASIL UPDATE", Toast.LENGTH_SHORT).show();
               } else {
                   System.out.println(response.body().getKode());
               }
           }

           @Override
           public void onFailure(Call<ResponseEditAlamat> call, Throwable t) {

           }
       });
   }
}