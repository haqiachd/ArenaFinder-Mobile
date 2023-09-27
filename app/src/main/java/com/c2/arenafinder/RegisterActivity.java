package com.example.fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
 EditText username;
 EditText password;
 EditText email;
 EditText conpass;
 Button regis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);
        username = findViewById(R.id.username);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.password);
        conpass = findViewById(R.id.conpass);
        regis = findViewById(R.id.regisbtn);


        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (username.getText().toString().equals("mahen") && email.getText().toString().equals("mahennekkers27@gmail.com") && password.getText().toString().equals("123") && conpass.getText().toString().equals("123")) {
                    // Registrasi berhasil
                    Toast.makeText(RegisterActivity.this, "Pendaftaran Akun Berhasil", Toast.LENGTH_SHORT).show();
                } else {
                    // Registrasi gagal
                    Toast.makeText(RegisterActivity.this, " Pendaftaran Akun Gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Mendapatkan referensi ke layout root (misalnya, ConstraintLayout atau RelativeLayout)
        View rootView = findViewById(android.R.id.content);

// Menambahkan listener klik pada layout root
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Menyembunyikan keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
    }

}
