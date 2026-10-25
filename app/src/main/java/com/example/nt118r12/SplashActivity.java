package com.example.nt118r12;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.ComponentActivity;
import android.widget.Button;

public class SplashActivity extends ComponentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Button btnRegister = findViewById(R.id.btnRegister);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(SplashActivity.this, RegisterActivity.class));
        });

        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        });
    }
}