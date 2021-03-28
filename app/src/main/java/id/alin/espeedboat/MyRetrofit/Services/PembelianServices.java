package id.alin.espeedboat.MyRetrofit.Services;

import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.DetailPembelian.ServerResponseDetailPembelian;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Jadwal.ServerResponseJadwalData;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Pembelian.ServerResponsePembelian;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PembelianServices {
    @FormUrlEncoded
    @POST("/api/getPembelian")
    Call<ServerResponsePembelian> getPembelian(
            @Header("Authorization") String Authorization,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("/api/getDetailPembelian")
    Call<ServerResponseDetailPembelian> getDetailPembelian(
            @Header("Authorization") String Authorization,
            @Field("id") long id
    );
}
