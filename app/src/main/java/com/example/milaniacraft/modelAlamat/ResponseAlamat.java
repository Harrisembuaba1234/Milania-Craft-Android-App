package com.example.milaniacraft.modelAlamat;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseAlamat{

	@SerializedName("data")
	private List<DataAlamat> data;

	@SerializedName("kode")
	private int kode;

	@SerializedName("message")
	private String message;

	public void setData(List<DataAlamat> data){
		this.data = data;
	}

	public List<DataAlamat> getData(){
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