package id.alin.espeedboat.MyRetrofit.Services;

import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.MetodePembayaran.ServerResponseMetodePembayaranData;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Pembelian.ServerResponsePembelianData;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ServerResponseModels;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PemesananServices {

    @FormUrlEncoded
    @POST("/api/postpemesanan")
    Call<ServerResponsePembelianData> postPemesananTicket(
            @Header("Authorization") String authorization,
            @Field("id_pemesan") String id_pemesan,
            @Field("id_jadwal") String id_jadwal,
            @Field("id_metode_pembayaran") String id_metode_pembayaran,
            @Field("penumpang") String penumpang,
            @Field("tipe_kapal") String tipe_kapal
    );

    @FormUrlEncoded
    @POST("/api/postpemesanan")
    Call<ServerResponsePembelianData> postPemesananTicket(
            @Header("Authorization") String authorization,
            @Field("id_pemesan") String id_pemesan,
            @Field("id_jadwal") String id_jadwal,
            @Field("id_metode_pembayaran") String id_metode_pembayaran,
            @Field("penumpang") String penumpang,
            @Field("tipe_kapal") String tipe_kapal,
            @Field("id_golongan") String id_golongan,
            @Field("nomor_polisi") String nomor_polisi
    );


    @POST("/api/getmetodepembayaran")
    Call<ServerResponseMetodePembayaranData> getMetodePembayaran(
            @Header("Authorization") String authorization
    );

}
