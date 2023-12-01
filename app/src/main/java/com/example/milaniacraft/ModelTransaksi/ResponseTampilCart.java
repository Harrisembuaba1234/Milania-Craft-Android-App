package com.example.milaniacraft.ModelTransaksi;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseTampilCart{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("data")
	private List<DataItemCart> data;

	@SerializedName("kode")
	private int kode;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setData(List<DataItemCart> data){
		this.data = data;
	}

	public List<DataItemCart> getData(){
		return data;
	}

	public void setKode(int kode){
		this.kode = kode;
	}

	public int getKode(){
		return kode;
	}
}