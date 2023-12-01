package com.example.milaniacraft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milaniacraft.API.ApiClient;
import com.example.milaniacraft.API.ApiInterface;
import com.example.milaniacraft.Activity.BottomNavigation;
import com.example.milaniacraft.Activity.Keranjang;
import com.example.milaniacraft.Activity.Register;
import com.example.milaniacraft.Adapter.AdapterCartUser;
import com.example.milaniacraft.Adapter.AdapterTransaksi;
import com.example.milaniacraft.ModelAkun.ResponseUser;
import com.example.milaniacraft.ModelTransaksi.DataItemCart;
import com.example.milaniacraft.ModelTransaksi.DataTampilTransaksi;
import com.example.milaniacraft.ModelTransaksi.ResponseDeleteTransaksi;
import com.example.milaniacraft.ModelTransaksi.ResponseDetail;
import com.example.milaniacraft.ModelTransaksi.ResponseTampilCart;
import com.example.milaniacraft.ModelTransaksi.ResponseTampilTrans;
import com.example.milaniacraft.ModelTransaksi.ResponseTampilTransaksi;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Transaksi extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterTransaksi adapterCartUser;
    List<DataItemCart> itemCarts = new ArrayList<>();
    ApiInterface ipe_interface;
    TextView lihat, getAlamat, getTanggal, getTotal, getId;
    ImageView TvHome;
    String jumlah, subTotal, idBarang, ivAlamat;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
        showTransaksi();
        dataFromAlamat();
        notif(Transaksi.this);
        toHome();
        ShowTrans();

        lihat = findViewById(R.id.lihatAlamat);
        lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Transaksi.this, PilihAlamat.class);
                i.putExtra("namaUser", getIntent().getStringExtra("namaUser"));
                i.putExtra("id_user", getIntent().getStringExtra("id_user"));
                startActivity(i);
            }
        });

        btn = findViewById(R.id.fkerxbtnkonfirm);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetDetail();
            }
        });
    }

    public void toHome(){
        TvHome = findViewById(R.id.proviousdetail);
        TvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Transaksi.this, Keranjang.class);
                i.putExtra("namaUser", getIntent().getStringExtra("namaUser"));
                i.putExtra("id_user", getIntent().getStringExtra("id_user"));
                startActivity(i);
                DeleteTrans();
            }
        });
    }

    public void DeleteTrans(){
        ipe_interface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseDeleteTransaksi> call = ipe_interface.DeleteTrans(getId.getText().toString(), getIntent().getStringExtra("id_user"));
        call.enqueue(new Callback<ResponseDeleteTransaksi>() {
            @Override
            public void onResponse(Call<ResponseDeleteTransaksi> call, Response<ResponseDeleteTransaksi> response) {
                int kode = response.body().getKode();
                Toast.makeText(Transaksi.this, "Kode : "+kode, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ResponseDeleteTransaksi> call, Throwable t) {

            }
        });

    }

    public void dataFromAlamat(){
        getAlamat = findViewById(R.id.getalmt);
        String jalamat = getIntent().getStringExtra("judul_alamat");
        String nalamat = getIntent().getStringExtra("nama_penerima");
        String talamat = getIntent().getStringExtra("no_telepon");
        String Lalamat = getIntent().getStringExtra("alamat_lengkap");
        String Kalamat = getIntent().getStringExtra("kecamatan");
        String Balamat = getIntent().getStringExtra("kabupaten");
        String Palamat = getIntent().getStringExtra("provinsi");
        String kode = getIntent().getStringExtra("kode_pos");

        if(jalamat == null || nalamat == null || talamat == null || Lalamat == null || Kalamat == null || Balamat == null || Palamat == null || kode == null){
            getAlamat.setText("Pilih Alamat");
        }else{
            getAlamat.setText(jalamat + "\n"+ nalamat + " - " + talamat + "\n"+ Lalamat + "," + Kalamat + "," + Balamat + "," + Palamat + "," + kode );
            ivAlamat = getAlamat.getText().toString();
            System.out.println(ivAlamat);
        }
    }

    public void showTransaksi(){
        ipe_interface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseTampilCart> call = ipe_interface.get_Cart(getIntent().getStringExtra("id_user"));
        call.enqueue(new Callback<ResponseTampilCart>() {
            @Override
            public void onResponse(Call<ResponseTampilCart> call, Response<ResponseTampilCart> response) {
                int kode = response.body().getKode();
                if(kode==1){
                    itemCarts = response.body().getData();
                    adapterCartUser = new AdapterTransaksi(Transaksi.this, itemCarts, getIntent());
                    recyclerView = findViewById(R.id.rcTransaksi);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Transaksi.this));
                    recyclerView.setAdapter(adapterCartUser);
                    adapterCartUser.notifyDataSetChanged();
                }else {
                    Toast.makeText(Transaksi.this, "Tidak ada transaksi", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseTampilCart> call, Throwable t) {
                Toast.makeText(Transaksi.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ShowTrans(){

            getId = (TextView) findViewById(R.id.idnya);
            getTanggal = (TextView) findViewById(R.id.tgl);
            getTotal = (TextView) findViewById(R.id.grand);
            ipe_interface = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponseTampilTrans> userCall = ipe_interface.GetTrans();
            userCall.enqueue(new Callback<ResponseTampilTrans>() {
                @Override
                public void onResponse(Call<ResponseTampilTrans> call, Response<ResponseTampilTrans> response) {
                    String m = response.body().getData().getWaktuTransaksi();
                    String e = response.body().getData().getGrandTotal();
                    String f = response.body().getData().getTransaksiId();
                    getTanggal.setText(m);
                    getTotal.setText(e);
                    getId.setText(f);
                }

                @Override
                public void onFailure(Call<ResponseTampilTrans> call, Throwable t) {

                }
            });

        }


    public void SetDetail() {
        String tvAlamat = getAlamat.getText().toString();
        System.out.println(tvAlamat);

        if(tvAlamat.equals("Pilih Alamat")){
            Toast.makeText(Transaksi.this, "Mohon Lengkapi Alamat Terlebih dahulu", Toast.LENGTH_LONG).show();
        }else if(tvAlamat.equals(ivAlamat)){

            for (int i = 0; i < itemCarts.size(); i++) {
                jumlah = itemCarts.get(i).getJumlah();
                subTotal = itemCarts.get(i).getSubTotal();
                idBarang = itemCarts.get(i).getIdBarang();

                ipe_interface = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponseDetail> call = ipe_interface.InsertDetail(getAlamat.getText().toString(), getId.getText().toString(), subTotal, jumlah, idBarang, getIntent().getStringExtra("id_user"));
                call.enqueue(new Callback<ResponseDetail>() {
                    @Override
                    public void onResponse(Call<ResponseDetail> call, Response<ResponseDetail> response) {
                        int kode = response.body().getKode();
                        Toast.makeText(Transaksi.this, "Kode : "+kode, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), BottomNavigation.class);
                        i.putExtra("namaUser", getIntent().getStringExtra("namaUser"));
                        i.putExtra("id_user", getIntent().getStringExtra("id_user"));
                        startActivity(i);
                    }

                    @Override
                    public void onFailure(Call<ResponseDetail> call, Throwable t) {

                    }
                });
            }
        }

    }

    public void notif(Activity activity){
        //change color notif bar
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.muda));
        //set icons notifbar
        View decor = activity.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
}