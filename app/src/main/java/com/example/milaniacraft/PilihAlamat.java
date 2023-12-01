package com.example.milaniacraft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milaniacraft.API.ApiClient;
import com.example.milaniacraft.API.ApiInterface;
import com.example.milaniacraft.Adapter.AdapterAlamat;
import com.example.milaniacraft.Adapter.AdapterCartUser;
import com.example.milaniacraft.Adapter.AdapterTransaksi;
import com.example.milaniacraft.ModelTransaksi.DataTampilTransaksi;
import com.example.milaniacraft.ModelTransaksi.ResponseTampilTransaksi;
import com.example.milaniacraft.modelAlamat.DataAlamat;
import com.example.milaniacraft.modelAlamat.ResponseAlamat;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PilihAlamat extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterAlamat adapterAlamat;
    List<DataAlamat> itemAlamat = new ArrayList<>();
    ApiInterface ipe_interface;
    Button btnpilih;
    ImageView kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_alamat);
        showAlamat();

        kembali = findViewById(R.id.kembalitoDetailPesan);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public  void dataFromAlamat(){

        btnpilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PilihAlamat.this, Transaksi.class);
                i.putExtra("judul_alamat", getIntent().getStringExtra("judul_alamat"));
                i.putExtra("nama", getIntent().getStringExtra("nama"));
                i.putExtra("telp", getIntent().getStringExtra("telp"));
                i.putExtra("alamat_lengkap", getIntent().getStringExtra("alamat_lengkap"));
                i.putExtra("kecamatan", getIntent().getStringExtra("kecamatan"));
                i.putExtra("kabupaten", getIntent().getStringExtra("kabupaten"));
                i.putExtra("provinsi", getIntent().getStringExtra("provinsi"));
                i.putExtra("kode_pos", getIntent().getStringExtra("kode_pos"));
            }
        });
    }

    public void showAlamat(){
        recyclerView = (RecyclerView) findViewById(R.id.rcAlamat);

        AdapterAlamat.PassAlamat passAlamat = new AdapterAlamat.PassAlamat() {
            @Override
            public void alamaat(String judulAlamat, String nama, String telp, String almtLngkap, String kcmtn, String kbptn, String prov, String kode) {
                String jdl = judulAlamat;
                String nm = nama;
                String tp = telp;
                String angp = almtLngkap;
                String kc = kcmtn;
                String kb = kbptn;
                String pro = prov;
                String kd = kode;

                btnpilih =  findViewById(R.id.btnPilih);
                btnpilih.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(PilihAlamat.this, Transaksi.class);
                        i.putExtra("judul_alamat", jdl);
                        i.putExtra("nama_penerima", nm);
                        i.putExtra("no_telepon", tp);
                        i.putExtra("alamat_lengkap", angp);
                        i.putExtra("kecamatan", kc);
                        i.putExtra("kabupaten", kb);
                        i.putExtra("provinsi", pro);
                        i.putExtra("kode_pos", kd);
                        i.putExtra("namaUser", getIntent().getStringExtra("namaUser"));
                        i.putExtra("id_user", getIntent().getStringExtra("id_user"));
                        startActivity(i);
                    }
                });

            }
        };
        ipe_interface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseAlamat> call = ipe_interface.get_Alamat(getIntent().getStringExtra("id_user"));
        call.enqueue(new Callback<ResponseAlamat>() {
            @Override
            public void onResponse(Call<ResponseAlamat> call, Response<ResponseAlamat> response) {
                int kode = response.body().getKode();
                if(kode==1){
                    itemAlamat = response.body().getData();
                    adapterAlamat = new AdapterAlamat(PilihAlamat.this, itemAlamat,passAlamat);
                    recyclerView = findViewById(R.id.rcAlamat);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(PilihAlamat.this));
                    recyclerView.setAdapter(adapterAlamat);
                    adapterAlamat.notifyDataSetChanged();
                }else {
                    Toast.makeText(PilihAlamat.this, "Alamat anda Kosong", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseAlamat> call, Throwable t) {
                Toast.makeText(PilihAlamat.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}