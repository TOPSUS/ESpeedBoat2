package id.alin.espeedboat.MyRetrofit.Services;

import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ProfileData.ServerResponseProfileData;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AuthServices {

    /*
    * INI DIGUNAKAN UNTUK LOGIN KE DALAM SISTEM
    *
    * RETURN NYA BERUPA ServerResponseProfileData
    *
    * NANTI RESPONSE ITU DIRUBAH KEBENTUK OBJEK YANG BISA DIOLAH APLIKASI
    * YAITU PROFILE DATA DI PACKAGE VIEWMODEL.MAINACTIVITYVIEWMODEL,PROFILEDATA
    * */
    @FormUrlEncoded
    @POST("/api/login")
    Call<ServerResponseProfileData> login(
            @Field("email") String email,
            @Field("password") String password,
            @Field("fcm_token") String fcm_token
    );

    /*
    * REGISTER DENGAN INPUT GAMBAR
    *
    * RETURNNYA BERUPA SERVERRESPONSEPROFILE DATA
    *
    * DISINI SSERVERRESPONSEPROFILE TIDAK DIRUBAH KE PROFILE DATA KARENA BELUM ADA PROSES LOGIN
    * */
    @Multipart
    @POST("/api/register")
    Call<ServerResponseProfileData> registerImage(
            @Part("nama") RequestBody nama,
            @Part("alamat") RequestBody alamat,
            @Part("jeniskelamin") RequestBody jeniskelamin,
            @Part("nohp") RequestBody nohp,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("c_password") RequestBody c_password,
            @Part MultipartBody.Part profileimage
    );

    /*
     * REGISTER DENGAN INPUT TANPA GAMBAR
     *
     * RETURNNYA BERUPA SERVERRESPONSEPROFILE DATA
     *
     * DISINI SSERVERRESPONSEPROFILE TIDAK DIRUBAH KE PROFILE DATA KARENA BELUM ADA PROSES LOGIN
     * */
    @FormUrlEncoded
    @POST("/api/register")
    Call<ServerResponseProfileData> registerNoImage(
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("jeniskelamin") String jeniskelamin,
            @Field("nohp") String nohp,
            @Field("email") String email,
            @Field("password") String password,
            @Field("c_password") String c_password
    );
}
