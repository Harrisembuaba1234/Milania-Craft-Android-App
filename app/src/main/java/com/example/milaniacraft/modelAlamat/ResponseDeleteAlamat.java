package com.example.milaniacraft.modelAlamat;

import com.google.gson.annotations.SerializedName;

public class ResponseDeleteAlamat{

	@SerializedName("kode")
	private int kode;

	public void setKode(int kode){
		this.kode = kode;
	}

	public int getKode(){
		return kode;
	}
}