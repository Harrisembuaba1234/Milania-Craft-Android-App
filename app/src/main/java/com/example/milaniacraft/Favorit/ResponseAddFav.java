package com.example.milaniacraft.Favorit;

import com.google.gson.annotations.SerializedName;

public class ResponseAddFav{

	@SerializedName("kode")
	private int kode;

	public void setKode(int kode){
		this.kode = kode;
	}

	public int getKode(){
		return kode;
	}
}