package com.example.milaniacraft.ModelBarang;

import com.google.gson.annotations.SerializedName;

public class DataCari {

	@SerializedName("image")
	private String image;

	@SerializedName("harga")
	private String harga;

	@SerializedName("id_barang")
	private String idBarang;

	@SerializedName("barang_jenis")
	private String barangJenis;

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("stok")
	private String stok;

	@SerializedName("deskripsi")
	private String deskripsi;

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

	public void setNamaBarang(String namaBarang){
		this.namaBarang = namaBarang;
	}

	public String getNamaBarang(){
		return namaBarang;
	}

	public void setStok(String stok){
		this.stok = stok;
	}

	public String getStok(){
		return stok;
	}

	public void setDeskripsi(String deskripsi){
		this.deskripsi = deskripsi;
	}

	public String getDeskripsi(){
		return deskripsi;
	}


}