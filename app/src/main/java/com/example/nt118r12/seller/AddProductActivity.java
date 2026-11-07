package com.example.nt118r12.seller;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.nt118r12.R;

public class AddProductActivity extends AppCompatActivity {
    private final int[] sizeIds = {R.id.size_38, R.id.size_39, R.id.size_40, R.id.size_41};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        SellerUi.applyLightStatusBar(this);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
        findViewById(R.id.btn_add_photo).setOnClickListener(v ->
                Toast.makeText(this, "Chọn ảnh chưa có", Toast.LENGTH_SHORT).show());


        for (int id : sizeIds) {
            TextView chip = findViewById(id);
            chip.setSelected(id == R.id.size_39);
            applySizeStyle(chip);
            chip.setOnClickListener(v -> {
                chip.setSelected(!chip.isSelected());
                applySizeStyle(chip);
            });
        }

        findViewById(R.id.btn_save_draft).setOnClickListener(v -> {
            Toast.makeText(this, "Đã lưu nháp", Toast.LENGTH_SHORT).show();
            finish();
        });
        findViewById(R.id.btn_publish).setOnClickListener(v -> {
            Toast.makeText(this, "Đã đăng sản phẩm", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void applySizeStyle(TextView chip) {
        boolean selected = chip.isSelected();
        chip.setBackgroundResource(selected ? R.drawable.seller_bg_size_chip_selected : R.drawable.seller_bg_size_chip);
        chip.setTextColor(ContextCompat.getColor(this, selected ? R.color.white : R.color.seller_ink_filter));
    }
}
