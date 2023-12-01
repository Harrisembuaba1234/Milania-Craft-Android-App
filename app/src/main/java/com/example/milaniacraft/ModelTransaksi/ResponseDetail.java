package com.example.milaniacraft.ModelTransaksi;

import com.google.gson.annotations.SerializedName;

public class ResponseDetail{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("jumlah")
	private Object jumlah;

	@SerializedName("kode")
	private int kode;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setJumlah(Object jumlah){
		this.jumlah = jumlah;
	}

	public Object getJumlah(){
		return jumlah;
	}

	public void setKode(int kode){
		this.kode = kode;
	}

	public int getKode(){
		return kode;
	}
}