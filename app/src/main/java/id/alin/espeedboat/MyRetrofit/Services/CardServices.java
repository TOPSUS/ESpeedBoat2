package id.alin.espeedboat.MyRetrofit.Services;

import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Card.ServerResponseCards;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Golongan.ServerResponseGolonganData;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CardServices {

    @POST("/api/getcard")
    Call<ServerResponseCards> getCards(
            @Header("Authorization") String Authorization
    );
}
