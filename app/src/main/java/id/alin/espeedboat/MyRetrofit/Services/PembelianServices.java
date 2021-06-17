package id.alin.espeedboat.MyRetrofit.Services;

import java.util.Map;

import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.DetailPembelian.ServerResponseDetailPembelian;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Jadwal.ServerResponseJadwalData;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Pembelian.ServerResponsePembelian;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Poin.ServerResponsePoin;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Reward.ServerResponseReward;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Reward.ServerResponseRiwayatReward;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ServerResponseModels;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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

    @Multipart
    @POST("/api/postbuktipembayaran")
    Call<ServerResponseModels> postBuktiPembayaran(
            @HeaderMap Map<String, String> Header,
            @Part MultipartBody.Part image_bukti_pembayaran,
            @Part("id_pembelian") RequestBody id_pembelian
    );

    @FormUrlEncoded
    @POST("/api/batalkanpembelian")
    Call<ServerResponseModels> postBatalkanPembelian(
            @Header("Authorization") String Authorization,
            @Field("id_pembelian") String id_pembelian
    );

    @FormUrlEncoded
    @POST("/api/setpembelianstatus")
    Call<ServerResponseModels> postPembelianStatus(
            @Header("Authorization") String Authorization,
            @Field("id") long id,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("/api/setReview")
    Call<ServerResponseModels> postReview(
            @Header("Authorization") String Authorization,
            @Field("id") long id,
            @Field("rating") int rating,
            @Field("review") String review
    );

    @FormUrlEncoded
    @POST("/api/getPoin")
    Call<ServerResponsePoin> postPoin(
            @Header("Authorization") String Authorization,
            @Field("id") long id
    );

    @FormUrlEncoded
    @POST("/api/getReward")
    Call<ServerResponseReward> postReward(
            @Header("Authorization") String Authorization,
            @Field("id") long id
    );

    @FormUrlEncoded
    @POST("/api/tukarReward")
    Call<ServerResponseModels> tukarPoint(
            @Header("Authorization") String Authorization,
            @Field("id") long id,
            @Field("nama") String nama,
            @Field("telepon") String telepon,
            @Field("alamat") String alamat
    );

    @FormUrlEncoded
    @POST("/api/getRiwayatReward")
    Call<ServerResponseRiwayatReward> riwayatReward(
            @Header("Authorization") String Authorization,
            @Field("id") long id
    );

    @FormUrlEncoded
    @POST("/api/terimaReward")
    Call<ServerResponseModels> terimaReward(
            @Header("Authorization") String Authorization,
            @Field("id") long id
    );

    @FormUrlEncoded
    @POST("/api/setrefund")
    Call<ServerResponseModels> setRefund(
            @Header("Authorization") String Authorization,
            @Field("id_pembelian") long id_pembelian,
            @Field("rekening") String rekening,
            @Field("alasan") String alasan
    );

    @FormUrlEncoded
    @POST("/api/terimarefund")
    Call<ServerResponseModels> terimaRefund(
            @Header("Authorization") String Authorization,
            @Field("id_pembelian") long id_pembelian
    );
}
