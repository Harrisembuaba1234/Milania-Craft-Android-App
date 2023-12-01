package com.example.milaniacraft.ModelLogin;

import com.google.gson.annotations.SerializedName;

public class ResponseRegister{

	@SerializedName("data")
	private DataRegister data;

	@SerializedName("kode")
	private int kode;

	public void setData(DataRegister data){
		this.data = data;
	}

	public DataRegister getData(){
		return data;
	}

	public void setKode(int kode){
		this.kode = kode;
	}

	public int getKode(){
		return kode;
	}
}