package com.c2.arenafinder.api.retrofit;

import androidx.annotation.NonNull;

import okhttp3.Credentials;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.c2.arenafinder.data.response.UsersResponse;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;

import java.util.concurrent.TimeUnit;

/**
 * Digunakan untuk menghubungkan aplikasi ke REST API / Server
 */
public class RetrofitClient {

//    public static final String BASE_URL = "http://192.168.96.152/arenafinderweb/c/s/"; // local
//    public static final String BASE_URL = "http://172.16.108.101/arenafinder/"; // wifi
    public static final String BASE_URL = "https://arenafinder.tifnganjuk.com/";

    public static final String CONTROLLERS = BASE_URL + "controllers/mobile/";

    public static final String PUBLIC_IMG =  BASE_URL + "public/img/";

    public static final String USER_PHOTO_URL = PUBLIC_IMG + "user-photo/";

    public static final String HOME_PAGER = PUBLIC_IMG + "homepager/";

    public static final String VENUE_IMG_URL = PUBLIC_IMG + "venue/";

    public static final String AKTIVITAS_URL = PUBLIC_IMG + "venue/";

    public static final String FASILITAS_URL = PUBLIC_IMG + "venue/";

    public static final String SUCCESSFUL_RESPONSE = "success";

    /**
     * Membuat instance dari koneksi
     *
     * @return koneksi
     */
    @NonNull
    public static RetrofitEndPoint getInstance(){
        return getConnection().create(RetrofitEndPoint.class);
    }

    /**
     * connect to the rest server
     */
    public static Retrofit getConnection() {
        LogApp.info(RetrofitClient.class, LogTag.RETROFIT_CONNECTION, "create connection to " + CONTROLLERS);
        Gson gson = new GsonBuilder().setLenient().create();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(CONTROLLERS)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }

    /**
     * cek apakah koneksi berhasil atau tidak
     *
     * @param response response dari koneksi
     * @return status koneksi
     */
    public static boolean apakahSukses(Response<UsersResponse> response) {
        return response.body() != null && response.body().getStatus().equalsIgnoreCase(SUCCESSFUL_RESPONSE);
    }
}
