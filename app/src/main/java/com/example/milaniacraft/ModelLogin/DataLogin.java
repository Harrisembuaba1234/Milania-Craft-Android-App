package com.example.milaniacraft.ModelLogin;

import com.google.gson.annotations.SerializedName;

public class DataLogin {

	@SerializedName("nama")
	private String nama;

	@SerializedName("pass")
	private String pass;

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("email")
	private String email;

	@SerializedName("status")
	private String status;

	@SerializedName("telp")
	private String telp;

	@SerializedName("image_user")
	private String imageUser;

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setPass(String pass){
		this.pass = pass;
	}

	public String getPass(){
		return pass;
	}

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setTelp(String telp){
		this.telp = telp;
	}

	public String getTelp(){
		return telp;
	}

	public void setImageUser(String imageUser){
		this.imageUser = imageUser;
	}

	public String getImageUser(){
		return imageUser;
	}

}