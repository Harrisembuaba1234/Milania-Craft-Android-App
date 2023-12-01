package com.example.milaniacraft.ModelTransaksi;

import com.google.gson.annotations.SerializedName;

public class DataTampilTransaksi {

	@SerializedName("image")
	private String image;

	@SerializedName("transaksi_id")
	private String transaksiId;

	@SerializedName("jumlah")
	private String jumlah;

	@SerializedName("harga")
	private String harga;

	@SerializedName("id_BarangDetail")
	private String idBarangDetail;

	@SerializedName("sub_total")
	private String subTotal;

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("barang_jenis")
	private String barangJenis;

	@SerializedName("id_UserDetail")
	private String idUserDetail;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setTransaksiId(String transaksiId){
		this.transaksiId = transaksiId;
	}

	public String getTransaksiId(){
		return transaksiId;
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

	public void setBarangJenis(String barangJenis){
		this.barangJenis = barangJenis;
	}

	public String getBarangJenis(){
		return barangJenis;
	}

	public void setIdUserDetail(String idUserDetail){
		this.idUserDetail = idUserDetail;
	}

	public String getIdUserDetail(){
		return idUserDetail;
	}
}