package com.example.nt118r12.seller;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118r12.R;
import com.example.nt118r12.adapters.OrderItemAdapter;
import com.example.nt118r12.models.OrderItem;
import com.example.nt118r12.models.SellerOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_ORDER = "EXTRA_ORDER";

    private TextView tvToolbarTitle;
    private TextView tvOrderStatus;
    private TextView tvPlacedDate;
    private TextView tvBuyerName;
    private TextView tvBuyerPhone;
    private TextView tvShippingAddress;
    private TextView tvPaymentMethod;
    private TextView tvShippingFee;
    private TextView tvTotalAmount;
    private TextView tvItemsCountTitle;
    private RecyclerView rvOrderItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        SellerUi.applyLightStatusBar(this);

        initViews();
        setupToolbar();
        bindData();
    }

    private void initViews() {
        tvToolbarTitle = findViewById(R.id.tv_toolbar_title);
        tvOrderStatus = findViewById(R.id.tv_order_status);
        tvPlacedDate = findViewById(R.id.tv_placed_date);
        tvBuyerName = findViewById(R.id.tv_buyer_name);
        tvBuyerPhone = findViewById(R.id.tv_buyer_phone);
        tvShippingAddress = findViewById(R.id.tv_shipping_address);
        tvPaymentMethod = findViewById(R.id.tv_payment_method);
        tvShippingFee = findViewById(R.id.tv_shipping_fee);
        tvTotalAmount = findViewById(R.id.tv_total_amount);
        tvItemsCountTitle = findViewById(R.id.tv_items_count_title);
        rvOrderItems = findViewById(R.id.rv_order_items);

        ImageView btnMessageBuyer = findViewById(R.id.btn_message_buyer);
        btnMessageBuyer.setOnClickListener(v ->
                Toast.makeText(this, "Open chat with " + tvBuyerName.getText(), Toast.LENGTH_SHORT).show());

        findViewById(R.id.btn_reject).setOnClickListener(v -> {
            Toast.makeText(this, "Order rejected", Toast.LENGTH_SHORT).show();
            finish();
        });

        findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            Toast.makeText(this, "Order confirmed successfully", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void setupToolbar() {
        ImageView btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());
    }

    private void bindData() {
        SellerOrder passedOrder = (SellerOrder) getIntent().getSerializableExtra(EXTRA_ORDER);
        String orderId = (passedOrder != null) ? passedOrder.getOrderId() : "#1024";
        String date = (passedOrder != null) ? passedOrder.getOrderDate() : "12 Oct 2023, 14:30";
        String buyer = (passedOrder != null) ? passedOrder.getBuyerName() : "Alex Johnson";
        double total = (passedOrder != null) ? passedOrder.getTotalPrice() : 282.00;

        tvToolbarTitle.setText("Order " + orderId);
        tvPlacedDate.setText("Placed on: " + date);
        tvBuyerName.setText(buyer);
        tvTotalAmount.setText(String.format(Locale.US, "$%.2f", total));

        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem("Nike React Miler", "EU 42", 140.00, 1));
        items.add(new OrderItem("Nike Air Max 270", "EU 43", 140.00, 1));

        tvItemsCountTitle.setText("Items Ordered (" + items.size() + ")");
        rvOrderItems.setAdapter(new OrderItemAdapter(items));
    }
}
