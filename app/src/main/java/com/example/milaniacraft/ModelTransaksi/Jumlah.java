package com.example.milaniacraft.ModelTransaksi;

import com.google.gson.annotations.SerializedName;

public class Jumlah{

	@SerializedName("transaksi_id")
	private String transaksiId;

	@SerializedName("waktu_transaksi")
	private String waktuTransaksi;

	@SerializedName("grand_total")
	private String grandTotal;

	public void setTransaksiId(String transaksiId){
		this.transaksiId = transaksiId;
	}

	public String getTransaksiId(){
		return transaksiId;
	}

	public void setWaktuTransaksi(String waktuTransaksi){
		this.waktuTransaksi = waktuTransaksi;
	}

	public String getWaktuTransaksi(){
		return waktuTransaksi;
	}

	public void setGrandTotal(String grandTotal){
		this.grandTotal = grandTotal;
	}

	public String getGrandTotal(){
		return grandTotal;
	}
}