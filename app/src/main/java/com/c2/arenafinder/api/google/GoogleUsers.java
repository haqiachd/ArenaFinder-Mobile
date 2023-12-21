package com.c2.arenafinder.api.google;

import android.content.Intent;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.util.ArenaFinder;

/**
 * Digunakan untuk melakukan login atau register dengan Google
 */
public class GoogleUsers {

    public static final int REQUEST_CODE = 1000;

    private final FragmentActivity fragmentActivity;

    private GoogleApiClient googleApiClient;

    private Intent signInIntent;

    private GoogleSignInAccount account;

    private boolean isAccountSelected = false;

    public GoogleUsers(@NonNull FragmentActivity fragmentActivity){

        LogApp.info(this, LogTag.CONSTRUCTOR, "create " + this.getClass().getSimpleName());
        this.fragmentActivity = fragmentActivity;

        // konfigurasi untuk permintaan autentikasi google oauth
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        try{
            if (googleApiClient != null) {
                onDestroy();
                LogApp.info(this, LogTag.GOOGLE_SIGN, "Google Users Non null client");
            }else{
                LogApp.info(this, LogTag.GOOGLE_SIGN, "Google Users On null client");
            }

            // integrasi layanan google oauth
            googleApiClient = new GoogleApiClient.Builder(fragmentActivity.getApplicationContext())
                    .enableAutoManage(fragmentActivity, connectionResult -> {
                        // jika gagal
                        LogApp.error(this, LogTag.METHOD, fragmentActivity.getString(R.string.err_google_auth_connection));
                        Toast.makeText(fragmentActivity.getApplicationContext(), fragmentActivity.getString(R.string.err_google_auth_connection), Toast.LENGTH_SHORT).show();
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();

            // inisialisasi intent untuk menampilkan google oauth intent
            signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        }catch (Throwable ex){
            ex.printStackTrace();
            ArenaFinder.playVibrator(fragmentActivity, ArenaFinder.VIBRATOR_LONG);
            Toast.makeText(fragmentActivity, "Fatal Error : " + ex.getMessage(), Toast.LENGTH_LONG).show();
//            ArenaFinder.restartApplication(fragmentActivity.getApplicationContext(), SplashScreenActivity.class);
        }

    }

    /**
     *
     * @return mendapatkan intent atau tampilan dari google auth
     */
    public Intent getIntent(){
        return signInIntent;
    }

    /**
     * aksi saat intent google auth dibuka
     *
     */
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        LogApp.info(this, LogTag.GOOGLE_SIGN, "Membuka intent Google Auth");

        isAccountSelected = false;
        // cek koneksi internet ada atau tidak
        if(!ArenaFinder.isInternetConnected(fragmentActivity)){
            LogApp.error(this, LogTag.GOOGLE_SIGN, fragmentActivity.getString(R.string.err_no_internet));
            Toast.makeText(fragmentActivity, fragmentActivity.getString(R.string.err_no_internet), Toast.LENGTH_SHORT).show();
            return;
        }

        // mendapatkan data akun yang dipilih
        if (requestCode == REQUEST_CODE && data != null) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            // jika data gagal didapatkan
            if(result == null || !result.isSuccess()){
                LogApp.warn(this, LogTag.GOOGLE_SIGN, fragmentActivity.getString(R.string.err_no_data_selected));
                Toast.makeText(fragmentActivity.getApplicationContext(), fragmentActivity.getString(R.string.err_no_data_selected), Toast.LENGTH_SHORT)
                        .show();
            }else{
                // mendapatkan data akun dan menyimpan datanya ke object account
                this.account = result.getSignInAccount();
                this.resetLastSignIn(); // remove
                isAccountSelected = true;
            }
        }else {
            LogApp.warn(this, LogTag.GOOGLE_SIGN, "request code is not valid or intent is null");
            Toast.makeText(fragmentActivity.getApplicationContext(), fragmentActivity.getString(R.string.err_google_auth_fatal), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *
     * @return data-data dari akun yang dipilih
     */
    public GoogleSignInAccount getUserData(){
        return this.account;
    }

    /**
     *
     * @return untuk mengecek apakah ada akun yang dipilih atau tidak
     */
    public boolean isAccountSelected(){
        return isAccountSelected;
    }

    /**
     * Menghapus akun yang sebelumnya dipilih
     */
    public void resetLastSignIn() {
        LogApp.info(this, LogTag.GOOGLE_SIGN, "Remove data akun yang sedang dipilih");

        if (googleApiClient != null) {
            if (googleApiClient.isConnected()) {
                // menghapus data akun sebelumnya
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        LogApp.info(fragmentActivity, LogTag.GOOGLE_SIGN, "Remove successfully");
                    }
                });
            }
        }
    }

    /**
     * disconect dari google api client, untuk reset app
     */
    public void onDestroy() {
        if (googleApiClient != null) {
            LogApp.error(this, LogTag.SIGNIN, "ON DESTROY");
            googleApiClient.stopAutoManage(fragmentActivity);
            googleApiClient.disconnect();
        }
    }

}
