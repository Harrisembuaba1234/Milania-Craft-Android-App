package com.example.milaniacraft.ModelLogin;

import com.google.gson.annotations.SerializedName;

public class DataRegister {

	@SerializedName("id_user")
	private int idUser;

	public void setIdUser(int idUser){
		this.idUser = idUser;
	}

	public int getIdUser(){
		return idUser;
	}
}