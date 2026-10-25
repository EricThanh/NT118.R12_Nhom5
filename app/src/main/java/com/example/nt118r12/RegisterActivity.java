package com.example.nt118r12;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.ComponentActivity;
import android.widget.Button;

public class RegisterActivity extends ComponentActivity {

    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageView btnBack = findViewById(R.id.btnBack);
        ImageView ivTogglePassword = findViewById(R.id.ivTogglePassword);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnRegisterSubmit = findViewById(R.id.btnRegisterSubmit);
        TextView tvLoginLink = findViewById(R.id.tvLoginLink);

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

        btnRegisterSubmit.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        tvLoginLink.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}