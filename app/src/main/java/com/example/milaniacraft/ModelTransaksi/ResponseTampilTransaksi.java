package com.example.milaniacraft.ModelTransaksi;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseTampilTransaksi{

	@SerializedName("data")
	private List<DataTampilTransaksi> data;

	@SerializedName("kode")
	private int kode;

	@SerializedName("message")
	private String message;

	public void setData(List<DataTampilTransaksi> data){
		this.data = data;
	}

	public List<DataTampilTransaksi> getData(){
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