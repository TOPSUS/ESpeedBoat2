package id.alin.espeedboat.MyRetrofit.Services;

import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Notification.ServerResponseNotificationData;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface NotificationServices {

    @POST("/api/getnotification")
    Call<ServerResponseNotificationData> getAllNotification(
            @Header("Authorization") String Authorization
    );
}
