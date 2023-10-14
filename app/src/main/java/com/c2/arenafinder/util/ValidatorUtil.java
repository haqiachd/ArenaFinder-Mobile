package com.c2.arenafinder.util;

import androidx.annotation.StringRes;
import com.c2.arenafinder.R;
import java.util.regex.Pattern;

public class ValidatorUtil {

    private @StringRes int message;

    public int getMessage() {
        return message;
    }

    private boolean isNull(String data){
        if (data != null){
            return data.isEmpty() || data.isBlank();
        }else {
            return true;
        }
    }

    public boolean isValidUsername(String username) {

        // cek username null atau tidak
        if (isNull(username)){
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
        if (isNull(email)){
            message = R.string.err_email_empty;
            return false;
        }

        if(Pattern.compile(emailRegex).matcher(email).matches()){
            // jika email valid
            message = R.string.suc_email_valid;
            return true;
        }else {
            message = R.string.err_email_invalid;
            return false;
        }
    }

    public boolean isValidName(String name) {

        // cek name null atau tidak
        if (isNull(name)){
            message = R.string.err_email_empty;
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

    public static void main(String[] args) {

        ValidatorUtil validationUtil = new ValidatorUtil();
        System.out.println("\n");
        System.out.print("username : ");
        System.out.println(validationUtil.isValidUsername("haqi"));
        System.out.println(validationUtil.getMessage());
        System.out.println("----");

        System.out.print("email : ");
        System.out.println(validationUtil.isValidEmail("hakiahamd756@gmail.com"));
        System.out.println(validationUtil.getMessage());
        System.out.println("----");

        System.out.print("nama : ");
        System.out.println(validationUtil.isValidName("Achmad Baihaqi"));
        System.out.println(validationUtil.getMessage());
        System.out.println("----");

        System.out.print("password : ");
        System.out.println(validationUtil.isValidPassword("Qqq.1234"));
        System.out.println(validationUtil.getMessage());
        System.out.println("----");

    }
}
