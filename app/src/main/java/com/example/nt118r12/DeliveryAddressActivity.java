package com.example.nt118r12;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

public class DeliveryAddressActivity extends AppCompatActivity {

    private View cardHome, cardOffice;
    private AppCompatRadioButton rbHome, rbOffice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);
        CustomerOrdersActivity.applySystemBarInsets(findViewById(R.id.root));

        findViewById(R.id.ivBack).setOnClickListener(v -> finish());

        cardHome = findViewById(R.id.cardHome);
        cardOffice = findViewById(R.id.cardOffice);
        rbHome = findViewById(R.id.rbHome);
        rbOffice = findViewById(R.id.rbOffice);

        cardHome.setOnClickListener(v -> selectAddress(true));
        cardOffice.setOnClickListener(v -> selectAddress(false));

        findViewById(R.id.ivEditHome).setOnClickListener(v ->
                Toast.makeText(this, "Sửa địa chỉ Nhà riêng", Toast.LENGTH_SHORT).show());
        findViewById(R.id.ivEditOffice).setOnClickListener(v ->
                Toast.makeText(this, "Sửa địa chỉ Văn phòng", Toast.LENGTH_SHORT).show());
        findViewById(R.id.btnAddAddress).setOnClickListener(v ->
                Toast.makeText(this, "Thêm địa chỉ mới", Toast.LENGTH_SHORT).show());

        findViewById(R.id.btnUseAddress).setOnClickListener(v -> {
            String name = rbHome.isChecked() ? "Nhà riêng" : "Văn phòng";
            Toast.makeText(this, "Đã chọn địa chỉ: " + name, Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void selectAddress(boolean home) {
        rbHome.setChecked(home);
        rbOffice.setChecked(!home);
        cardHome.setBackgroundResource(home ? R.drawable.bg_card_selected : R.drawable.bg_card);
        cardOffice.setBackgroundResource(home ? R.drawable.bg_card : R.drawable.bg_card_selected);
    }
}
