package id.alin.espeedboat.MyRetrofit.Services;

import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Jadwal.ServerResponseJadwalData;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Pelabuhan.ServerResponsePelabuhanData;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface JadwalServices {
    public static final String SPEEDBOAT = "speedboat";
    public static final String FERI = "feri";

    @FormUrlEncoded
    @POST("/api/getjadwal")
    Call<ServerResponseJadwalData> getJadwal(
            @Header("Authorization") String Authorization,
            @Field("date") String date,
            @Field("id_asal_pelabuhan") String id_asal_pelabuhan,
            @Field("id_tujuan_pelabuhan") String id_tujuan_pelabuhan,
            @Field("jumlah_penumpang") String jumlah_penumpang,
            @Field("tipe_kapal") String tipe_kapal
    );

    @FormUrlEncoded
    @POST("/api/getjadwal")
    Call<ServerResponseJadwalData> getJadwal(
            @Header("Authorization") String Authorization,
            @Field("date") String date,
            @Field("id_asal_pelabuhan") String id_asal_pelabuhan,
            @Field("id_tujuan_pelabuhan") String id_tujuan_pelabuhan,
            @Field("id_golongan") String id_golongan,
            @Field("jumlah_penumpang") String jumlah_penumpang,
            @Field("tipe_kapal") String tipe_kapal
    );
}
