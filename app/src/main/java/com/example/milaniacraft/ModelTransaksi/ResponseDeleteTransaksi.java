package com.example.milaniacraft.ModelTransaksi;

import com.google.gson.annotations.SerializedName;

public class ResponseDeleteTransaksi{

	@SerializedName("kode")
	private int kode;

	public void setKode(int kode){
		this.kode = kode;
	}

	public int getKode(){
		return kode;
	}
}