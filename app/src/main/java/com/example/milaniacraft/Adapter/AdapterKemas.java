package com.example.milaniacraft.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milaniacraft.Activity.DetailBarang;
import com.example.milaniacraft.ModelHistory.DataHistory;
import com.example.milaniacraft.R;
import com.example.milaniacraft.RincianDikemas;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class AdapterKemas extends RecyclerView.Adapter<AdapterKemas.HolderDataBaru> {

    Context ctx;
    List<DataHistory> list;
    Intent intent;
    public AdapterKemas(Context ctx, List<DataHistory> list, Intent intent) {
        this.ctx = ctx;
        this.list = list;
        this.intent = intent;
    }

    @NonNull
    @Override
    public AdapterKemas.HolderDataBaru onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(ctx).inflate(R.layout.card_kemas, parent, false);
        return new AdapterKemas.HolderDataBaru(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterKemas.HolderDataBaru holder, @SuppressLint("RecyclerView") int position) {

        DataHistory db = list.get(position);

        holder.status.setText(String.valueOf("Pesanan " + db.getStatus()));
        holder.tanggal.setText(String.valueOf(db.getWaktuTransaksi()));
        holder.NoTrans.setText(String.valueOf(db.getTransaksiId()));
        holder.TvHistory.setText(String.valueOf(" Sedang dalam proses pengemasan"));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent =  new Intent(view.getContext(), RincianDikemas.class);
                mIntent.putExtra("id_UserBeli", list.get(position).getIdUserBeli());
                mIntent.putExtra("namaUser", intent.getStringExtra("namaUser"));
                mIntent.putExtra("alamat", list.get(position).getAlamat());
                mIntent.putExtra("jasa_kurir", list.get(position).getJasaKurir());
                mIntent.putExtra("no_resi", list.get(position).getNoResi());
                mIntent.putExtra("status", list.get(position).getStatus());
                mIntent.putExtra("grand_total", list.get(position).getGrandTotal());
                mIntent.putExtra("transaksi_id", list.get(position).getTransaksiId());
                mIntent.putExtra("waktu_transaksi", list.get(position).getWaktuTransaksi());
                mIntent.putExtra("waktu_pembayaran", list.get(position).getWaktuPembayaran());
                mIntent.putExtra("waktu_pengiriman", list.get(position).getWaktuPengiriman());
                mIntent.putExtra("waktu_pesanan_selesai", list.get(position).getWaktuPesananSelesai());
                view.getContext().startActivity(mIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HolderDataBaru extends RecyclerView.ViewHolder {
        TextView status, tanggal, NoTrans, TvHistory;

        public HolderDataBaru(@NonNull View itemView) {
            super(itemView);

            status = itemView.findViewById(R.id.dikirim1);
            tanggal = itemView.findViewById(R.id.tanggalTrans1);
            NoTrans = itemView.findViewById(R.id.id1);
            TvHistory = itemView.findViewById(R.id.history1);
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
