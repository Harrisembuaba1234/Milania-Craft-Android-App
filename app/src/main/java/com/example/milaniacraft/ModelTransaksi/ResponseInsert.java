package com.example.milaniacraft.ModelTransaksi;

import com.google.gson.annotations.SerializedName;

public class ResponseInsert{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("jumlah")
	private Jumlah jumlah;

	@SerializedName("kode")
	private int kode;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setJumlah(Jumlah jumlah){
		this.jumlah = jumlah;
	}

	public Jumlah getJumlah(){
		return jumlah;
	}

	public void setKode(int kode){
		this.kode = kode;
	}

	public int getKode(){
		return kode;
	}
}