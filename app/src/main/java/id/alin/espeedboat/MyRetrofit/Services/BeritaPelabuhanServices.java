package id.alin.espeedboat.MyRetrofit.Services;

import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.BeritaEspeed.ServerResponseBeritaEspeed;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.BeritaPelabuhan.ServerResponseBeritaPelabuhan;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ProfileData.ServerResponseProfileData;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/*
* DIGUNAKAN UNTUK BERINTERAKSI DENGAN BERITA PROFILE SERVER
* */
public interface BeritaPelabuhanServices {

    /*
     * DIGUNAKAN UNTUK MENGAMBIL USER PROFILE
     * PARAMETER YANG DIPERLUKAN ADALAH ID
     * */
    @POST("/api/readberitapelabuhan")
    Call<ServerResponseBeritaPelabuhan> readAllBeritaPelabuhan(
            @Header("Authorization") String Authorization
    );

    /*
     * DIGUNAKAN UNTUK MENGAMBIL USER PROFILE
     * PARAMETER YANG DIPERLUKAN ADALAH ID
     * */
    @POST("/api/readberitaespeed")
    Call<ServerResponseBeritaEspeed> readAllBeritaKapal(
            @Header("Authorization") String Authorization
    );

}
