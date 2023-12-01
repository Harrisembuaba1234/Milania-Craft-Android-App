package com.example.milaniacraft.ModelTransaksi;

import com.google.gson.annotations.SerializedName;

public class DataItemCart {

	@SerializedName("id_keranjang")
	private String idKeranjang;

	@SerializedName("image")
	private String image;

	@SerializedName("harga")
	private String harga;

	@SerializedName("jumlah")
	private String jumlah;

	@SerializedName("id_BarangKeranjang")
	private String idBarang;

	@SerializedName("barang_jenis")
	private String barangJenis;

	@SerializedName("sub_total")
	private String subTotal;

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("id_UserKeranjang")
	private String idUser;

	public void setIdKeranjang(String idKeranjang){
		this.idKeranjang = idKeranjang;
	}

	public String getIdKeranjang(){
		return idKeranjang;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setHarga(String harga){
		this.harga = harga;
	}

	public String getHarga(){
		return harga;
	}

	public void setJumlah(String jumlah){
		this.jumlah = jumlah;
	}

	public String getJumlah(){
		return jumlah;
	}

	public void setIdBarang(String idBarang){
		this.idBarang = idBarang;
	}

	public String getIdBarang(){
		return idBarang;
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

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}
}