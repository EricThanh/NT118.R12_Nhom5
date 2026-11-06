package com.example.nt118r12.seller;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118r12.R;
import com.example.nt118r12.adapters.SellerOrderAdapter;
import com.example.nt118r12.models.SellerOrder;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class SellerOrdersActivity extends AppCompatActivity {
    private SellerOrderAdapter adapter;
    private EditText etSearch;
    private TabLayout tabLayout;

    private final String[] tabLabels = new String[]{
            "Mới (3)",
            "Chờ giao (2)",
            "Đang giao",
            "Hoàn thành"
    };

    private final String[] tabStatuses = new String[]{
            SellerOrder.STATUS_NEW,
            SellerOrder.STATUS_TO_SHIP,
            SellerOrder.STATUS_SHIPPING,
            SellerOrder.STATUS_COMPLETE
    };

    private String currentStatus = SellerOrder.STATUS_NEW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_orders);
        SellerUi.applyLightStatusBar(this);

        SellerNavHelper.setup(this, R.id.nav_wishlist);

        List<SellerOrder> orders = new ArrayList<>();
        orders.add(new SellerOrder("#1024", "12/10/2023, 14:30", "Alex Johnson", 282.00, SellerOrder.STATUS_NEW));
        orders.add(new SellerOrder("#1025", "12/10/2023, 11:15", "Sophia Miller", 112.50, SellerOrder.STATUS_NEW));
        orders.add(new SellerOrder("#1021", "11/10/2023, 16:45", "David Brown", 170.00, SellerOrder.STATUS_TO_SHIP));
        orders.add(new SellerOrder("#1019", "10/10/2023, 09:20", "Emma Wilson", 340.00, SellerOrder.STATUS_SHIPPING));
        orders.add(new SellerOrder("#1015", "08/10/2023, 13:00", "Michael Clark", 120.00, SellerOrder.STATUS_COMPLETE));
        orders.add(new SellerOrder("#1012", "06/10/2023, 18:10", "Olivia Davis", 160.00, SellerOrder.STATUS_COMPLETE));

        adapter = new SellerOrderAdapter(orders, new SellerOrderAdapter.OnOrderActionListener() {
            @Override
            public void onConfirmClick(SellerOrder order) {
                if (SellerOrder.STATUS_NEW.equals(order.getStatus())) {
                    order.setStatus(SellerOrder.STATUS_TO_SHIP);
                } else if (SellerOrder.STATUS_TO_SHIP.equals(order.getStatus())) {
                    order.setStatus(SellerOrder.STATUS_SHIPPING);
                } else if (SellerOrder.STATUS_SHIPPING.equals(order.getStatus())) {
                    order.setStatus(SellerOrder.STATUS_COMPLETE);
                }
                Toast.makeText(SellerOrdersActivity.this, "Đơn " + order.getOrderId()
                        + " đã chuyển sang " + getStatusLabel(order.getStatus()), Toast.LENGTH_SHORT).show();
                applyFilter();
            }

            @Override
            public void onRejectClick(SellerOrder order) {
                orders.remove(order);
                Toast.makeText(SellerOrdersActivity.this, "Đã từ chối đơn " + order.getOrderId(), Toast.LENGTH_SHORT).show();
                applyFilter();
            }

            @Override
            public void onOrderClick(SellerOrder order) {
                Intent intent = new Intent(SellerOrdersActivity.this, OrderDetailsActivity.class);
                intent.putExtra(OrderDetailsActivity.EXTRA_ORDER, order);
                startActivity(intent);
            }
        });

        RecyclerView rv = findViewById(R.id.rv_seller_orders);
        rv.setAdapter(adapter);

        tabLayout = findViewById(R.id.tab_layout_orders);
        for (String label : tabLabels) {
            tabLayout.addTab(tabLayout.newTab().setText(label));
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentStatus = tabStatuses[tab.getPosition()];
                applyFilter();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        etSearch = findViewById(R.id.et_search_orders);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applyFilter();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        applyFilter();
    }

    private void applyFilter() {
        adapter.filter(currentStatus, etSearch.getText().toString());
    }

    private String getStatusLabel(String status) {
        if (SellerOrder.STATUS_TO_SHIP.equals(status)) return "chờ giao hàng";
        if (SellerOrder.STATUS_SHIPPING.equals(status)) return "đang giao hàng";
        if (SellerOrder.STATUS_COMPLETE.equals(status)) return "hoàn thành";
        return "chờ xác nhận";
    }
}
