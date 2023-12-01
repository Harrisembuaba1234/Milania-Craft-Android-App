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
import com.example.milaniacraft.ModelTransaksi.DataItemCart;
import com.example.milaniacraft.ModelTransaksi.DataTampilTransaksi;
import com.example.milaniacraft.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class AdapterTransaksi extends RecyclerView.Adapter<AdapterTransaksi.HolderDataCari> {
    Context ctx;
    List<DataItemCart> listCariData;
    Intent intent;

    public AdapterTransaksi(Context ctx, List<DataItemCart> listCariData, Intent intent) {
        this.ctx = ctx;
        this.listCariData = listCariData;
        this.intent = intent;
    }

    @NonNull
    @Override
    public AdapterTransaksi.HolderDataCari onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(ctx).inflate(R.layout.card_transaksi, parent, false);
        return new AdapterTransaksi.HolderDataCari(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTransaksi.HolderDataCari holder, @SuppressLint("RecyclerView") int position) {
        DataItemCart db = listCariData.get(position);

        holder.tvJenis.setText(String.valueOf(db.getBarangJenis()));
        holder.tvNama.setText(String.valueOf(db.getNamaBarang()));
        holder.tvJumlah.setText("x "+db.getJumlah() + " pcs" );
        String hrg = db.getHarga();
        int cv = Integer.parseInt(hrg);
        String hasilConvert = toRupiah(cv);
        holder.tvHarga.setText(String.valueOf(hasilConvert));
        Picasso.get().load(ApiClient.IMAGES_URL+listCariData.get(position).getImage()).resize(65,70).error(R.mipmap.ic_launcher).into(holder.ivIcon);

    }

    @Override
    public int getItemCount() {
        return listCariData.size();
    }

    public class HolderDataCari extends RecyclerView.ViewHolder {
        TextView tvHarga, tvJenis,tvNama,tvJumlah;
        ImageView ivIcon;

        public HolderDataCari(@NonNull View itemView) {
            super(itemView);

            tvJenis = itemView.findViewById(R.id.tbucket1);
            tvNama = itemView.findViewById(R.id.namabarang1);
            tvJumlah = itemView.findViewById(R.id.jmlhbeli);
            tvHarga = itemView.findViewById(R.id.txHarga);
            ivIcon = itemView.findViewById(R.id.tvimageView1);
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
