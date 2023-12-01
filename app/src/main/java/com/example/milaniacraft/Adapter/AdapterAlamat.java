package com.example.milaniacraft.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milaniacraft.API.ApiClient;
import com.example.milaniacraft.ModelTransaksi.DataTampilTransaksi;
import com.example.milaniacraft.R;
import com.example.milaniacraft.modelAlamat.DataAlamat;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterAlamat extends RecyclerView.Adapter<AdapterAlamat.HolderDataCari> {
    Context ctx;
    List<DataAlamat> listCariData;
//    Intent intent;
    AdapterAlamat.PassAlamat passAlamat;
    List<Integer> selectCheck = new ArrayList<>();


    public AdapterAlamat(Context ctx, List<DataAlamat> listCariData, PassAlamat passAlamat) {
        this.ctx = ctx;
        this.listCariData = listCariData;
        this.passAlamat = passAlamat;

        for (int i = 0; i<listCariData.size(); i++){
            selectCheck.add(0);
        }
    }

    public interface PassAlamat{
        void alamaat(String judulAlamat, String nama, String telp, String almtLngkap, String kcmtn, String kbptn, String prov, String kode );
    }

    @NonNull
    @Override
    public AdapterAlamat.HolderDataCari onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(ctx).inflate(R.layout.card_alamat, parent, false);
        return new AdapterAlamat.HolderDataCari(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAlamat.HolderDataCari holder, @SuppressLint("RecyclerView") int position) {
        DataAlamat db = listCariData.get(position);

        if (selectCheck.get(position) ==1){
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int k = 0; k<selectCheck.size(); k++){
                    if (k == position){
                        selectCheck.set(k,1);
                    } else {
                        selectCheck.set(k,0);
                    }
                }
                notifyDataSetChanged();
            }
        });
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true){
                    Toast.makeText(ctx, db.getJudulAlamat(), Toast.LENGTH_SHORT).show();
                    passAlamat.alamaat(db.getJudulAlamat(),db.getNamaPenerima(),db.getNoTelp(), db.getAlamatLengkap(), db.getKecamatan(), db.getKabupaten(), db.getProvinsi(), db.getKodePos());

                }
            }
        });

        holder.tvJudul.setText(String.valueOf(db.getJudulAlamat()));
        holder.tvNama.setText(String.valueOf(db.getNamaPenerima()));
        holder.tvtelp.setText(String.valueOf("- "+db.getNoTelp()) );
        holder.tvalamatFull.setText(String.valueOf(db.getAlamatLengkap()+","+ db.getKecamatan()+","+ db.getKabupaten()+"," + db.getProvinsi()+"," + db.getKodePos()));

    }

    @Override
    public int getItemCount() {
        return listCariData.size();
    }

    public class HolderDataCari extends RecyclerView.ViewHolder {
        TextView tvalamatFull, tvJudul,tvNama,tvtelp;
        CheckBox checkBox;


        public HolderDataCari(@NonNull View itemView) {
            super(itemView);

            tvJudul = itemView.findViewById(R.id.headerVoucher);
            tvNama = itemView.findViewById(R.id.status);
            tvtelp = itemView.findViewById(R.id.strip);
            tvalamatFull = itemView.findViewById(R.id.alamatFull);
            checkBox = itemView.findViewById(R.id.checkVoucher);
        }
    }

}
