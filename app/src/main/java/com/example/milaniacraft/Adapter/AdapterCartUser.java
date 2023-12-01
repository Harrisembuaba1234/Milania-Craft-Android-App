package com.example.milaniacraft.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milaniacraft.API.ApiClient;
import com.example.milaniacraft.ModelTransaksi.DataItemCart;
import com.example.milaniacraft.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class AdapterCartUser extends RecyclerView.Adapter<AdapterCartUser.ViewHolder> {
    Context context;
    List<DataItemCart> itemCarts;
    int totalPrice = 0;
    private final AdapterCartClick itemClick;



    public AdapterCartUser(Context context, List<DataItemCart> itemCarts, AdapterCartClick itemClick) {
        this.context = context;
        this.itemCarts = itemCarts;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_keranjang,parent,false);

        ViewHolder holder = new ViewHolder(view, itemClick);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataItemCart db = itemCarts.get(position);

        String hrg = db.getHarga();
        int cv = Integer.parseInt(hrg);
        String hasilConvert = toRupiah(cv);
        holder.tvHarga.setText(String.valueOf(hasilConvert));
        holder.tvVariasi.setText(String.valueOf(db.getNamaBarang()));
        holder.tvNama.setText(String.valueOf(db.getBarangJenis()));
        holder.tvJumlah.setText(String.valueOf(db.getJumlah()));
        holder.tvtotal.setText(String.valueOf(db.getSubTotal()));
        Picasso.get().load(ApiClient.IMAGES_URL + itemCarts.get(position).getImage()).error(R.mipmap.ic_launcher).into(holder.ivIcon);

    }

    @Override
    public int getItemCount() {
        return itemCarts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHarga, tvVariasi, tvNama, tvJumlah, tvtotal;
        ImageView ivIcon,ivDelete;
        public ViewHolder(@NonNull View itemView, AdapterCartClick adapterCartClick) {
            super(itemView);
            tvHarga = itemView.findViewById(R.id.hpxharga);
            tvVariasi = itemView.findViewById(R.id.textNama);
            tvNama = itemView.findViewById(R.id.hpxjudul);
            tvJumlah = itemView.findViewById(R.id.hpxjumlah);
            ivIcon = itemView.findViewById(R.id.hpximage);
            tvtotal = itemView.findViewById(R.id.hpxtotal);
            ivDelete = itemView.findViewById(R.id.friwxcheckbox);

            ivDelete.setOnClickListener(new View.OnClickListener() {
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
