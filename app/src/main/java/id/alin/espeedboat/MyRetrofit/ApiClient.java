package id.alin.espeedboat.MyRetrofit;

import android.content.Intent;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import id.alin.espeedboat.LoginActivity;
import id.alin.espeedboat.RegisterActivity;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;
    public static String BASE_URL = "https://www.espeedboat.xyz/";
    public static String BASE_IMAGE_USER = "https://www.espeedboat.xyz/storage/image_users/";
    public static String BASE_IMAGE_BERITA_PELABUHAN = "https://www.espeedboat.xyz/storage/image_berita_pelabuhan/";

    public static Retrofit getRetrofit(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}