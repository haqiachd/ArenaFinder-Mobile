package com.c2.arenafinder.api.retrofit;

import com.c2.arenafinder.data.response.UsersResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitEndPoint {

    @FormUrlEncoded
    @POST("login.php")
    Call<UsersResponse> login (
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login_google.php")
    Call<UsersResponse> loginGoogle(
            @Field("email") String email
    );

}
