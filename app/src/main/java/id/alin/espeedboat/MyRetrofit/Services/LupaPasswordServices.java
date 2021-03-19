package id.alin.espeedboat.MyRetrofit.Services;

import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ProfileData.ServerResponseProfileData;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ServerResponseModels;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LupaPasswordServices {

    @FormUrlEncoded
    @POST("/api/requestemailcode")
    Call<ServerResponseModels> sendEmail(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("/api/verifikasilupapasswordemail")
    Call<ServerResponseModels> sendCode(
            @Field("code") String code,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("/api/ubahpasswordmenggunakanemail")
    Call<ServerResponseModels> sendNewPassword(
            @Field("code") String code,
            @Field("email") String email,
            @Field("password") String password,
            @Field("confirm_password") String confirm_password
    );

}
