package com.example.milaniacraft.modelAlamat;

import com.google.gson.annotations.SerializedName;

public class DataAlamat {

	@SerializedName("provinsi")
	private String provinsi;

	@SerializedName("id_alamat")
	private String idAlamat;

	@SerializedName("judul_alamat")
	private String judulAlamat;

	@SerializedName("kecamatan")
	private String kecamatan;

	@SerializedName("kode_pos")
	private String kodePos;

	@SerializedName("id_userPembeli")
	private String idUserPembeli;

	@SerializedName("kabupaten")
	private String kabupaten;

	@SerializedName("alamat_lengkap")
	private String alamatLengkap;

	@SerializedName("nama_penerima")
	private String namaPenerima;

	@SerializedName("no_telepon")
	private String noTelp;

	public void setProvinsi(String provinsi){
		this.provinsi = provinsi;
	}

	public String getProvinsi(){
		return provinsi;
	}

	public void setIdAlamat(String idAlamat){
		this.idAlamat = idAlamat;
	}

	public String getIdAlamat(){
		return idAlamat;
	}

	public void setJudulAlamat(String judulAlamat){
		this.judulAlamat = judulAlamat;
	}

	public String getJudulAlamat(){
		return judulAlamat;
	}

	public void setKecamatan(String kecamatan){
		this.kecamatan = kecamatan;
	}

	public String getKecamatan(){
		return kecamatan;
	}

	public void setKodePos(String kodePos){
		this.kodePos = kodePos;
	}

	public String getKodePos(){
		return kodePos;
	}

	public void setIdUserPembeli(String idUserPembeli){
		this.idUserPembeli = idUserPembeli;
	}

	public String getIdUserPembeli(){
		return idUserPembeli;
	}

	public void setKabupaten(String kabupaten){
		this.kabupaten = kabupaten;
	}

	public String getKabupaten(){
		return kabupaten;
	}

	public void setAlamatLengkap(String alamatLengkap){
		this.alamatLengkap = alamatLengkap;
	}

	public String getAlamatLengkap(){
		return alamatLengkap;
	}

	public void setNamaPenerima(String namaPenerima){
		this.namaPenerima = namaPenerima;
	}

	public String getNamaPenerima(){
		return namaPenerima;
	}

	public void setNoTelp(String noTelp){
		this.noTelp = noTelp;
	}

	public String getNoTelp(){
		return noTelp;
	}
}