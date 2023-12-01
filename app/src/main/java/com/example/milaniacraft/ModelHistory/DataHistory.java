package com.example.milaniacraft.ModelHistory;

import com.google.gson.annotations.SerializedName;

public class DataHistory {

	@SerializedName("transaksi_id")
	private String transaksiId;

	@SerializedName("id_UserBeli")
	private String idUserBeli;

	@SerializedName("status")
	private String status;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("waktu_transaksi")
	private String waktuTransaksi;

	@SerializedName("grand_total")
	private String grandTotal;

	@SerializedName("jasa_kurir")
	private String jasaKurir;

	@SerializedName("no_resi")
	private String noResi;

	@SerializedName("waktu_pembayaran")
	private String waktuPembayaran;

	@SerializedName("waktu_pengiriman")
	private String waktuPengiriman;

	@SerializedName("waktu_pesanan_selesai")
	private String waktuPesananSelesai;

	@SerializedName("waktu_dibatalkan")
	private String waktuDibatalkan;

	public void setTransaksiId(String transaksiId){
		this.transaksiId = transaksiId;
	}

	public String getTransaksiId(){
		return transaksiId;
	}

	public void setIdUserBeli(String idUserBeli){
		this.idUserBeli = idUserBeli;
	}

	public String getIdUserBeli(){
		return idUserBeli;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
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

	public void setJasaKurir(String jasaKurir){
		this.jasaKurir = jasaKurir;
	}

	public String getJasaKurir(){
		return jasaKurir;
	}

	public void setNoResi(String noResi){
		this.noResi = noResi;
	}

	public String getNoResi(){
		return noResi;
	}

	public void setWaktuPembayaran(String waktuPembayaran){
		this.waktuPembayaran = waktuPembayaran;
	}

	public String getWaktuPembayaran(){
		return waktuPembayaran;
	}

	public void setWaktuPengiriman(String waktuPengiriman){
		this.waktuPengiriman = waktuPengiriman;
	}

	public String getWaktuPengiriman(){
		return waktuPengiriman;
	}

	public void setWaktuPesananSelesai(String waktuPesananSelesai){
		this.waktuPesananSelesai = waktuPesananSelesai;
	}

	public String getWaktuPesananSelesai(){
		return waktuPesananSelesai;
	}

	public void setWaktuDibatalkan(String waktuDibatalkan){
		this.waktuDibatalkan = waktuDibatalkan;
	}

	public String getWaktuDibatalkan(){
		return waktuDibatalkan;
	}
}