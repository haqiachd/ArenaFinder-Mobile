package com.c2.arenafinder.api.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

import com.c2.arenafinder.data.model.AktivitasMemberModel;
import com.c2.arenafinder.data.model.EditCommentModel;
import com.c2.arenafinder.data.model.StatusPesananModel;
import com.c2.arenafinder.data.response.AktivitasDetailedResponse;
import com.c2.arenafinder.data.response.AktivitasMemberResponse;
import com.c2.arenafinder.data.response.AktivitasResponse;
import com.c2.arenafinder.data.response.AktivitasStatusResponse;
import com.c2.arenafinder.data.response.ArenaFinderResponse;
import com.c2.arenafinder.data.response.BerandaResponse;
import com.c2.arenafinder.data.response.CreateBookingResponse;
import com.c2.arenafinder.data.response.StatusPesananResponse;
import com.c2.arenafinder.data.response.UsersResponse;
import com.c2.arenafinder.data.response.ReferensiResponse;
import com.c2.arenafinder.data.response.VenueBookingResponse;
import com.c2.arenafinder.data.response.VenueDetailedResponse;
import com.c2.arenafinder.data.response.VenueExtendedResponse;
import com.c2.arenafinder.data.response.VenueReviewsResponse;
import com.c2.arenafinder.data.response.VerifyResponse;
import com.google.gson.annotations.Expose;

public interface RetrofitEndPoint {

    @GET("cek_koneksi.php")
    Call<ArenaFinderResponse> cekKoneksi();

    @GET("users/cek_user.php")
    Call<UsersResponse> cekUser(
            @Query("email") String email
    );

    @GET("users/cek_userid.php")
    Call<UsersResponse> cekUserID(
            @Query("username") String username,
            @Query("email") String email
    );

    @FormUrlEncoded
    @POST("users/login.php")
    Call<UsersResponse> login(
            @Field("userid") String userid,
            @Field("password") String password,
            @Field("device_token") String token
    );

    @FormUrlEncoded
    @POST("users/login_google.php")
    Call<UsersResponse> loginGoogle(
            @Field("email") String email,
            @Field("device_token") String token
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
            @Field("type") String type,
            @Field("action") String action
    );

    @FormUrlEncoded
    @POST("email/update_verify.php")
    Call<VerifyResponse> updateVerify(
            @Field("email") String email
    );

    @GET("feature/venue_baru.php")
    Call<ReferensiResponse> getVenueBaru();

    @GET("page/dashboard.php")
    Call<BerandaResponse> homePage();

    @GET("page/aktivitas.php")
    Call<AktivitasResponse> aktivitasPage();

    @GET("page/referensi.php")
    Call<ReferensiResponse> referensiPage();

    @GET("page/sub/type/sport_type_venue.php")
    Call<VenueExtendedResponse> sportType(
            @Query("sport") String sport
    );

    @GET("page/sub/all/allv_rating.php")
    Call<VenueExtendedResponse> getAllRating();

    @GET("page/sub/all/allv_kosong.php")
    Call<VenueExtendedResponse> getAllKosong();

    @GET("page/sub/all/allv_lokasi.php")
    Call<VenueExtendedResponse> getAllLokasi();

    @GET("page/sub/all/allv_gratis.php")
    Call<VenueExtendedResponse> getAllGratis();

    @GET("page/sub/all/allv_berbayar.php")
    Call<VenueExtendedResponse> getAllBebayar();

    @GET("page/sub/all/allv_disewakan.php")
    Call<VenueExtendedResponse> getAllDisewakan();

    @GET("page/sub/all/allv_baru.php")
    Call<VenueExtendedResponse> getAllBaru();

    @GET("page/sub/all/allv_rekomendasi.php")
    Call<VenueExtendedResponse> getAllRekomendasi();

    @GET("page/sub/search/allv_search.php")
    Call<VenueExtendedResponse> searchVenue(
            @Query("name") String name
    );

    @GET("feature/venues/venue_detailed.php")
    Call<VenueDetailedResponse> venueDetailed(
            @Query("id_venue") String idVenue
    );

    @GET("feature/venues/reviews/venue_reviews.php")
    Call<VenueReviewsResponse> venueComment(
            @Query("id_user") String idUser,
            @Query("id_venue") String idVenue
    );

    @PUT("feature/venues/reviews/edit_comment.php")
    Call<VenueReviewsResponse> editComment(
            @Body EditCommentModel model
    );

    @HTTP(method = "DELETE", path = "feature/venues/reviews/delete_comment.php", hasBody = true)
//    @DELETE("feature/venues/reviews/delete_comment.php")
    Call<VenueReviewsResponse> deleteComment(
            @Body EditCommentModel model
    );

    @GET("feature/venues/booking/get_jadwal.php")
    Call<VenueBookingResponse> getJadwal(
            @Query("id_venue") String idVenue,
            @Query("id_lapangan") String idLapnangan,
            @Query("date") String date
    );

    @FormUrlEncoded
    @POST("feature/venues/booking/new_booking.php")
    Call<CreateBookingResponse> createBooking(
            @Field("id_venue") String idVenue,
            @Field("email") String email,
            @Field("total_price") String totalPrice
    );


    @FormUrlEncoded
    @POST("feature/venues/booking/booking_detail.php")
    Call<CreateBookingResponse> bookingDetail(
            @Field("id_booking") String idVenue,
            @Field("date") String date,
            @Field("id_price") String idPrice
    );

    @GET("feature/venues/booking/order_status.php")
    Call<StatusPesananResponse> getStatusPesanan(
            @Query("email") String email,
            @Query("status") String status
    );

    @GET("feature/activities/activity_detailed.php")
    Call<AktivitasDetailedResponse> aktivitasDetailed(
            @Query("id_aktivitas") String idAktivitas,
            @Query("email") String email
    );

    @PUT("feature/activities/activity_join.php")
    Call<AktivitasMemberResponse> joinActivity(
            @Body AktivitasMemberModel model
    );

    @HTTP(method = "DELETE", path = "feature/activities/activity_leave.php", hasBody = true)
//    @DELETE("feature/activities/activity_leave.php")
    Call<AktivitasMemberResponse> leaveActivity(
            @Body AktivitasMemberModel model
    );

    @GET("feature/activities/activity_status.php")
    Call<AktivitasStatusResponse> statusActivity(
            @Query("email") String email,
            @Query("status") String status
    );

    @GET("page/sub/type/sport_type_activity.php")
    Call<AktivitasStatusResponse> sportActivity(
            @Query("sport") String sport
    );

    @GET("page/sub/search/alla_search.php")
    Call<AktivitasStatusResponse> searchActivity(
            @Query("name") String name
    );

}
