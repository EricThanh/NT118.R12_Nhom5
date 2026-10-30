package com.example.nt118r12;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118r12.adapters.OrderAdapter;
import com.example.nt118r12.models.Order;

import java.util.ArrayList;
import java.util.List;

public class CustomerOrdersActivity extends AppCompatActivity {

    private final List<Order> allOrders = new ArrayList<>();
    private OrderAdapter orderAdapter;
    private TextView tvEmpty;
    private TextView[] tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_orders);
        applySystemBarInsets(findViewById(R.id.root));

        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(v -> finish());

        tvEmpty = findViewById(R.id.tvEmpty);

        allOrders.add(new Order("1024", "22/10/2026 · 09:30", "Nike React Miler", "Size 42 · x1",
                170.0, 2, 282.0, Order.STATUS_PENDING));
        allOrders.add(new Order("1019", "20/10/2026 · 14:12", "Nike Air Max 270", "Size 41 · x2",
                224.0, 1, 224.0, Order.STATUS_SHIPPING));
        allOrders.add(new Order("1008", "12/10/2026 · 19:45", "Nike Free Metcon 5", "Size 40 · x1",
                130.0, 1, 130.0, Order.STATUS_DELIVERED));
        allOrders.add(new Order("0997", "05/10/2026 · 08:20", "Nike Pegasus 41", "Size 42 · x1",
                140.0, 1, 140.0, Order.STATUS_CANCELLED));

        RecyclerView rvOrders = findViewById(R.id.rvOrders);
        rvOrders.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter(new ArrayList<>(allOrders), this::onOrderAction);
        rvOrders.setAdapter(orderAdapter);

        tabs = new TextView[]{
                findViewById(R.id.tabAll),
                findViewById(R.id.tabPending),
                findViewById(R.id.tabShipping),
                findViewById(R.id.tabDelivered),
                findViewById(R.id.tabCancelled)
        };

        final int[] tabStatus = {-1, Order.STATUS_PENDING, Order.STATUS_SHIPPING,
                Order.STATUS_DELIVERED, Order.STATUS_CANCELLED};
        for (int i = 0; i < tabs.length; i++) {
            final int index = i;
            tabs[i].setOnClickListener(v -> selectTab(index, tabStatus[index]));
        }
        selectTab(0, -1);
    }

    private void selectTab(int index, int status) {
        for (int i = 0; i < tabs.length; i++) {
            boolean selected = i == index;
            tabs[i].setBackgroundResource(selected ? R.drawable.bg_chip_selected : R.drawable.bg_chip_unselected);
            tabs[i].setTextColor(ContextCompat.getColor(this, selected ? R.color.white : R.color.text_primary));
        }

        List<Order> filtered = new ArrayList<>();
        for (Order order : allOrders) {
            if (status == -1 || order.getStatus() == status) {
                filtered.add(order);
            }
        }
        orderAdapter.setOrders(filtered);
        tvEmpty.setVisibility(filtered.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private void onOrderAction(Order order) {
        switch (order.getStatus()) {
            case Order.STATUS_SHIPPING:
                Intent intent = new Intent(this, OrderTrackingActivity.class);
                intent.putExtra(OrderTrackingActivity.EXTRA_ORDER_ID, order.getId());
                startActivity(intent);
                break;
            case Order.STATUS_DELIVERED:
                Toast.makeText(this, "Đã thêm " + order.getProductName() + " vào giỏ hàng", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "Chi tiết đơn #" + order.getId(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    static void applySystemBarInsets(View root) {
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
    }
}
