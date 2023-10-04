package com.c2.arenafinder.api.retrofit;

import com.c2.arenafinder.data.response.UsersResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitEndPoint {

    @FormUrlEncoded
    @POST("users/login.php")
    Call<UsersResponse> login (
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("users/login_google.php")
    Call<UsersResponse> loginGoogle(
            @Field("email") String email
    );

    @Multipart
    @POST("users/update_pp.php")
    Call<UsersResponse> uploadPhotoMultipart(
            @Part("action") RequestBody action,
            @Part MultipartBody.Part photo);

    @FormUrlEncoded
    @POST("users/update_pp.php")
    Call<UsersResponse> uploadPhotoBase64(
            @Field("email") String email,
            @Field("photo") String photo);

}
