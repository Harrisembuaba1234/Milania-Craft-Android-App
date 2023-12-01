package com.example.milaniacraft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.milaniacraft.API.ApiClient;
import com.example.milaniacraft.API.ApiInterface;
import com.example.milaniacraft.Activity.Keranjang;
import com.example.milaniacraft.Adapter.AdapterLihatAlamat;
import com.example.milaniacraft.ModelTransaksi.ResponseDeleteKeranjang;
import com.example.milaniacraft.modelAlamat.DataAlamat;
import com.example.milaniacraft.modelAlamat.ResponseAlamat;
import com.example.milaniacraft.modelAlamat.ResponseDeleteAlamat;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LihatAlamat extends AppCompatActivity implements AdapterLihatAlamat.AdapterCartClick {
    RecyclerView recyclerView;
    AdapterLihatAlamat adapterAlamat;
    List<DataAlamat> itemAlamat = new ArrayList<>();
    ApiInterface ipe_interface;
    Button btnDaftar;
    ImageView btnKembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_alamat);
        showAlamat();

        btnDaftar = findViewById(R.id.btnDaftrAlamt);
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_user = getIntent().getStringExtra("id_user");
                String namaUser = getIntent().getStringExtra("namaUser");
                Intent i = new Intent(getApplicationContext(), InputAlamat.class);
                i.putExtra("id_user", id_user);
                i.putExtra("namaUser", namaUser);
                startActivity(i);
            }
        });
        
        btnKembali = findViewById(R.id.Alamatkembali);
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void showAlamat(){
        ipe_interface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseAlamat> call = ipe_interface.get_Alamat(getIntent().getStringExtra("id_user"));
        call.enqueue(new Callback<ResponseAlamat>() {
            @Override
            public void onResponse(Call<ResponseAlamat> call, Response<ResponseAlamat> response) {
                int kode = response.body().getKode();
                if(kode==1){
                    itemAlamat = response.body().getData();
                    adapterAlamat = new AdapterLihatAlamat(LihatAlamat.this, itemAlamat, LihatAlamat.this);
                    recyclerView = findViewById(R.id.rcDaftarAlamat);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(LihatAlamat.this));
                    recyclerView.setAdapter(adapterAlamat);
                    adapterAlamat.notifyDataSetChanged();
                }else {
                    Toast.makeText(LihatAlamat.this, "Alamat anda Kosong", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseAlamat> call, Throwable t) {
                Toast.makeText(LihatAlamat.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDeleteClick(int position) {
        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseDeleteAlamat> delete = service.deleteAlamat(String.valueOf(itemAlamat.get(position).getIdAlamat()));
        delete.enqueue(new Callback<ResponseDeleteAlamat>() {
            @Override
            public void onResponse(Call<ResponseDeleteAlamat> call, Response<ResponseDeleteAlamat> response) {
                if (response.body().getKode() == 1) {

                    adapterAlamat.notifyItemRemoved(position);
                    itemAlamat.remove(position);

                } else {
                    Toast.makeText(LihatAlamat.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseDeleteAlamat> call, Throwable t) {
                Toast.makeText(LihatAlamat.this, "Server Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}
