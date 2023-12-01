package com.example.milaniacraft.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milaniacraft.API.ApiClient;
import com.example.milaniacraft.API.ApiInterface;
import com.example.milaniacraft.Adapter.AdapterCartUser;
import com.example.milaniacraft.CurrencyModel;
import com.example.milaniacraft.InputAlamat;
import com.example.milaniacraft.ModelTransaksi.DataItemCart;
import com.example.milaniacraft.ModelTransaksi.ResponseDeleteKeranjang;
import com.example.milaniacraft.ModelTransaksi.ResponseInsert;
import com.example.milaniacraft.ModelTransaksi.ResponseTampilCart;
import com.example.milaniacraft.R;
import com.example.milaniacraft.Transaksi;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Keranjang extends AppCompatActivity implements AdapterCartUser.AdapterCartClick {
    RecyclerView recyclerView;
    AdapterCartUser adapterCartUser;
    List<DataItemCart> itemCarts = new ArrayList<>();
    ApiInterface ipe_interface;
    TextView totalHarga;
    ImageView Tvkembali;
    Button button;
    String total, idUser, jumlah, subTotal, idBarang;
    ConstraintLayout KeranjangKosong;
//    private ActivityKeranjangBinding bind;

    private  int totalprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);
        showChart();
        toHome();

        totalHarga = findViewById(R.id.dataxhargabelitotal);


        KeranjangKosong = findViewById(R.id.cartkosong);

        button = findViewById(R.id.fkerxbtnbeli1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREF_ACCOUNT", Context.MODE_PRIVATE);
                String id_user = sharedPreferences.getString("KEY_ID","");
                String nama = sharedPreferences.getString("KEY_NAMA","");
                Intent i = new Intent(Keranjang.this, Transaksi.class);
                i.putExtra("namaUser", nama);
                i.putExtra("id_user", id_user);
                SetTrans();
                startActivity(i);
            }
        });
    }

    public void SetTrans(){
        String id_user = getIntent().getStringExtra("id_user");
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREF_ACCOUNT", Context.MODE_PRIVATE);
        id_user = sharedPreferences.getString("KEY_ID","");
            ipe_interface = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponseInsert> call = ipe_interface.InsertTrans(totalHarga.getText().toString(), id_user);
            call.enqueue(new Callback<ResponseInsert>() {
                @Override
                public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                    int kode = response.body().getKode();
                    Toast.makeText(Keranjang.this, "Kode : "+kode, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<ResponseInsert> call, Throwable t) {

                }
            });
        }


//    }
    private void CheckListKeranjang(){
        if (itemCarts.isEmpty() && itemCarts==null){
            KeranjangKosong.setVisibility(View.VISIBLE);
        }else{
            KeranjangKosong.setVisibility(View.GONE);
        }
    }

    public void toHome(){
        Tvkembali = findViewById(R.id.kembali);
        Tvkembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREF_ACCOUNT", Context.MODE_PRIVATE);
                String id_user = sharedPreferences.getString("KEY_ID","");
                String nama = sharedPreferences.getString("KEY_NAMA","");
                Intent i = new Intent(Keranjang.this, BottomNavigation.class);
                i.putExtra("namaUser", id_user);
                i.putExtra("id_user", nama);
                startActivity(i);
            }
        });
    }

    public void showChart(){
        String id_user = getIntent().getStringExtra("id_user");
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREF_ACCOUNT", Context.MODE_PRIVATE);
        id_user = sharedPreferences.getString("KEY_ID","");
        ipe_interface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseTampilCart> call = ipe_interface.get_Cart(id_user);
        call.enqueue(new Callback<ResponseTampilCart>() {
            @Override
            public void onResponse(Call<ResponseTampilCart> call, Response<ResponseTampilCart> response) {
                int kode = response.body().getKode();
                if(kode==1){
                    itemCarts = response.body().getData();
                    adapterCartUser = new AdapterCartUser(Keranjang.this,itemCarts,Keranjang.this);
                    recyclerView = findViewById(R.id.fkerxrecyclekeranjang);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Keranjang.this));
                    recyclerView.setAdapter(adapterCartUser);
                    adapterCartUser.notifyDataSetChanged();
                    KeranjangKosong.setVisibility(View.GONE);
                    Totalharga();
                }else {
                    KeranjangKosong.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ResponseTampilCart> call, Throwable t) {
                Toast.makeText(Keranjang.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static String toRupiah(int rupiah){
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator('.');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia.format(rupiah);
    }

    @Override
    public void onDeleteClick(int position) {
        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseDeleteKeranjang> delete = service.deleteKeranjang(String.valueOf(itemCarts.get(position).getIdKeranjang()));
        delete.enqueue(new Callback<ResponseDeleteKeranjang>() {
            @Override
            public void onResponse(Call<ResponseDeleteKeranjang> call, Response<ResponseDeleteKeranjang> response) {
                if (response.body().getKode() == 1) {

                    Totalharga();
                    adapterCartUser.notifyItemRemoved(position);
                    itemCarts.remove(position);

                } else {
                    Toast.makeText(Keranjang.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
                }

                CheckListKeranjang();
            }

            @Override
            public void onFailure(Call<ResponseDeleteKeranjang> call, Throwable t) {
                Toast.makeText(Keranjang.this, "Server Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void Totalharga(){
        totalprice = 0;
        for (int i = 0; i < itemCarts.size(); i++) {
            totalprice = totalprice + Integer.parseInt(itemCarts.get(i).getSubTotal());
            totalHarga = findViewById(R.id.dataxhargabelitotal);
            new CurrencyModel(String.valueOf(totalprice), totalHarga);

        }
    }
}