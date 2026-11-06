package com.example.nt118r12.seller;

import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nt118r12.R;

public class SellerDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_dashboard);
        SellerUi.applyLightStatusBar(this);

        SellerNavHelper.setup(this, R.id.nav_wishlist);

        findViewById(R.id.btn_period).setOnClickListener(v ->
                Toast.makeText(this, "Chọn khoảng thời gian chưa có", Toast.LENGTH_SHORT).show());
        TextView tvSeeAll = findViewById(R.id.tv_see_all);
        tvSeeAll.setPaintFlags(tvSeeAll.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvSeeAll.setOnClickListener(v ->
                startActivity(new android.content.Intent(this, SellerProductActivity.class)));
    }
}
