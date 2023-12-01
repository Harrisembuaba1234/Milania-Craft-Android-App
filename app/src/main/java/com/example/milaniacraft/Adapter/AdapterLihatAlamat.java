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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milaniacraft.API.ApiClient;
import com.example.milaniacraft.Activity.DetailBarang;
import com.example.milaniacraft.EditAlamat;
import com.example.milaniacraft.ModelTransaksi.DataItemCart;
import com.example.milaniacraft.R;
import com.example.milaniacraft.modelAlamat.DataAlamat;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;


public class AdapterLihatAlamat extends RecyclerView.Adapter<AdapterLihatAlamat.ViewHolder> {
    Context context;
    List<DataAlamat> itemCarts;
    int totalPrice = 0;
    private final AdapterCartClick itemClick;



    public AdapterLihatAlamat(Context context, List<DataAlamat> itemCarts, AdapterCartClick itemClick) {
        this.context = context;
        this.itemCarts = itemCarts;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_daftar_alamat,parent,false);

        ViewHolder holder = new ViewHolder(view, itemClick);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DataAlamat db = itemCarts.get(position);

        holder.tvJudul.setText(String.valueOf(db.getJudulAlamat()));
       holder.tvNama.setText(String.valueOf(db.getNamaPenerima()));
      holder.tvtelp.setText(String.valueOf("- "+db.getNoTelp()) );
       holder.tvalamatFull.setText(String.valueOf(db.getAlamatLengkap()+","+ db.getKecamatan()+","+ db.getKabupaten()+"," + db.getProvinsi()+"," + db.getKodePos()));
        holder.TvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent =  new Intent(view.getContext(), EditAlamat.class);
                mIntent.putExtra("id_alamat", itemCarts.get(position).getIdAlamat());
                mIntent.putExtra("id_user", itemCarts.get(position).getIdUserPembeli());
                mIntent.putExtra("judul_alamat", itemCarts.get(position).getJudulAlamat());
                mIntent.putExtra("provinsi", itemCarts.get(position).getProvinsi());
                mIntent.putExtra("kabupaten", itemCarts.get(position).getKabupaten());
                mIntent.putExtra("kecamatan", itemCarts.get(position).getKecamatan());
                mIntent.putExtra("alamat_lengkap", itemCarts.get(position).getAlamatLengkap());
                mIntent.putExtra("kode_pos", itemCarts.get(position).getKodePos());
                mIntent.putExtra("nama_penerima", itemCarts.get(position).getNamaPenerima());
                mIntent.putExtra("no_telepon", itemCarts.get(position).getNoTelp());
                view.getContext().startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemCarts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvalamatFull, tvJudul,tvNama,tvtelp;
        ImageView ivEdit;
        CardView TvEdit;
        public ViewHolder(@NonNull View itemView, AdapterCartClick adapterCartClick) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.headerVoucher1);
           tvNama = itemView.findViewById(R.id.status1);
           tvtelp = itemView.findViewById(R.id.strip1);
           tvalamatFull = itemView.findViewById(R.id.alamatFull1);
           ivEdit = itemView.findViewById(R.id.editHapus);
           TvEdit = itemView.findViewById(R.id.jaran);

            ivEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (adapterCartClick != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            adapterCartClick.onDeleteClick(pos);;
                        }
                    }
                }
            });
        }
    }

    public interface AdapterCartClick{
        public void onDeleteClick(int position);
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
//public class AdapterLihatAlamat extends RecyclerView.Adapter<AdapterLihatAlamat.ViewHolder> {
//    Context context;
//    List<DataAlamat> itemCarts;
//    int totalPrice = 0;
//    private final AdapterCartClick itemClick;
//
//
//
//    public AdapterLihatAlamat(Context ctx, List<DataAlamat> listCariData, AdapterCartClick itemClick1) {
//        this.ctx = ctx;
//        this.listCariData = listCariData;
//        this.itemClick = itemClick1;
//
//    }
//
//
//    @NonNull
//    @Override
//    public HolderDataCari onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(ctx).inflate(R.layout.card_daftar_alamat,parent,false);
//
//        AdapterLihatAlamat.HolderDataCari holder = new AdapterLihatAlamat.HolderDataCari(view, itemClick);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull AdapterLihatAlamat.HolderDataCari holder, @SuppressLint("RecyclerView") int position) {
//        DataAlamat db = listCariData.get(position);
//
//        holder.tvJudul.setText(String.valueOf(db.getJudulAlamat()));
//        holder.tvNama.setText(String.valueOf(db.getNamaPenerima()));
//        holder.tvtelp.setText(String.valueOf("- "+db.getNoTelp()) );
//        holder.tvalamatFull.setText(String.valueOf(db.getAlamatLengkap()+","+ db.getKecamatan()+","+ db.getKabupaten()+"," + db.getProvinsi()+"," + db.getKodePos()));
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return listCariData.size();
//    }
//
//    public class HolderDataCari extends RecyclerView.ViewHolder {
//        TextView tvalamatFull, tvJudul,tvNama,tvtelp;
//        ImageView ivEdit;
//
//        public HolderDataCari(@NonNull View itemView, AdapterCartClick adapterCartClick) {
//            super(itemView);
//
//            tvJudul = itemView.findViewById(R.id.headerVoucher1);
//            tvNama = itemView.findViewById(R.id.status1);
//            tvtelp = itemView.findViewById(R.id.strip1);
//            tvalamatFull = itemView.findViewById(R.id.alamatFull1);
//            ivEdit = itemView.findViewById(R.id.editHapus);
//
//            ivEdit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (adapterCartClick != null){
//                        int pos = getAdapterPosition();
//                        if (pos != RecyclerView.NO_POSITION) {
//                            adapterCartClick.onDeleteClick(pos);;
//                        }
//                    }
//                }
//            });
//        }
//    }
//
//    public interface AdapterCartClick{
//        public void onDeleteClick(int position);
//    }
//
//}
