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
import com.example.milaniacraft.DetailCari;
import com.example.milaniacraft.ModelBarang.DataCari;
import com.example.milaniacraft.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterCari extends RecyclerView.Adapter<AdapterCari.HolderDataCari> {
    Context ctx;
    List<DataCari> listCariData;
    Intent intent;

    public AdapterCari(Context ctx, List<DataCari> listCariData, Intent intent) {
        this.ctx = ctx;
        this.listCariData = listCariData;
        this.intent = intent;
    }

    public void setFilteredList(List<DataCari> filteredList) {
        this.listCariData = filteredList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public AdapterCari.HolderDataCari onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(ctx).inflate(R.layout.card_cari, parent, false);
        return new AdapterCari.HolderDataCari(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCari.HolderDataCari holder, @SuppressLint("RecyclerView") int position) {
        DataCari db = listCariData.get(position);

        holder.tvJenis.setText(String.valueOf(db.getBarangJenis()));
        holder.tvNama.setText(String.valueOf(db.getNamaBarang()));
        Picasso.get().load(ApiClient.IMAGES_URL+listCariData.get(position).getImage()).resize(65,70).error(R.mipmap.ic_launcher).into(holder.ivIcon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = ctx.getSharedPreferences("SHARED_PREF_ACCOUNT", Context.MODE_PRIVATE);
                String id_user = sharedPreferences.getString("KEY_ID","");
                String nama = sharedPreferences.getString("KEY_NAMA","");
                Intent mIntent =  new Intent(view.getContext(), DetailCari.class);
                mIntent.putExtra("id_barang", listCariData.get(position).getIdBarang());
                System.out.println("Tess id user = "+intent.getStringExtra("id_user"));
                mIntent.putExtra("id_user",id_user);
                mIntent.putExtra("namaUser", nama);
                mIntent.putExtra("image", listCariData.get(position).getImage());
                mIntent.putExtra("barang_jenis", listCariData.get(position).getBarangJenis());
                mIntent.putExtra("nama_barang", listCariData.get(position).getNamaBarang());
                mIntent.putExtra("harga", listCariData.get(position).getHarga());
                mIntent.putExtra("stok", listCariData.get(position).getStok());
                mIntent.putExtra("deskripsi", listCariData.get(position).getDeskripsi());
                view.getContext().startActivity(mIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listCariData.size();
    }

    public class HolderDataCari extends RecyclerView.ViewHolder {
        TextView tvId, tvJenis,tvNama,tvUkuran;
        ImageView ivIcon;

        public HolderDataCari(@NonNull View itemView) {
            super(itemView);

            tvJenis = itemView.findViewById(R.id.tbucket);
            tvNama = itemView.findViewById(R.id.namabarang);
            ivIcon = itemView.findViewById(R.id.tvimageView);
        }
    }
}
