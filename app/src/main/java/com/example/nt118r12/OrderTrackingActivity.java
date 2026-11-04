package com.example.nt118r12;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OrderTrackingActivity extends AppCompatActivity {

    public static final String EXTRA_ORDER_ID = "ORDER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);
        CustomerOrdersActivity.applySystemBarInsets(findViewById(R.id.root));

        findViewById(R.id.ivBack).setOnClickListener(v -> finish());

        String orderId = getIntent().getStringExtra(EXTRA_ORDER_ID);
        if (orderId != null) {
            TextView tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
            tvHeaderTitle.setText("Đơn #" + orderId);
        }

        TextView tvTrackingCode = findViewById(R.id.tvTrackingCode);
        findViewById(R.id.btnCopy).setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboard != null) {
                clipboard.setPrimaryClip(ClipData.newPlainText("Mã vận đơn", tvTrackingCode.getText()));
                Toast.makeText(this, "Đã sao chép mã vận đơn", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btnContactSeller).setOnClickListener(v ->
                Toast.makeText(this, "Đang mở cuộc trò chuyện với người bán", Toast.LENGTH_SHORT).show());
    }
}
