package id.alin.espeedboat.MyRetrofit.Services;

import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ServerResponseModels;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PemesananServices {

    @FormUrlEncoded
    @POST("/api/postpemesanan")
    Call<ServerResponseModels> postPemesananTicket(
            @Header("Authorization") String authorization,
            @Field("id_pemesan") String id_pemesan,
            @Field("id_jadwal") String id_jadwal,
            @Field("penumpang") String penumpang
    );
}
