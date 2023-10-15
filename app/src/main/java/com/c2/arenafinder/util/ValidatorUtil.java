package com.c2.arenafinder.util;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.ui.custom.ButtonAccountCustom;
import com.google.android.material.button.MaterialButton;

import java.util.regex.Pattern;

public class ValidatorUtil {

    private @StringRes
    int message;
    private static final @ColorRes int SUCCESS_COLOR = R.color.jade_green;
    private static final @ColorRes int ERROR_COLOR = R.color.orangered;

    private boolean isValidUsername, isValidEmail, isValidFullName, isValidPassword, isValidKonf;

    private final Context context;
    private final ButtonAccountCustom btnAccount;
    private final MaterialButton btnMaterial;
    private final TextView txtHelper;

    public ValidatorUtil(Context context, ButtonAccountCustom btnAccount, TextView txtHelper) {
        this.context = context;
        this.btnAccount = btnAccount;
        this.btnMaterial = null;
        this.txtHelper = txtHelper;
    }

    public ValidatorUtil(Context context, MaterialButton btnMaterial, TextView txtHelper){
        this.context = context;
        this.btnAccount = null;
        this.btnMaterial = btnMaterial;
        this.txtHelper = txtHelper;
    }

    public int getMessage() {
        return message;
    }

    private boolean isNull(String data) {
        if (data != null) {
            return data.isEmpty() || data.isBlank();
        } else {
            return true;
        }
    }

    public boolean isValidUsername(String username) {

        // cek username null atau tidak
        if (isNull(username)) {
            message = R.string.err_username_empty;
            return false;
        }

        // panjang username harus antara 4 hingga 20 karakter.
        if (username.length() < 4 || username.length() > 20) {
            message = R.string.err_username_length;
            return false;
        }

        // karakter yang diizinkan adalah huruf, angka, '.', ',', '-', dan '_'.
        String allowedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.,-_";

        // periksa setiap karakter dalam username.
        for (char c : username.toCharArray()) {
            if (allowedCharacters.indexOf(c) == -1) {
                message = R.string.err_username_symbols;
                return false; // jika mengandung karakter yang tidak diizinkan
            }
        }

        // jika username valid
        message = R.string.suc_username_valid;
        return true;
    }

    public boolean isValidEmail(String email) {
        // regex dari email
        String emailRegex = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        // cek email null atau tidak
        if (isNull(email)) {
            message = R.string.err_email_empty;
            return false;
        }

        if (Pattern.compile(emailRegex).matcher(email).matches()) {
            // jika email valid
            message = R.string.suc_email_valid;
            return true;
        } else {
            message = R.string.err_email_invalid;
            return false;
        }
    }

    public boolean isValidName(String name) {

        // cek name null atau tidak
        if (isNull(name)) {
            message = R.string.err_name_empty;
            return false;
        }

        // panjang nama harus berada dalam rentang 4 hingga 50 karakter.
        if (name.length() < 4 || name.length() > 50) {
            message = R.string.err_name_length;
            return false;
        }

        // nama tidak boleh mengandung angka.
        if (name.matches(".*\\d.*")) {
            message = R.string.err_name_numbers;
            return false;
        }

        // simbol yang diizinkan adalah '.', ',', '-', dan ''' (tanda kutip tunggal).
        if (!name.matches("^[a-zA-Z.,\\-' ]+$")) {
            message = R.string.err_name_symbols;
            return false;
        }

        // jika nama valid
        message = R.string.suc_name_valid;
        return true;
    }

    public boolean isValidPassword(String password) {

        // cek password null atau tidak
        if (isNull(password)){
            message = R.string.err_password_empty;
            return false;
        }

        // panjang password harus berada dalam rentang 8 hingga 30 karakter.
        if (password.length() < 8 || password.length() > 30) {
            message = R.string.err_password_length;
            return false;
        }

        // password harus mengandung setidaknya satu huruf kecil.
        if (!Pattern.compile("[a-z]").matcher(password).find()) {
            message = R.string.err_password_lowercase;
            return false;
        }

        // password harus mengandung setidaknya satu huruf besar.
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            message = R.string.err_password_uppercase;
            return false;
        }

        // password harus mengandung setidaknya satu angka.
        if (!Pattern.compile("[0-9]").matcher(password).find()) {
            message = R.string.err_password_number;
            return false;
        }

        // password harus mengandung setidaknya satu simbol (contoh: !@#$%^&*()_+{}[]:;<>,.?~\/|=).
        if (!Pattern.compile("[!@#$%^&*()_+{}\\[\\]:;<>,.?~\\\\/|=]").matcher(password).find()) {
            message = R.string.err_password_symbol;
            return false;
        }

        // jika password valid
        message = R.string.suc_password_valid;
        return true;
    }

    public boolean isValidConfirm(String password, String confirm){

        // cek password null atau tidak
        if (isNull(confirm)){
            message = R.string.err_password_konf_empty;
            return false;
        }

        // jika konfirmasi tidak cocok
        if (!confirm.equals(password)){
            message = R.string.err_password_konf_invalid;
            return false;
        }

        // jika konfirmasi cocok
        message = R.string.suc_password_konf_valid;
        return true;
    }

    private void actionSuccess(@StringRes int msgSuccess) {
        if (btnAccount != null){
            btnAccount.setStatus(ButtonAccountCustom.ENABLE);
        }
        if (btnMaterial != null){
            btnMaterial.setEnabled(true);
        }
        txtHelper.setText(msgSuccess);
        txtHelper.setTextColor(ContextCompat.getColor(context, SUCCESS_COLOR));
    }

    private void actionError(@StringRes int msgError) {
        if (btnAccount != null){
            btnAccount.setStatus(ButtonAccountCustom.DISABLE);
        }
        if (btnMaterial != null){
            btnMaterial.setEnabled(false);
        }
        txtHelper.setText(msgError);
        txtHelper.setTextColor(ContextCompat.getColor(context, ERROR_COLOR));
    }

    public void signupFirstValidation(String username, String email, String fullName) {

        // validasi data
        if (isValidUsername(username)) {
            isValidUsername = true;
            LogApp.info(context, "tingkat 1");
            if (isValidEmail(email)) {
                isValidEmail = true;
                LogApp.info(context, "tingkat 2");
                isValidFullName = isValidName(fullName);
            } else {
                isValidEmail = false;
            }
        } else {
            isValidUsername = false;
        }

        if (isValidUsername && isValidEmail && isValidFullName){
            actionSuccess(R.string.suc_userdata_valid);
        }else {
            actionError(message);
        }
    }

    public void signupGoogleValidation(String username, String password, String confirm){
        if (isValidUsername(username)){
            isValidUsername = true;
            if (isValidPassword(password)){
                isValidPassword = true;
                isValidKonf = isValidConfirm(password, confirm);
            }else {
                isValidPassword = false;
            }
        }else {
            isValidUsername = false;
        }

        if (isValidPassword && isValidKonf){
            actionSuccess(R.string.suc_userdata_valid);
        }else {
            actionError(message);
        }

    }

    public void signinValidation(String userid, String password) {

        // validasi userid dan passoword
        if (isValidEmail(userid) || isValidUsername(userid)) {
            isValidEmail = true;
            isValidUsername = true;
            isValidPassword = isValidPassword(password);
        } else {
            isValidEmail = false;
            isValidUsername = false;
            message = R.string.err_userid_invalid;
        }

        // jika data valid
        if (isValidUsername && isValidEmail && isValidPassword) {
            actionSuccess(R.string.suc_userdata_valid);
            LogApp.info(context, LogTag.ON_VALIDATOR, context.getString(R.string.suc_userdata_valid));
        } else {
            actionError(message);
        }
    }

    public void forgotPassValidation(String email) {

        // validasi data
        if (isValidEmail(email)) {
            actionSuccess(R.string.suc_email_valid);
        } else {
            actionError(message);
        }
    }

    public void inputPassword(String password, String confirm){

        if (isValidPassword(password)){
            isValidPassword = true;
            isValidKonf = isValidConfirm(password, confirm);
        }else {
            isValidPassword = false;
        }

        if (isValidPassword && isValidKonf){
            actionSuccess(R.string.suc_password_valid);
        }else {
            actionError(message);
        }

    }
}
