package com.c2.arenafinder.api.retrofit;

import com.c2.arenafinder.data.response.ArenaFinderResponse;
import com.c2.arenafinder.data.response.UsersResponse;
import com.c2.arenafinder.data.response.VerifyResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitEndPoint {

    @GET("cek_koneksi.php")
    Call<ArenaFinderResponse> cekKoneksi();

    @GET("users/cek_user.php")
    Call<UsersResponse> cekUser(
            @Query("email") String email
    );

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

    @FormUrlEncoded
    @POST("users/register.php")
    Call<UsersResponse> register(
            @Field("username") String username,
            @Field("email") String email,
            @Field("full_name") String fullName,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("users/register_google.php")
    Call<UsersResponse> registerGoogle(
            @Field("username") String username,
            @Field("email") String email,
            @Field("full_name") String fullName,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("users/update_pw.php")
    Call<UsersResponse> updatePassword(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("users/update_pp.php")
    Call<UsersResponse> uploadPhotoBase64(
            @Field("email") String email,
            @Field("photo") String photo);

    @FormUrlEncoded
    @POST("users/update_acc.php")
    Call<UsersResponse> updateAccount(
            @Field("email") String email,
            @Field("username") String username,
            @Field("full_name") String fullName
    );

    @GET("users/cek_userid.php")
    Call<UsersResponse> cekUserId(
            @Query("username") String username,
            @Query("email") String email
    );

    @FormUrlEncoded
    @POST("email/mail.php")
    Call<VerifyResponse> sendEmail(
            @Field("email") String email,
            @Field("type") String type
    );

}
