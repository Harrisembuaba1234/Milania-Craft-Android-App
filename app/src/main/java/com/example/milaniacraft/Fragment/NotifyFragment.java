package com.example.milaniacraft.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ipsec.ike.ChildSaProposal;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.milaniacraft.API.ApiClient;
import com.example.milaniacraft.API.ApiInterface;
import com.example.milaniacraft.Activity.Keranjang;
import com.example.milaniacraft.Adapter.AdapterBarang;
import com.example.milaniacraft.Adapter.AdapterBatal;
import com.example.milaniacraft.Adapter.AdapterBelumBayar;
import com.example.milaniacraft.Adapter.AdapterKemas;
import com.example.milaniacraft.Adapter.AdapterKirim;
import com.example.milaniacraft.Adapter.AdapterSelesai;
import com.example.milaniacraft.ModelBarang.DataCari;
import com.example.milaniacraft.ModelBarang.ResponseCari;
import com.example.milaniacraft.ModelHistory.DataHistory;
import com.example.milaniacraft.ModelHistory.ResponseHistory;
import com.example.milaniacraft.R;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotifyFragment extends Fragment {
    Chip Belum, dikemas, dikirim, selesai, dibatalkan;
    private RecyclerView rvData, rvKirim, rvKemas, rvSelesai, rvDibatalkan;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private List<DataHistory> listData = new ArrayList<>();
    ConstraintLayout TransaksiKosong;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notify, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        retriveBelumBayar();
        Belum = getView().findViewById(R.id.chipBelum);
        dikemas = getView().findViewById(R.id.chipDikemas);
        dikirim = getView().findViewById(R.id.chipDikirim);
        selesai = getView().findViewById(R.id.chipSelesai);
        dibatalkan = getView().findViewById(R.id.chipDibatalkan);
        rvData = getView().findViewById(R.id.recycleHistory);
        TransaksiKosong = getView().findViewById(R.id.Transkosong);
        rvKemas = getView().findViewById(R.id.rcKemas);
        rvKirim = getView().findViewById(R.id.rcKirim);
        rvSelesai = getView().findViewById(R.id.rcSelesai);
        rvDibatalkan = getView().findViewById(R.id.rcDibatalkan);

        Belum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Belum.isChecked()) {
                    retriveBelumBayar();
                    rvData.setVisibility(View.VISIBLE);
                    rvKemas.setVisibility(View.GONE);
                    rvKirim.setVisibility(View.GONE);
                    rvSelesai.setVisibility(View.GONE);
                    rvDibatalkan.setVisibility(View.GONE);

                    Toast.makeText(getActivity(), "Ndang dibayar", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Yowes iku ae", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dikemas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dikemas.isChecked()) {
                    retriveDikemas();
                    rvData.setVisibility(View.GONE);
                    rvKemas.setVisibility(View.VISIBLE);
                    rvKirim.setVisibility(View.GONE);
                    rvSelesai.setVisibility(View.GONE);
                    rvDibatalkan.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "JAVA selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "JAVA deselected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dikirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dikirim.isChecked()) {
                    retriveDikirim();
                    rvData.setVisibility(View.GONE);
                    rvKemas.setVisibility(View.GONE);
                    rvKirim.setVisibility(View.VISIBLE);
                    rvSelesai.setVisibility(View.GONE);
                    rvDibatalkan.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "PHP selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "PHP deselected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selesai.isChecked()) {
                    retriveSelesai();
                    rvData.setVisibility(View.GONE);
                    rvKemas.setVisibility(View.GONE);
                    rvKirim.setVisibility(View.GONE);
                    rvSelesai.setVisibility(View.VISIBLE);
                    rvDibatalkan.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Python selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Python deselected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dibatalkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dibatalkan.isChecked()) {
                    retriveDibatalkan();
                    rvData.setVisibility(View.GONE);
                    rvKemas.setVisibility(View.GONE);
                    rvKirim.setVisibility(View.GONE);
                    rvSelesai.setVisibility(View.GONE);
                    rvDibatalkan.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "JS selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "JS deselected", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void CheckListKeranjang(){
        if (listData.isEmpty() && listData==null){
            TransaksiKosong.setVisibility(View.VISIBLE);
        }else{
            TransaksiKosong.setVisibility(View.GONE);
        }
    }

    public void retriveBelumBayar(){
        String id = getActivity().getIntent().getStringExtra("id_user");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SHARED_PREF_ACCOUNT", Context.MODE_PRIVATE);
        id= sharedPreferences.getString("KEY_ID", "");
        System.out.println("idnya " + id);
        ApiInterface BB = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseHistory> barangCall = BB.getHistoryBB(id);
        barangCall.enqueue(new Callback<ResponseHistory>() {
            @Override
            public void onResponse(Call<ResponseHistory> call, Response<ResponseHistory> response) {
                int kode = response.body().getKode();
                if(kode==1){
                    listData = response.body().getData();
                    adData = new AdapterBelumBayar(getContext(), listData,getActivity().getIntent());
                    rvData = getView().findViewById(R.id.recycleHistory);
                    lmData = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    rvData.setLayoutManager(lmData);
                    rvData.setAdapter(adData);
                    adData.notifyDataSetChanged();
                    TransaksiKosong.setVisibility(View.GONE);
                }else {
                    TransaksiKosong.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ResponseHistory> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void retriveDikemas(){
        String id = getActivity().getIntent().getStringExtra("id_user");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SHARED_PREF_ACCOUNT", Context.MODE_PRIVATE);
        id = sharedPreferences.getString("KEY_ID", "");
        System.out.println("idnya " + id);
        ApiInterface BB = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseHistory> barangCall = BB.getHistoryDkS(id);
        barangCall.enqueue(new Callback<ResponseHistory>() {
            @Override
            public void onResponse(Call<ResponseHistory> call, Response<ResponseHistory> response) {
                int kode = response.body().getKode();
                if(kode==1){
                    listData = response.body().getData();
                    adData = new AdapterKemas(getContext(), listData,getActivity().getIntent());
                    rvKemas = getView().findViewById(R.id.rcKemas);
                    lmData = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    rvKemas.setLayoutManager(lmData);
                    rvKemas.setAdapter(adData);
                    adData.notifyDataSetChanged();
                    TransaksiKosong.setVisibility(View.GONE);
                }else {
                    TransaksiKosong.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ResponseHistory> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void retriveDikirim(){
        String id = getActivity().getIntent().getStringExtra("id_user");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SHARED_PREF_ACCOUNT", Context.MODE_PRIVATE);
        id = sharedPreferences.getString("KEY_ID", "");
        System.out.println("idnya " + id);
        ApiInterface BB = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseHistory> barangCall = BB.getHistoryDk(id);
        barangCall.enqueue(new Callback<ResponseHistory>() {
            @Override
            public void onResponse(Call<ResponseHistory> call, Response<ResponseHistory> response) {
                int kode = response.body().getKode();
                if(kode==1){
                    listData = response.body().getData();
                    adData = new AdapterKirim(getContext(), listData,getActivity().getIntent());
                    rvKirim = getView().findViewById(R.id.rcKirim);
                    lmData = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    rvKirim.setLayoutManager(lmData);
                    rvKirim.setAdapter(adData);
                    adData.notifyDataSetChanged();
                    TransaksiKosong.setVisibility(View.GONE);
                }else {
                    TransaksiKosong.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ResponseHistory> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void retriveSelesai(){
        String id = getActivity().getIntent().getStringExtra("id_user");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SHARED_PREF_ACCOUNT", Context.MODE_PRIVATE);
        id = sharedPreferences.getString("KEY_ID", "");
        System.out.println("idnya " + id);
        ApiInterface BB = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseHistory> barangCall = BB.getHistorySl(id);
        barangCall.enqueue(new Callback<ResponseHistory>() {
            @Override
            public void onResponse(Call<ResponseHistory> call, Response<ResponseHistory> response) {
                int kode = response.body().getKode();
                if(kode==1){
                    listData = response.body().getData();
                    adData = new AdapterSelesai(getContext(), listData,getActivity().getIntent());
                    rvSelesai = getView().findViewById(R.id.rcSelesai);
                    lmData = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    rvSelesai.setLayoutManager(lmData);
                    rvSelesai.setAdapter(adData);
                    adData.notifyDataSetChanged();
                    TransaksiKosong.setVisibility(View.GONE);
                }else {
                    TransaksiKosong.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ResponseHistory> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void retriveDibatalkan(){
        String id = getActivity().getIntent().getStringExtra("id_user");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SHARED_PREF_ACCOUNT", Context.MODE_PRIVATE);
        id = sharedPreferences.getString("KEY_ID", "");
        System.out.println("idnya " + id);
        ApiInterface BB = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseHistory> barangCall = BB.getHistoryDb(id);
        barangCall.enqueue(new Callback<ResponseHistory>() {
            @Override
            public void onResponse(Call<ResponseHistory> call, Response<ResponseHistory> response) {
                int kode = response.body().getKode();
                if(kode==1){
                    listData = response.body().getData();
                    adData = new AdapterBatal(getContext(), listData,getActivity().getIntent());
                    rvDibatalkan = getView().findViewById(R.id.rcDibatalkan);
                    lmData = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    rvDibatalkan.setLayoutManager(lmData);
                    rvDibatalkan.setAdapter(adData);
                    adData.notifyDataSetChanged();
                    TransaksiKosong.setVisibility(View.GONE);
                }else {
                    TransaksiKosong.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ResponseHistory> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    }
