package com.example.milaniacraft;

import androidx.appcompat.app.AppCompatActivity;
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

public class RincianDikemas extends AppCompatActivity {
    String jasa,noResi,alamat,total,waktuTrans,waktuBayar, idTrans;
    TextView tvAlamat, tvTotal, tvTanggal, tvId, tvJasa, tvTglBayar;
    ApiInterface ipe_interface;
    RecyclerView recyclerView;
    AdapterHistoryPesan adapterCartUser;
    List<DataGetHistoryPesan> itemCarts = new ArrayList<>();
    ImageView kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian_dikemas);
        showDetailPesanan();

        tvJasa = findViewById(R.id.resi);
        tvAlamat = findViewById(R.id.getalamat2);
        tvTotal = findViewById(R.id.getTotal2);
        tvId = findViewById(R.id.getNotrans2);
        tvTanggal = findViewById(R.id.getTanggal2);
        tvTglBayar = findViewById(R.id.getTglPmbyaran);
        kembali = findViewById(R.id.arrowBack3);
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

        tvJasa.setText(jasa + " - " + noResi );
        tvAlamat.setText(alamat);
        tvTotal.setText(total);
        tvId.setText(idTrans);
        tvTanggal.setText(waktuTrans);
        tvTglBayar.setText(waktuBayar);


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
                    adapterCartUser = new AdapterHistoryPesan(RincianDikemas.this, itemCarts, getIntent());
                    recyclerView = findViewById(R.id.rcDataPesananKemas);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(RincianDikemas.this));
                    recyclerView.setAdapter(adapterCartUser);
                    adapterCartUser.notifyDataSetChanged();

                }else {

                    Toast.makeText(RincianDikemas.this, "Tidak ada transaksi", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ResponseGetHistoryPesan> call, Throwable t) {

                Toast.makeText(RincianDikemas.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }

        });

    }
}