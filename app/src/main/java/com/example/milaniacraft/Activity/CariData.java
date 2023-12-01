package com.example.milaniacraft.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milaniacraft.API.ApiClient;
import com.example.milaniacraft.API.ApiInterface;
import com.example.milaniacraft.Adapter.AdapterCari;
import com.example.milaniacraft.ModelBarang.DataCari;
import com.example.milaniacraft.ModelBarang.ResponseCari;
import com.example.milaniacraft.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CariData extends AppCompatActivity {

    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private List<DataCari> listData = new ArrayList<>();
    private SearchView searchView;
    AdapterCari adapterCari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_data);
        retrieveData();

        rvData = findViewById(R.id.Tvrecycleview);
        lmData = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);

        searchView = findViewById(R.id.search2);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                FilterList(newText);
                return true;
            }
        });
    }

    public void FilterList(String text){

        List<DataCari> FilteredList = new ArrayList<>();
        for (DataCari brg : listData){
            if (brg.getBarangJenis().toLowerCase().contains(text.toLowerCase())){

                FilteredList.add(brg);
            }
        }

        if (FilteredList.isEmpty()){
            Toast.makeText(CariData.this, "No Data", Toast.LENGTH_SHORT).show();
        } else {

            adapterCari.setFilteredList(FilteredList);
        }
    }


    public void retrieveData() {
        ApiInterface ai = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseCari> tampilData = ai.ardretriveData();
        tampilData.enqueue(new Callback<ResponseCari>() {
            @Override
            public void onResponse(Call<ResponseCari> call, Response<ResponseCari> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(CariData.this, "Kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();

                listData = response.body().getData();

                adapterCari = new AdapterCari(CariData.this, listData, getIntent());
                rvData.setAdapter(adapterCari);
                adapterCari.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ResponseCari> call, Throwable t) {
                Toast.makeText(CariData.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}