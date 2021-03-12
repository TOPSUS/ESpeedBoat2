package id.alin.espeedboat.MyRetrofit.Services;

import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ProfileData.ServerResponseProfileData;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

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
    @FormUrlEncoded
    @POST("/api/userprofile")
    Call<ServerResponseProfileData> userprofile(
            @Header("Authorization") String Authorization,
            @Field("id") String id
    );
}
