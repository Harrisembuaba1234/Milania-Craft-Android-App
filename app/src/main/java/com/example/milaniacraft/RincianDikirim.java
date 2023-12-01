package com.example.milaniacraft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milaniacraft.API.ApiClient;
import com.example.milaniacraft.API.ApiInterface;
import com.example.milaniacraft.Adapter.AdapterHistoryPesan;
import com.example.milaniacraft.ModelHistory.DataGetHistoryPesan;
import com.example.milaniacraft.ModelHistory.ResponseGetHistoryPesan;
import com.example.milaniacraft.ModelHistory.ResponseUpdateKirim;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RincianDikirim extends AppCompatActivity {
    String jasa,noResi,alamat,total,waktuTrans,waktuBayar,waktuKirim, idTrans;
    TextView tvAlamat, tvTotal, tvTanggal, tvId, tvJasa, tvTglBayar, tvTglKirim;
    ApiInterface ipe_interface;
    RecyclerView recyclerView;
    AdapterHistoryPesan adapterCartUser;
    List<DataGetHistoryPesan> itemCarts = new ArrayList<>();
    CardView chat,diterima;
    ImageView kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian_dikirim);
        showDetailPesanan();

        chat = findViewById(R.id.hubungi);
        diterima = findViewById(R.id.sudahditerima);
        diterima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateKirim();
            }
        });
        kembali = findViewById(R.id.arrowBack2);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvJasa = findViewById(R.id.resi2);
        tvAlamat = findViewById(R.id.getalamat3);
        tvTotal = findViewById(R.id.getTotal3);
        tvId = findViewById(R.id.getNotrans3);
        tvTanggal = findViewById(R.id.getTanggal3);
        tvTglBayar = findViewById(R.id.getTglPmbyaran2);
        tvTglKirim = findViewById(R.id.getTglPngiriman);

        jasa = getIntent().getStringExtra("jasa_kurir");
        noResi = getIntent().getStringExtra("no_resi");
        alamat = getIntent().getStringExtra("alamat");
        total = getIntent().getStringExtra("grand_total");
        waktuTrans = getIntent().getStringExtra("waktu_transaksi");
        waktuBayar = getIntent().getStringExtra("waktu_pembayaran");
        System.out.println("waktu_pembayaran" + waktuBayar);
        idTrans = getIntent().getStringExtra("transaksi_id");
        waktuKirim = getIntent().getStringExtra("waktu_pengiriman");

        tvJasa.setText(jasa + " - " + noResi );
        tvAlamat.setText(alamat);
        tvTotal.setText(total);
        tvId.setText(idTrans);
        tvTanggal.setText(waktuTrans);
        tvTglBayar.setText(waktuBayar);
        tvTglKirim.setText(waktuKirim);
    }

    public void updateKirim(){
        ipe_interface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseUpdateKirim> call = ipe_interface.UpdateKirim(getIntent().getStringExtra("transaksi_id"));
        call.enqueue(new Callback<ResponseUpdateKirim>() {
            @Override
            public void onResponse(Call<ResponseUpdateKirim> call, Response<ResponseUpdateKirim> response) {
                int kode = response.body().getKode();
                Toast.makeText(RincianDikirim.this, "Kode : "+kode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseUpdateKirim> call, Throwable t) {

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
                    adapterCartUser = new AdapterHistoryPesan(RincianDikirim.this, itemCarts, getIntent());
                    recyclerView = findViewById(R.id.rcDataPesananKirim);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(RincianDikirim.this));
                    recyclerView.setAdapter(adapterCartUser);
                    adapterCartUser.notifyDataSetChanged();

                }else {

                    Toast.makeText(RincianDikirim.this, "Tidak ada transaksi", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ResponseGetHistoryPesan> call, Throwable t) {

                Toast.makeText(RincianDikirim.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }

        });

    }
}