package com.example.milaniacraft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milaniacraft.API.ApiClient;
import com.example.milaniacraft.API.ApiInterface;
import com.example.milaniacraft.Activity.DetailBarang;
import com.example.milaniacraft.Activity.Keranjang;
import com.example.milaniacraft.ModelTransaksi.ResponseCart;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFav extends AppCompatActivity {

    ImageView FotoBarang,kurang,tambah, Back;
    TextView TextJenis,TextNama,TextStok,TextHarga,TextJumlah,TextTotal,TextDeskripsi;
    String idBarang,Jenis,Nama,Stok,Harga,Deskripsi,Foto, NamaBarang;
    Button button;
    int numberOrder = 0;
    int totalHarga = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);
        toBackOnHome();

        FotoBarang = findViewById(R.id.bucketbunga);
        TextJenis = findViewById(R.id.bucket);
        TextNama = findViewById(R.id.palsu);
        TextHarga = findViewById(R.id.hargabucket);
        TextDeskripsi = findViewById(R.id.panjang);
        TextStok = findViewById(R.id.jenisUkuran);
        //tambahan
        idBarang = getIntent().getStringExtra("id_barang");
        NamaBarang = getIntent().getStringExtra("namaUser");
        Jenis = getIntent().getStringExtra("barang_jenis");
        Nama = getIntent().getStringExtra("nama_barang");
        Stok = getIntent().getStringExtra("stok");
        Deskripsi = getIntent().getStringExtra("deskripsi");
        Harga = getIntent().getStringExtra("harga");
        Foto = getIntent().getStringExtra("image");

        TextJenis.setText(Jenis);
        // Hehe
        TextDeskripsi.setText(Deskripsi);
        TextNama.setText(Nama);
        TextStok.setText(Stok);
        int cv = Integer.parseInt(Harga);
        String hasilConvert = toRupiah(cv);
        TextHarga.setText(hasilConvert);
        Picasso.get().load(ApiClient.IMAGES_URL+Foto).resize(325, 340).
                error(R.mipmap.ic_launcher).into(FotoBarang);

        kurang = findViewById(R.id.btnmin);
        tambah = findViewById(R.id.btnplus);
        TextTotal = findViewById(R.id.settotal);
        TextJumlah = findViewById(R.id.jumlah);
        kurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOrder <= 0) numberOrder=0;
                else
                    numberOrder--;
                TextJumlah.setText(String.valueOf(numberOrder));
                int bis = Integer.parseInt(getIntent().getStringExtra("harga"));
                String hasilConvert = toRupiah(bis);
                TextHarga.setText(hasilConvert);
                totalHarga = bis * numberOrder;
                new CurrencyModel(String.valueOf(totalHarga), TextTotal);
            }
        });

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOrder >= Integer.parseInt(TextStok.getText().toString()));
                else
                    numberOrder++;
                TextJumlah.setText(String.valueOf(numberOrder));
                int bis = Integer.parseInt(getIntent().getStringExtra("harga"));
                String hasilConvert = toRupiah(bis);
                TextHarga.setText(hasilConvert);
                totalHarga = bis * numberOrder;
                new CurrencyModel(String.valueOf(totalHarga), TextTotal);

            }
        });

        button = findViewById(R.id.fkerxbtnbeli);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numberOrder == 0){
                    Toast.makeText(DetailFav.this, "harap tentukan jumlah dahulu", Toast.LENGTH_SHORT).show();

                }else{
                    String user = getIntent().getStringExtra("id_user");
                    System.out.println("Mau Insert id = "+user);
                    ApiInterface ipe_interface = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponseCart> call = ipe_interface.setCart(String.valueOf(numberOrder),String.valueOf(totalHarga),idBarang,user);
                    call.enqueue(new Callback<ResponseCart>() {
                        @Override
                        public void onResponse(Call<ResponseCart> call, Response<ResponseCart> response) {

                            int kode = response.body().getKode();

                            Toast.makeText(DetailFav.this, "Kode : "+kode, Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), Keranjang.class);
                            i.putExtra("id_user",user);
                            i.putExtra("namaUser", getIntent().getStringExtra("namaUser"));
                            i.putExtra("nama_barang", getIntent().getStringExtra("nama_barang"));
                            startActivity(i);
                        }
                        @Override
                        public void onFailure(Call<ResponseCart> call, Throwable t) {
                            System.out.println(t.getMessage());

                        }
                    });
                }

            }
        });

    }

    public  void toBackOnHome(){
        Back = findViewById(R.id.back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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
}