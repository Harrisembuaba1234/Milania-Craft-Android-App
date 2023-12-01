package com.example.milaniacraft.API;

import com.example.milaniacraft.Favorit.ResponseAddFav;
import com.example.milaniacraft.Favorit.ResponseDeletFav;
import com.example.milaniacraft.Favorit.ResponseTampilFav;
import com.example.milaniacraft.ModelAkun.ResponseEditUser;
import com.example.milaniacraft.ModelAkun.ResponseHapusFoto;
import com.example.milaniacraft.ModelAkun.ResponseImg;
import com.example.milaniacraft.ModelAkun.ResponseRubahPW;
import com.example.milaniacraft.ModelAkun.ResponseUser;
import com.example.milaniacraft.ModelBarang.ResponseCari;
import com.example.milaniacraft.ModelHistory.ResponseGetHistoryPesan;
import com.example.milaniacraft.ModelHistory.ResponseHistory;
import com.example.milaniacraft.ModelHistory.ResponseUpdateBelum;
import com.example.milaniacraft.ModelHistory.ResponseUpdateKirim;
import com.example.milaniacraft.ModelLogin.CheckEmail;
import com.example.milaniacraft.ModelLogin.ResponseEmail;
import com.example.milaniacraft.ModelLogin.ResponseLogin;
import com.example.milaniacraft.ModelLogin.ResponseRegister;
import com.example.milaniacraft.ModelLogin.UpdatePassword;
import com.example.milaniacraft.ModelLogin.VerifEmail;
import com.example.milaniacraft.ModelTransaksi.ResponseCart;
import com.example.milaniacraft.ModelTransaksi.ResponseDeleteKeranjang;
import com.example.milaniacraft.ModelTransaksi.ResponseDeleteTransaksi;
import com.example.milaniacraft.ModelTransaksi.ResponseDetail;
import com.example.milaniacraft.ModelTransaksi.ResponseInsert;
import com.example.milaniacraft.ModelTransaksi.ResponseTampilCart;
import com.example.milaniacraft.ModelTransaksi.ResponseTampilTrans;
import com.example.milaniacraft.ModelTransaksi.ResponseTampilTransaksi;
import com.example.milaniacraft.modelAlamat.ResponseAlamat;
import com.example.milaniacraft.modelAlamat.ResponseDeleteAlamat;
import com.example.milaniacraft.modelAlamat.ResponseEditAlamat;
import com.example.milaniacraft.modelAlamat.ResponseInsertAlamat;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login1.php")
    Call<ResponseLogin> loginResponse(
            @Field("email") String email,
            @Field("pass") String pass
    );

    @FormUrlEncoded
    @POST("register1.php")
    Call<ResponseRegister> registerResponse(
            @Field("nama") String nama,
            @Field("email") String email,
            @Field("pass") String pass
    );

    @FormUrlEncoded
    @POST("DataVerif_Email.php")
    Call<ResponseEmail> getVerifEmail(
            @Field("id_user") String id_user
    );

    @FormUrlEncoded
    @POST("UpdateEmail.php")
    Call<VerifEmail> setUpdateEmail(
            @Field("id_user") String id_user
    );

    @FormUrlEncoded
    @POST("LupaPassword.php")
    Call<UpdatePassword> setNewPassword(
            @Field("email") String email,
            @Field("pass") String pass
    );

    @FormUrlEncoded
    @POST("CheckEmail.php")
    Call<CheckEmail> getCheckEmail(
            @Field("email") String email
    );

    @GET("retrieve.php")
    Call<ResponseCari> ardretriveData();

    @GET("bucket.php")
    Call<ResponseCari> ardretriveDataBucket();

    @GET("hampers.php")
    Call<ResponseCari> ardretriveDataHampers();

    @GET("seserahan.php")
    Call<ResponseCari> ardretriveDataSeserahan();

    @GET("tampilTransaksi.php")
    Call<ResponseTampilTransaksi> getTransaksi(
            @Query("id_UserDetail") String id_UserDetail
    );

    @FormUrlEncoded
    @POST("InsertKeranjang.php")
    Call<ResponseCart> setCart(
            @Field("jumlah") String jumlah,
            @Field("sub_total") String sub_total,
            @Field("id_BarangKeranjang") String id_BarangKeranjang,
            @Field("id_UserKeranjang") String id_UserKeranjang
    );

    @GET("keranjang.php")
    Call<ResponseTampilCart> get_Cart(
            @Query("id_UserKeranjang") String id_UserKeranjang
    );

    @GET("tampilAlamat.php")
    Call<ResponseAlamat> get_Alamat(
            @Query("id_userPembeli") String id_userPembeli
    );

    @DELETE("DeleteKeranjang.php")
    Call<ResponseDeleteKeranjang> deleteKeranjang(@Query("id_keranjang") String id_keranjang);

    @FormUrlEncoded
    @POST("TampilUser.php")
    Call<ResponseUser> getDataUser(
            @Field("id_user") String id_user
    );

    @Multipart
    @POST("ImgUplod.php")
    Call<ResponseImg> uploadImage (
            @Part MultipartBody.Part image,
            @Part("id_user")RequestBody idUser,
            @Part("nama")RequestBody nama,
            @Part("email")RequestBody email,
            @Part("telp")RequestBody no_telpon
    );

    @FormUrlEncoded
    @POST("UpdateProfil.php")
    Call<ResponseEditUser> getUpdateDataUser(
            @Field("id_user") String id_user,
            @Field("nama") String nama,
            @Field("email") String email,
            @Field("telp") String no_telpon
    );

    @FormUrlEncoded
    @POST("RubahPassword.php")
    Call<ResponseRubahPW> UbahPw(
            @Field("id_user") String id_user,
            @Field("pass") String password
    );

    @FormUrlEncoded
    @POST("InsertAlamat.php")
    Call<ResponseInsertAlamat> InsertAlamat(
            @Field("judul_alamat") String judul_alamat,
            @Field("provinsi") String provinsi,
            @Field("kabupaten") String kabupaten,
            @Field("kecamatan") String kecamatan,
            @Field("alamat_lengkap") String alamat_lengkap,
            @Field("kode_pos") String kode_pos,
            @Field("nama_penerima") String nama_penerima,
            @Field("no_telepon") String no_telepon,
            @Field("id_userPembeli") String id_userPembeli
    );

    @FormUrlEncoded
    @POST("autocode.php")
    Call<ResponseInsert> InsertTrans(
            @Field("grand_total") String grand_total,
            @Field("id_UserBeli") String id_UserBeli
//            @Field("sub_total") String sub_total,
//            @Field("jumlah") String jumlah,
//            @Field("id_BarangDetail") String id_BarangDetail
    );

    @FormUrlEncoded
    @POST("InsertDetailTransaksi.php")
    Call<ResponseDetail> InsertDetail(
            @Field("alamat") String alamat,
            @Field("transaksi_id") String transaksi_id,
            @Field("sub_total") String sub_total,
            @Field("jumlah") String jumlah,
            @Field("id_BarangDetail") String id_BarangDetail,
            @Field("id_UserKeranjang") String id_UserKeranjang

    );

    @GET("getDataTrans.php")
    Call<ResponseTampilTrans> GetTrans();

    @FormUrlEncoded
    @POST("BelumBayar.php")
    Call<ResponseHistory> getHistoryBB(
            @Field("id_UserBeli") String id_UserBeli
    );

    @FormUrlEncoded
    @POST("Dikemas.php")
    Call<ResponseHistory> getHistoryDkS(
            @Field("id_UserBeli") String id_UserBeli
    );

    @FormUrlEncoded
    @POST("Dikirim.php")
    Call<ResponseHistory> getHistoryDk(
            @Field("id_UserBeli") String id_UserBeli
    );

    @FormUrlEncoded
    @POST("Selesai.php")
    Call<ResponseHistory> getHistorySl(
            @Field("id_UserBeli") String id_UserBeli
    );

    @FormUrlEncoded
    @POST("Dibatalkan.php")
    Call<ResponseHistory> getHistoryDb(
            @Field("id_UserBeli") String id_UserBeli
    );

    @GET("getHistoryTrans.php")
    Call<ResponseGetHistoryPesan> getHistoryPesan(
            @Query("transaksi_id") String transaksi_id
    );

    @FormUrlEncoded
    @POST("UpdateKirim.php")
    Call<ResponseUpdateKirim> UpdateKirim(
            @Field("transaksi_id") String transaksi_id
    );

    @FormUrlEncoded
    @POST("DeleteTransaksi.php")
    Call<ResponseDeleteTransaksi> DeleteTrans(
            @Field("transaksi_id") String transaksi_id,
            @Field("id_UserBeli") String id_UserBeli
    );

    @FormUrlEncoded
    @POST("UpdateBelum.php")
    Call<ResponseUpdateBelum> UpdateBelum(
            @Field("transaksi_id") String transaksi_id,
            @Field("id_UserBeli") String id_UserBeli
    );

    @FormUrlEncoded
    @POST("HapusPoto.php")
    Call<ResponseHapusFoto> HapusFoto(
            @Field("id_user") String id_user
    );

    @DELETE("DeleteAlamat.php")
    Call<ResponseDeleteAlamat> deleteAlamat(@Query("id_alamat") String id_alamat);

    @FormUrlEncoded
    @POST("UpdateAlamat.php")
    Call<ResponseEditAlamat> SetAlamat(
            @Field("judul_alamat") String judul_alamat,
            @Field("provinsi") String provinsi,
            @Field("kabupaten") String kabupaten,
            @Field("kecamatan") String kecamatan,
            @Field("alamat_lengkap") String alamat_lengkap,
            @Field("kode_pos") String kode_pos,
            @Field("nama_penerima") String nama_penerima,
            @Field("no_telepon") String no_telepon,
            @Field("id_alamat") String id_alamat
    );

    @FormUrlEncoded
    @POST("AddFav.php")
    Call<ResponseAddFav> AddFavorit(
            @Field("id_BarangFav") String id_BarangFav,
            @Field("id_pembeli") String id_pembeli
    );

    @DELETE("DeleteFav.php")
    Call<ResponseDeletFav> DelFav(@Query("id_BarangFav") String id_BarangFav);

    @GET("TampilFav.php")
    Call<ResponseTampilFav> getFavorit(
            @Query("id_pembeli") String id_pembeli
    );

}
