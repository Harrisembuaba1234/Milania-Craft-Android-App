package com.example.milaniacraft.ModelHistory;

import com.google.gson.annotations.SerializedName;

public class ResponseUpdateKirim{

	@SerializedName("kode")
	private int kode;

	public void setKode(int kode){
		this.kode = kode;
	}

	public int getKode(){
		return kode;
	}
}