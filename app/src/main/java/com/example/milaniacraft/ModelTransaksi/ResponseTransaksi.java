package com.example.milaniacraft.ModelTransaksi;

import com.google.gson.annotations.SerializedName;

public class ResponseTransaksi{

	@SerializedName("kode")
	private int kode;

	public int getKode(){
		return kode;
	}
}