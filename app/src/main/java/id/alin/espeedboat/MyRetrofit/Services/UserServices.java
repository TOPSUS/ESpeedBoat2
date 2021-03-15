package id.alin.espeedboat.MyRetrofit.Services;

import java.util.Map;

import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ProfileData.ServerResponseProfileData;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * SERVICE RETROFIT YANG AKAN DIGUNAKAN UNTUK
 * MEMINTA SEMUA KEPERLUAN USER
 *
 * BAIK REFRESH PROFILE, MENGUBAH DATA, MENGHAPUS, LOGOUT DAN LAIN - LAIN
 */
public interface UserServices {

    /*
    * DIGUNAKAN UNTUK MENGAMBIL USER PROFILE
    * PARAMETER YANG DIPERLUKAN ADALAH ID
    * */
    @POST("/api/userprofile")
    Call<ServerResponseProfileData> userprofile(
            @Header("Authorization") String Authorization
    );

    /*
     * EDIT PROFILE DENGAN INPUT GAMBAR
     *
     * RETURNNYA BERUPA SERVERRESPONSEPROFILE DATA
     *
     * DISINI SSERVERRESPONSEPROFILE TIDAK DIRUBAH KE PROFILE DATA KARENA BELUM ADA PROSES LOGIN
     * */
    @Multipart
    @POST("/api/editProfile")
    Call<ServerResponseProfileData> editImage(
            RequestBody body,
            RequestBody requestBody,
            @Part("nama") RequestBody nama,
            @Part("alamat") RequestBody alamat,
            @Part("jeniskelamin") RequestBody jeniskelamin,
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
    @POST("/api/user/editProfile")
    Call<ServerResponseProfileData> editProfile(
            @Header("Authorization") String Authorization,
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("jeniskelamin") String jeniskelamin,
            @Field("nohp") String nohp,
            @Field("email") String email,
            @Field("photo") String photo
    );
}
