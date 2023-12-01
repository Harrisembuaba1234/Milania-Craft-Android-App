package com.example.milaniacraft.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.milaniacraft.API.ApiClient;
import com.example.milaniacraft.API.ApiInterface;
import com.example.milaniacraft.Adapter.AdapterBarang;
import com.example.milaniacraft.Adapter.AdapterFav;
import com.example.milaniacraft.Favorit.DataTampilFav;
import com.example.milaniacraft.Favorit.ResponseTampilFav;
import com.example.milaniacraft.ModelBarang.DataCari;
import com.example.milaniacraft.ModelBarang.ResponseCari;
import com.example.milaniacraft.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FavoritFragment extends Fragment {
    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private List<DataTampilFav> listData = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        retriveSeserahan();
    }

    public void retriveSeserahan(){
        String id = getActivity().getIntent().getStringExtra("id_user");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SHARED_PREF_ACCOUNT", Context.MODE_PRIVATE);
        id = sharedPreferences.getString("KEY_ID", "");
        ApiInterface meki = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseTampilFav> barangCall = meki.getFavorit(id);
        barangCall.enqueue(new Callback<ResponseTampilFav>() {
            @Override
            public void onResponse(Call<ResponseTampilFav> call, Response<ResponseTampilFav> response) {
                int kode = response.body().getKode();
                if(kode==1){

                    listData = response.body().getData();
                    adData = new AdapterFav(getContext(), listData,getActivity().getIntent());
                    rvData = getView().findViewById(R.id.rcFav);
                    rvData.setHasFixedSize(true);
                    rvData.setLayoutManager(new GridLayoutManager(getActivity(),2));
                    rvData.setAdapter(adData);
                    adData.notifyDataSetChanged();
                }else {
                    Toast.makeText(getActivity(), "Kategori Seserahan Kosong", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<ResponseTampilFav> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}