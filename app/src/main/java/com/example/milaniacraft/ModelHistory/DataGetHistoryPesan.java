package com.example.milaniacraft.ModelHistory;

import com.google.gson.annotations.SerializedName;

public class DataGetHistoryPesan {

	@SerializedName("image")
	private String image;

	@SerializedName("jumlah")
	private String jumlah;

	@SerializedName("harga")
	private String harga;

	@SerializedName("id_BarangDetail")
	private String idBarangDetail;

	@SerializedName("barang_jenis")
	private String barangJenis;

	@SerializedName("sub_total")
	private String subTotal;

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("id_TransaksiDetail")
	private String idTransaksiDetail;

	@SerializedName("id_UserBeli")
	private String idUserBeli;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setJumlah(String jumlah){
		this.jumlah = jumlah;
	}

	public String getJumlah(){
		return jumlah;
	}

	public void setHarga(String harga){
		this.harga = harga;
	}

	public String getHarga(){
		return harga;
	}

	public void setIdBarangDetail(String idBarangDetail){
		this.idBarangDetail = idBarangDetail;
	}

	public String getIdBarangDetail(){
		return idBarangDetail;
	}

	public void setBarangJenis(String barangJenis){
		this.barangJenis = barangJenis;
	}

	public String getBarangJenis(){
		return barangJenis;
	}

	public void setSubTotal(String subTotal){
		this.subTotal = subTotal;
	}

	public String getSubTotal(){
		return subTotal;
	}

	public void setNamaBarang(String namaBarang){
		this.namaBarang = namaBarang;
	}

	public String getNamaBarang(){
		return namaBarang;
	}

	public void setIdTransaksiDetail(String idTransaksiDetail){
		this.idTransaksiDetail = idTransaksiDetail;
	}

	public String getIdTransaksiDetail(){
		return idTransaksiDetail;
	}

	public void setIdUserBeli(String idUserBeli){
		this.idUserBeli = idUserBeli;
	}

	public String getIdUserBeli(){
		return idUserBeli;
	}
}