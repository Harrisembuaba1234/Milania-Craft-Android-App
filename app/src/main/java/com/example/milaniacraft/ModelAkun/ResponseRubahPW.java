package com.example.milaniacraft.ModelAkun;

import com.google.gson.annotations.SerializedName;

public class ResponseRubahPW{

	@SerializedName("kode")
	private int kode;

	@SerializedName("message")
	private String message;

	public void setKode(int kode){
		this.kode = kode;
	}

	public int getKode(){
		return kode;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}