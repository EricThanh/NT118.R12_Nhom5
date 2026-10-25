package com.example.nt118r12;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.ComponentActivity;
import android.widget.Button;

public class LoginActivity extends ComponentActivity {

    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView btnBack = findViewById(R.id.btnBack);
        ImageView ivTogglePassword = findViewById(R.id.ivTogglePassword);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnLoginSubmit = findViewById(R.id.btnLoginSubmit);
        TextView tvRegisterLink = findViewById(R.id.tvRegisterLink);

        btnBack.setOnClickListener(v -> finish());

        ivTogglePassword.setOnClickListener(v -> {
            isPasswordVisible = !isPasswordVisible;
            if (isPasswordVisible) {
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                ivTogglePassword.setImageResource(R.drawable.ic_eye_on);
            } else {
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                ivTogglePassword.setImageResource(R.drawable.ic_eye_off);
            }
            etPassword.setSelection(etPassword.getText().length());
        });

        btnLoginSubmit.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        tvRegisterLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });
    }
}