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
                Toast.makeText(this, "Màn đơn hàng do thành viên khác phụ trách", Toast.LENGTH_SHORT).show());

        for (int id : new int[]{R.id.tv_home_stat_1, R.id.tv_home_stat_2, R.id.tv_home_stat_3}) {
            TextView stat = findViewById(id);
            SpannableString text = new SpannableString(stat.getText());
            text.setSpan(new AbsoluteSizeSpan(20, true), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            stat.setText(text);
        }

        setupQuantity(R.id.btn_qty_minus_1, R.id.btn_qty_plus_1, R.id.tv_qty_1);
        setupQuantity(R.id.btn_qty_minus_2, R.id.btn_qty_plus_2, R.id.tv_qty_2);
    }

    private void setupQuantity(int minusId, int plusId, int qtyId) {
        TextView qty = findViewById(qtyId);
        findViewById(minusId).setOnClickListener(v -> {
            int value = Integer.parseInt(qty.getText().toString());
            if (value > 1) qty.setText(String.valueOf(value - 1));
        });
        findViewById(plusId).setOnClickListener(v -> {
            int value = Integer.parseInt(qty.getText().toString());
            qty.setText(String.valueOf(value + 1));
        });
    }
}
