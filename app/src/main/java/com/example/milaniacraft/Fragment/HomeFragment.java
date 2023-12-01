package com.example.milaniacraft.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milaniacraft.API.ApiClient;
import com.example.milaniacraft.API.ApiInterface;
import com.example.milaniacraft.Activity.CariData;
import com.example.milaniacraft.Activity.Keranjang;
import com.example.milaniacraft.Activity.Login;
import com.example.milaniacraft.Activity.Register;
import com.example.milaniacraft.Adapter.AdapterBarang;
import com.example.milaniacraft.Favorit.ResponseAddFav;
import com.example.milaniacraft.Favorit.ResponseDeletFav;
import com.example.milaniacraft.ModelBarang.DataCari;
import com.example.milaniacraft.ModelBarang.ResponseCari;
import com.example.milaniacraft.ModelTransaksi.ResponseDeleteKeranjang;
import com.example.milaniacraft.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements AdapterBarang.AdapterBarangClick {
    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private List<DataCari> listData = new ArrayList<>();
    AdapterBarang adapterBarang;
    TextView setGreteing;
    CardView cardView;
    Button bucket,hampers,seserahan;
    ImageView Tvkeranjang;
    ImageButton TvChat;
    String waktu, waktu2, waktu3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Tosearch();
        retriveBucket();
        LihatKeranjang();
        setDays();


        String username = getActivity().getIntent().getStringExtra("id_user");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SHARED_PREF_ACCOUNT", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("KEY_ID", "");

        setGreteing = (TextView) getView().findViewById(R.id.hi);
        String nama = getActivity().getIntent().getStringExtra("namaUser");
        nama = sharedPreferences.getString("KEY_NAMA", "");
        setGreteing.setText("Hi, "+nama);

        bucket = getView().findViewById(R.id.buket);
        bucket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retriveBucket();

            }
        });

        hampers = getView().findViewById(R.id.hampers);
        hampers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retriveHampers();

            }
        });

        seserahan = getView().findViewById(R.id.seserahan);
        seserahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retriveSeserahan();

            }
        });

    }

    public void setDays(){
        TvChat = getView().findViewById(R.id.chatWa);
        TvChat.setOnClickListener(View -> {
            //set waktu
            Calendar now = Calendar.getInstance();
            int hour = now.get(Calendar.HOUR_OF_DAY);
            System.out.println(hour);

            waktu = "https://wa.me/+6282244542429?text=Selamat Pagi Gan";
            waktu2 = "https://wa.me/+6282244542429?text=Selamat Siang Gan";
            waktu3 = "https://wa.me/+6282244542429?text=Selamat Malam Gan";
            if (hour <= 6 || hour <= 11) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(waktu));
                startActivity(intent);
            } else if (hour <= 17) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(waktu2));
                startActivity(intent);
            } else if (hour <= 24) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(waktu3));
                startActivity(intent);
            }

        });
    }

    public void LihatKeranjang(){
        Tvkeranjang = getView().findViewById(R.id.belanja);
        Tvkeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SHARED_PREF_ACCOUNT", Context.MODE_PRIVATE);
                String id_user = sharedPreferences.getString("KEY_ID","");
                String nama = sharedPreferences.getString("KEY_NAMA","");
                Intent i = new Intent(getContext(), Keranjang.class);
                i.putExtra("id_user", id_user);
                i.putExtra("namaUser", nama);
                startActivity(i);
            }
        });
    }

    public void retriveBucket(){
        ApiInterface meki = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseCari> barangCall = meki.ardretriveDataBucket();
        barangCall.enqueue(new Callback<ResponseCari>() {
            @Override
            public void onResponse(Call<ResponseCari> call, Response<ResponseCari> response) {
                int kode = response.body().getKode();
                if(kode==1){
                    listData = response.body().getData();
                    adData = new AdapterBarang(getContext(), listData, getActivity().getIntent(), HomeFragment.this);
                    rvData = getView().findViewById(R.id.rv_data);
                    rvData.setHasFixedSize(true);
                    rvData.setLayoutManager(new GridLayoutManager(getActivity(),2));
                    rvData.setAdapter(adData);
                    adData.notifyDataSetChanged();
                }else {
                    Toast.makeText(getActivity(), "Kategori Bucket Kosong", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ResponseCari> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void retriveHampers(){
        ApiInterface meki = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseCari> barangCall = meki.ardretriveDataHampers();
        barangCall.enqueue(new Callback<ResponseCari>() {
            @Override
            public void onResponse(Call<ResponseCari> call, Response<ResponseCari> response) {
                int kode = response.body().getKode();
                if (kode == 1) {

                    listData = response.body().getData();
                    adData = new AdapterBarang(getContext(), listData, getActivity().getIntent(), HomeFragment.this );
                    rvData = getView().findViewById(R.id.rv_data);
                    rvData.setHasFixedSize(true);
                    rvData.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    rvData.setAdapter(adData);
                    adData.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "Kategori Hampers Kosong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCari> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void retriveSeserahan(){
        ApiInterface meki = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseCari> barangCall = meki.ardretriveDataSeserahan();
        barangCall.enqueue(new Callback<ResponseCari>() {
            @Override
            public void onResponse(Call<ResponseCari> call, Response<ResponseCari> response) {
                int kode = response.body().getKode();
                if(kode==1){

                    listData = response.body().getData();
                    adData = new AdapterBarang(getContext(), listData,getActivity().getIntent(),HomeFragment.this);
                    rvData = getView().findViewById(R.id.rv_data);
                    rvData.setHasFixedSize(true);
                    rvData.setLayoutManager(new GridLayoutManager(getActivity(),2));
                    rvData.setAdapter(adData);
                    adData.notifyDataSetChanged();
                }else {
                    Toast.makeText(getActivity(), "Kategori Seserahan Kosong", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseCari> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Tosearch(){
        cardView = getView().findViewById(R.id.search);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), CariData.class);
                i.putExtra("id_user", getActivity().getIntent().getStringExtra("id_user"));
                i.putExtra("namaUser", getActivity().getIntent().getStringExtra("namaUser"));
                i.putExtra("image_user", getActivity().getIntent().getStringExtra("image_user"));
                i.putExtra("image_sampul", getActivity().getIntent().getStringExtra("image_sampul"));
                i.putExtra("telp", getActivity().getIntent().getStringExtra("telp"));
                i.putExtra("email", getActivity().getIntent().getStringExtra("email"));
                startActivity(i);
            }
        });
    }

    @Override
    public void onAddFavClick(int position) {
        String id_user = getActivity().getIntent().getStringExtra("id_user");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SHARED_PREF_ACCOUNT", Context.MODE_PRIVATE);
        id_user = sharedPreferences.getString("KEY_ID","");
        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseAddFav> delete = service.AddFavorit(String.valueOf(listData.get(position).getIdBarang()), id_user);
        delete.enqueue(new Callback<ResponseAddFav>() {
            @Override
            public void onResponse(Call<ResponseAddFav> call, Response<ResponseAddFav> response) {
                int kode = response.body().getKode();
                if (response.body().getKode() == 1) {
                    Toast.makeText(getContext(), "Barang ditambahkan ke Favorit", Toast.LENGTH_SHORT).show();
                }else if(kode == 3){
                    Toast.makeText(getContext(),"Barang sudah ada di list favorit",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Data Tidak Ada", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseAddFav> call, Throwable t) {
                Toast.makeText(getContext(), "Server Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onDeleteFavClick(int position) {



    }
}