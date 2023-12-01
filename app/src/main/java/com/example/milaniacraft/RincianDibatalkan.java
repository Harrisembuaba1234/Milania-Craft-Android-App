package com.example.milaniacraft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milaniacraft.API.ApiClient;
import com.example.milaniacraft.API.ApiInterface;
import com.example.milaniacraft.Adapter.AdapterHistoryPesan;
import com.example.milaniacraft.ModelHistory.DataGetHistoryPesan;
import com.example.milaniacraft.ModelHistory.ResponseGetHistoryPesan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RincianDibatalkan extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterHistoryPesan adapterCartUser;
    List<DataGetHistoryPesan> itemCarts = new ArrayList<>();
    ApiInterface ipe_interface;
    String alamat,total,nopesan,waktuPesan, waktuBatal;
    TextView tvAlamat, tvTotal, tvTanggal, tvId, tvBatal;
    CardView batal;
    ImageView kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian_dibatalkan);
        showDetailPesanan();

        alamat = getIntent().getStringExtra("alamat");
        total = getIntent().getStringExtra("grand_total");
        nopesan = getIntent().getStringExtra("transaksi_id");
        waktuPesan = getIntent().getStringExtra("waktu_transaksi");
        waktuBatal = getIntent().getStringExtra("waktu_dibatalkan");

        tvAlamat = findViewById(R.id.getalamat5);
        tvTotal = findViewById(R.id.getTotal5);
        tvTanggal = findViewById(R.id.getTanggal5);
        tvId = findViewById(R.id.getNotrans5);
        tvBatal = findViewById(R.id.getTglDibatalkan);

        tvAlamat.setText(alamat);
        tvTotal.setText(total);
        tvTanggal.setText(waktuPesan);
        tvId.setText(nopesan);
        tvBatal.setText(waktuBatal);
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
                    adapterCartUser = new AdapterHistoryPesan(RincianDibatalkan.this, itemCarts, getIntent());
                    recyclerView = findViewById(R.id.rcDataPesananBatal);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(RincianDibatalkan.this));
                    recyclerView.setAdapter(adapterCartUser);
                    adapterCartUser.notifyDataSetChanged();

                }else {

                    Toast.makeText(RincianDibatalkan.this, "Tidak ada transaksi", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ResponseGetHistoryPesan> call, Throwable t) {

                Toast.makeText(RincianDibatalkan.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }

        });

    }

}