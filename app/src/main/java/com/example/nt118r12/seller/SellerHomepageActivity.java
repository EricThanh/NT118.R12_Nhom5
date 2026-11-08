package com.example.nt118r12.seller;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nt118r12.R;

public class SellerHomepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_homepage);
        SellerUi.applyLightStatusBar(this);

        SellerNavHelper.setup(this, R.id.nav_home);

        findViewById(R.id.btn_notification).setOnClickListener(v ->
                Toast.makeText(this, "Chưa có thông báo", Toast.LENGTH_SHORT).show());
        findViewById(R.id.btn_home_add_product).setOnClickListener(v ->
                startActivity(new Intent(this, AddProductActivity.class)));
        findViewById(R.id.btn_home_view_orders).setOnClickListener(v ->
                startActivity(new Intent(this, SellerOrdersActivity.class)));

        for (int id : new int[]{R.id.tv_home_stat_1, R.id.tv_home_stat_2, R.id.tv_home_stat_3}) {
            TextView stat = findViewById(id);
            SpannableString text = new SpannableString(stat.getText());
            text.setSpan(new AbsoluteSizeSpan(24, true), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            stat.setText(text);
        }
    }
}