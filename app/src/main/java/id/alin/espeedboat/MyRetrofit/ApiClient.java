package id.alin.espeedboat.MyRetrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;
    public static String BASE_URL = "https://www.espeedboat.xyz/";

    public static Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit =new retrofit2.Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }
}
