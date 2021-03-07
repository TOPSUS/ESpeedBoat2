package id.alin.espeedboat.MyRetrofit.Services;

import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ProfileData;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthServices {

    @FormUrlEncoded
    @POST("/api/login")
    Call<ProfileData> login(
            @Field("email") String email,
            @Field("password") String password
    );
}
