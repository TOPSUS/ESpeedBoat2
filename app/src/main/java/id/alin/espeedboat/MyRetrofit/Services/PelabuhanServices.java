package id.alin.espeedboat.MyRetrofit.Services;

import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Pelabuhan.ServerResponsePelabuhanData;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ProfileData.ServerResponseProfileData;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PelabuhanServices {

    @POST("/api/readpelabuhan")
    Call<ServerResponsePelabuhanData> readPelabuhan(
        @Header("Authorization") String Authorization
    );

}
