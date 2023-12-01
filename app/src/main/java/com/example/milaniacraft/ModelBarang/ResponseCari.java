package com.example.milaniacraft.ModelBarang;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseCari{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("data")
	private List<DataCari> data;

	@SerializedName("kode")
	private int kode;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setData(List<DataCari> data){
		this.data = data;
	}

	public List<DataCari> getData(){
		return data;
	}

	public void setKode(int kode){
		this.kode = kode;
	}

	public int getKode(){
		return kode;
	}
}