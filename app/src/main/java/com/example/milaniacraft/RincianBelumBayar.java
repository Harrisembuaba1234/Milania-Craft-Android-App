package com.example.milaniacraft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milaniacraft.API.ApiClient;
import com.example.milaniacraft.API.ApiInterface;
import com.example.milaniacraft.Activity.Keranjang;
import com.example.milaniacraft.Adapter.AdapterHistoryPesan;
import com.example.milaniacraft.Adapter.AdapterTransaksi;
import com.example.milaniacraft.ModelHistory.DataGetHistoryPesan;
import com.example.milaniacraft.ModelHistory.ResponseGetHistoryPesan;
import com.example.milaniacraft.ModelHistory.ResponseUpdateBelum;
import com.example.milaniacraft.ModelTransaksi.DataItemCart;
import com.example.milaniacraft.ModelTransaksi.ResponseDeleteTransaksi;
import com.example.milaniacraft.ModelTransaksi.ResponseTampilCart;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RincianBelumBayar extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterHistoryPesan adapterCartUser;
    List<DataGetHistoryPesan> itemCarts = new ArrayList<>();
    ApiInterface ipe_interface;
    String alamat,total,nopesan,waktu;
    TextView tvAlamat, tvTotal, tvTanggal, tvId;
    CardView batal;
    ImageView kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian_pesan);
        showDetailPesanan();

        alamat = getIntent().getStringExtra("alamat");
        total = getIntent().getStringExtra("grand_total");
        nopesan = getIntent().getStringExtra("transaksi_id");
        waktu = getIntent().getStringExtra("waktu_transaksi");

        tvAlamat = findViewById(R.id.getalamat);
        tvTotal = findViewById(R.id.getTotal);
        tvTanggal = findViewById(R.id.getTanggal);
        tvId = findViewById(R.id.getNotrans);
        batal = findViewById(R.id.batalkan);
        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteTrans();
            }
        });
        kembali = findViewById(R.id.arrowBack);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvAlamat.setText(alamat);
        tvTotal.setText(total);
        tvTanggal.setText(waktu);
        tvId.setText(nopesan);

    }

    public void DeleteTrans(){
        ipe_interface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseUpdateBelum> call = ipe_interface.UpdateBelum(getIntent().getStringExtra("transaksi_id"), getIntent().getStringExtra("id_UserBeli"));
        call.enqueue(new Callback<ResponseUpdateBelum>() {
            @Override
            public void onResponse(Call<ResponseUpdateBelum> call, Response<ResponseUpdateBelum> response) {
                int kode = response.body().getKode();
                Toast.makeText(RincianBelumBayar.this, "Kode : "+kode, Toast.LENGTH_SHORT).show();
               onBackPressed();

            }

            @Override
            public void onFailure(Call<ResponseUpdateBelum> call, Throwable t) {

            }
        });

    }

    public void showDetailPesanan(){
        ipe_interface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseGetHistoryPesan> call = ipe_interface.getHistoryPesan(getIntent().getStringExtra("transaksi_id"));
        call.enqueue(new Callback<ResponseGetHistoryPesan>() {
            @Override
            public void onResponse(Call<ResponseGetHistoryPesan> call, Response<ResponseGetHistoryPesan> response) {

                int kode = response.body().getKode();

                if(kode==1){

                    itemCarts = response.body().getData();
                    adapterCartUser = new AdapterHistoryPesan(RincianBelumBayar.this, itemCarts, getIntent());
                    recyclerView = findViewById(R.id.rcDataPesanan);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(RincianBelumBayar.this));
                    recyclerView.setAdapter(adapterCartUser);
                    adapterCartUser.notifyDataSetChanged();

                }else {

                    Toast.makeText(RincianBelumBayar.this, "Tidak ada transaksi", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ResponseGetHistoryPesan> call, Throwable t) {

                Toast.makeText(RincianBelumBayar.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }

        });

    }

}