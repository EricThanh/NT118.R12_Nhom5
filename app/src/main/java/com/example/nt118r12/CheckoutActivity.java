package com.example.nt118r12;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        ImageView ivBack = findViewById(R.id.ivBack);
        AppCompatButton btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        ivBack.setOnClickListener(v -> finish());

        btnPlaceOrder.setOnClickListener(v -> {
            Intent intent = new Intent(CheckoutActivity.this, SuccessActivity.class);
            startActivity(intent);
            finish();
        });
    }
}