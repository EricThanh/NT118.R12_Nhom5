package com.example.nt118r12;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class SuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        AppCompatButton btnContinueShopping = findViewById(R.id.btnContinueShopping);
        AppCompatButton btnMyOrders = findViewById(R.id.btnMyOrders);

        btnContinueShopping.setOnClickListener(v -> {
            Intent intent = new Intent(SuccessActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
        
        btnMyOrders.setOnClickListener(v -> {

        });
    }
}