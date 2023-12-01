package com.example.milaniacraft.Favorit;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseTampilFav{

	@SerializedName("data")
	private List<DataTampilFav> data;

	@SerializedName("kode")
	private int kode;

	@SerializedName("message")
	private String message;

	public void setData(List<DataTampilFav> data){
		this.data = data;
	}

	public List<DataTampilFav> getData(){
		return data;
	}

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