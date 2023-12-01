package com.example.milaniacraft.ModelHistory;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseGetHistoryPesan{

	@SerializedName("data")
	private List<DataGetHistoryPesan> data;

	@SerializedName("kode")
	private int kode;

	@SerializedName("message")
	private String message;

	public void setData(List<DataGetHistoryPesan> data){
		this.data = data;
	}

	public List<DataGetHistoryPesan> getData(){
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