package com.example.milaniacraft.Favorit;

import com.google.gson.annotations.SerializedName;

public class DataTampilFav {

	@SerializedName("image")
	private String image;

	@SerializedName("id_BarangFav")
	private String idBarangFav;

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

	@SerializedName("id_fav")
	private String idFav;

	@SerializedName("id_pembeli")
	private String idPembeli;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setIdBarangFav(String idBarangFav){
		this.idBarangFav = idBarangFav;
	}

	public String getIdBarangFav(){
		return idBarangFav;
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

	public void setIdFav(String idFav){
		this.idFav = idFav;
	}

	public String getIdFav(){
		return idFav;
	}

	public void setIdPembeli(String idPembeli){
		this.idPembeli = idPembeli;
	}

	public String getIdPembeli(){
		return idPembeli;
	}
}