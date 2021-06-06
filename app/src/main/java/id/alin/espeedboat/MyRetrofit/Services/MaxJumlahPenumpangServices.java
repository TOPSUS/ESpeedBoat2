package id.alin.espeedboat.MyRetrofit.Services;

import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.MaxJumlahPenumpang.ServerResponseMaxJumlahPenumpang;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ServerResponseModels;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MaxJumlahPenumpangServices {

    @FormUrlEncoded
    @POST("/api/getmaxjumlahpenumpang")
    Call<ServerResponseMaxJumlahPenumpang> getMaxPenumpang(
            @Header("Authorization") String Authorization,
            @Field("id_golongan") String email
    );
}
