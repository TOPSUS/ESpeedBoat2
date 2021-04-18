package id.alin.espeedboat.MyRetrofit.Services;

import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Golongan.ServerResponseGolonganData;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Pembelian.ServerResponsePembelian;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GolonganServices {

    @FormUrlEncoded
    @POST("/api/readgologan")
    Call<ServerResponseGolonganData> getGolongan(
            @Header("Authorization") String Authorization,
            @Field("id_pelabuhan") String id_pelabuhan
    );
}
