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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RincianSelesai extends AppCompatActivity {
    String jasa,noResi,alamat,total,waktuTrans,waktuBayar,waktuKirim,waktuSelesai, idTrans;
    TextView tvAlamat, tvTotal, tvTanggal, tvId, tvJasa, tvTglBayar, tvTglKirim, tvTglSelesai;
    ApiInterface ipe_interface;
    RecyclerView recyclerView;
    AdapterHistoryPesan adapterCartUser;
    List<DataGetHistoryPesan> itemCarts = new ArrayList<>();
    CardView chat;
    ImageView kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian_selesai);
        showDetailPesanan();

        tvJasa = findViewById(R.id.resi3);
        tvAlamat = findViewById(R.id.getalamat4);
        tvTotal = findViewById(R.id.getTotal4);
        tvId = findViewById(R.id.getNotrans4);
        tvTanggal = findViewById(R.id.getTanggal4);
        tvTglBayar = findViewById(R.id.getTglPmbyaran3);
        tvTglKirim = findViewById(R.id.getTglPngiriman2);
        tvTglSelesai = findViewById(R.id.getTglSelesai);
        kembali = findViewById(R.id.arrowBack4);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        jasa = getIntent().getStringExtra("jasa_kurir");
        noResi = getIntent().getStringExtra("no_resi");
        alamat = getIntent().getStringExtra("alamat");
        total = getIntent().getStringExtra("grand_total");
        waktuTrans = getIntent().getStringExtra("waktu_transaksi");
        waktuBayar = getIntent().getStringExtra("waktu_pembayaran");
        System.out.println("waktu_pembayaran" + waktuBayar);
        idTrans = getIntent().getStringExtra("transaksi_id");
        waktuKirim = getIntent().getStringExtra("waktu_pengiriman");
        waktuSelesai = getIntent().getStringExtra("waktu_pesanan_selesai");

        tvJasa.setText(jasa + " - " + noResi );
        tvAlamat.setText(alamat);
        tvTotal.setText(total);
        tvId.setText(idTrans);
        tvTanggal.setText(waktuTrans);
        tvTglBayar.setText(waktuBayar);
        tvTglKirim.setText(waktuKirim);
        tvTglSelesai.setText(waktuSelesai);
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
                    adapterCartUser = new AdapterHistoryPesan(RincianSelesai.this, itemCarts, getIntent());
                    recyclerView = findViewById(R.id.rcDataPesananSelesai);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(RincianSelesai.this));
                    recyclerView.setAdapter(adapterCartUser);
                    adapterCartUser.notifyDataSetChanged();

                }else {

                    Toast.makeText(RincianSelesai.this, "Tidak ada transaksi", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ResponseGetHistoryPesan> call, Throwable t) {

                Toast.makeText(RincianSelesai.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }

        });

    }
}