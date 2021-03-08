package id.alin.espeedboat.MyRetrofit.Services;

import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ProfileData.ProfileData;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AuthServices {

    @FormUrlEncoded
    @POST("/api/login")
    Call<ProfileData> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @Multipart
    @POST("/api/register")
    Call<ProfileData> register(
            @Part("nama") RequestBody nama,
            @Part("alamat") RequestBody alamat,
            @Part("jeniskelamin") RequestBody jeniskelamin,
            @Part("nohp") RequestBody nohp,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("c_password") RequestBody c_password,
            @Part MultipartBody.Part profileimage
    );
}
