package com.example.milaniacraft.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.example.milaniacraft.ModelHistory.DataGetHistoryPesan;
import com.example.milaniacraft.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class AdapterHistoryPesan extends RecyclerView.Adapter<AdapterHistoryPesan.HolderDataBaru> {

    Context ctx;
    List<DataGetHistoryPesan> listData1;
    Intent intent;
    public AdapterHistoryPesan(Context ctx, List<DataGetHistoryPesan> listData1, Intent intent) {
        this.ctx = ctx;
        this.listData1 = listData1;
        this.intent = intent;
    }

    @NonNull
    @Override
    public AdapterHistoryPesan.HolderDataBaru onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(ctx).inflate(R.layout.card_get_pesanan, parent, false);
        return new AdapterHistoryPesan.HolderDataBaru(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHistoryPesan.HolderDataBaru holder, @SuppressLint("RecyclerView") int position) {

        DataGetHistoryPesan db = listData1.get(position);

        holder.tvJenis.setText(String.valueOf(db.getBarangJenis()));
        holder.tvNama.setText(String.valueOf(db.getNamaBarang()));
        holder.tvJumlah.setText(String.valueOf(db.getJumlah()));
        holder.tvHarga.setText(String.valueOf(db.getSubTotal()));
        Picasso.get().load(ApiClient.IMAGES_URL+listData1.get(position).getImage()).resize(65,70).error(R.mipmap.ic_launcher).into(holder.tvIcon);

    }

    @Override
    public int getItemCount() {
        return listData1.size();
    }

    public class HolderDataBaru extends RecyclerView.ViewHolder {
        TextView  tvJenis,tvNama,tvHarga, tvJumlah;
        ImageView tvIcon;

        public HolderDataBaru(@NonNull View itemView) {
            super(itemView);

            tvJenis = itemView.findViewById(R.id.tbucket1);
            tvNama = itemView.findViewById(R.id.namabarang1);
            tvJumlah = itemView.findViewById(R.id.jmlhbeli);
            tvHarga = itemView.findViewById(R.id.txHarga);
            tvIcon = itemView.findViewById(R.id.tvimageView1);
        }
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
