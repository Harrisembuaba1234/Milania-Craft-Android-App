package com.example.milaniacraft.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milaniacraft.API.ApiClient;
import com.example.milaniacraft.Activity.DetailBarang;
import com.example.milaniacraft.ModelBarang.DataCari;
import com.example.milaniacraft.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class AdapterBarang extends RecyclerView.Adapter<AdapterBarang.HolderDataBaru> {

    Context ctx;
    List<DataCari> listData1;
    Intent intent;
    private final AdapterBarangClick listclik;

    public AdapterBarang(Context ctx, List<DataCari> listData1, Intent intent, AdapterBarangClick listclik) {
        this.ctx = ctx;
        this.listData1 = listData1;
        this.intent = intent;
        this.listclik = listclik;
    }


    @NonNull
    @Override
    public AdapterBarang.HolderDataBaru onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(ctx).inflate(R.layout.card_barang, parent, false);
        HolderDataBaru holder = new HolderDataBaru(layout, listclik);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBarang.HolderDataBaru holder, @SuppressLint("RecyclerView") int position) {
        DataCari db = listData1.get(position);

        holder.tvJenis.setText(String.valueOf(db.getBarangJenis()));
        holder.tvNama.setText(String.valueOf(db.getNamaBarang()));
        String hrg = db.getHarga();
        int cv = Integer.parseInt(hrg);
        String hasilConvert = toRupiah(cv);
        holder.tvHarga.setText(String.valueOf(hasilConvert));
//        holder.tvStok.setText(String.valueOf(db.getStok()));
        Picasso.get().load(ApiClient.IMAGES_URL+listData1.get(position).getImage()).resize(150, 157).error(R.mipmap.ic_launcher).into(holder.ivIcon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = ctx.getSharedPreferences("SHARED_PREF_ACCOUNT", Context.MODE_PRIVATE);
                String id_user = sharedPreferences.getString("KEY_ID","");
                String nama = sharedPreferences.getString("KEY_NAMA","");
                Intent mIntent =  new Intent(view.getContext(), DetailBarang.class);
                mIntent.putExtra("id_barang", listData1.get(position).getIdBarang());
                System.out.println("Tess id user = "+intent.getStringExtra("id_user"));
                mIntent.putExtra("id_user",id_user);
                mIntent.putExtra("namaUser", nama);
                mIntent.putExtra("image", listData1.get(position).getImage());
                mIntent.putExtra("barang_jenis", listData1.get(position).getBarangJenis());
                mIntent.putExtra("nama_barang", listData1.get(position).getNamaBarang());
                mIntent.putExtra("harga", listData1.get(position).getHarga());
                mIntent.putExtra("stok", listData1.get(position).getStok());
                mIntent.putExtra("deskripsi", listData1.get(position).getDeskripsi());
                view.getContext().startActivity(mIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listData1.size();
    }

    public class HolderDataBaru extends RecyclerView.ViewHolder {
        TextView tvId, tvJenis,tvNama,tvHarga, tvStok;
        ImageView ivIcon, ivFav, ivDelFav;

        public HolderDataBaru(@NonNull View itemView, AdapterBarangClick adapterBarangClick) {
            super(itemView);

            tvJenis = itemView.findViewById(R.id.inpo);
            tvNama = itemView.findViewById(R.id.deskripsi);
            tvHarga = itemView.findViewById(R.id.harga);
            ivIcon = itemView.findViewById(R.id.fotobunga);
            ivFav = itemView.findViewById(R.id.btnFav);

            ivFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (adapterBarangClick != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            adapterBarangClick.onAddFavClick(pos);
                            ;
                        }
                    }
                }
            });
        }
    }

    public interface AdapterBarangClick{
        public void onAddFavClick(int position);
        public void onDeleteFavClick(int position);
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
