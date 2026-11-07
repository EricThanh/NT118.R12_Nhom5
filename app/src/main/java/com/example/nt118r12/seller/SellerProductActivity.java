package com.example.nt118r12.seller;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118r12.R;
import com.example.nt118r12.adapters.SellerProductAdapter;
import com.example.nt118r12.models.SellerProduct;

import java.util.ArrayList;
import java.util.List;

public class SellerProductActivity extends AppCompatActivity {
    private SellerProductAdapter adapter;
    private EditText etSearch;
    private TextView[] tabs;
    private String[] tabStatuses;
    private String currentStatus = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_product);
        SellerUi.applyLightStatusBar(this);

        SellerNavHelper.setup(this, R.id.nav_cart);

        List<SellerProduct> products = new ArrayList<>();
        products.add(new SellerProduct("Nike React Miler", 170.0, SellerProduct.STATUS_ACTIVE, 12));
        products.add(new SellerProduct("Nike Air Max 270", 112.0, SellerProduct.STATUS_ACTIVE, 8));
        products.add(new SellerProduct("Nike Air Force 1", 120.0, SellerProduct.STATUS_OUT_OF_STOCK, 0));
        products.add(new SellerProduct("Nike Free Metcon 3", 160.0, SellerProduct.STATUS_HIDDEN, 5));

        adapter = new SellerProductAdapter(products, new SellerProductAdapter.OnProductActionListener() {
            @Override
            public void onEditClick(SellerProduct product) {
                startActivity(new Intent(SellerProductActivity.this, AddProductActivity.class));
            }

            @Override
            public void onDeleteClick(SellerProduct product) {
                products.remove(product);
                applyFilter();
            }
        });
        RecyclerView rv = findViewById(R.id.rv_seller_products);
        rv.setAdapter(adapter);

        findViewById(R.id.btn_add_product).setOnClickListener(v ->
                startActivity(new Intent(this, AddProductActivity.class)));
        findViewById(R.id.btn_filter).setOnClickListener(v ->
                Toast.makeText(this, "Bộ lọc nâng cao chưa có", Toast.LENGTH_SHORT).show());

        etSearch = findViewById(R.id.et_search_products);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applyFilter();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        tabs = new TextView[]{
                findViewById(R.id.tab_all), findViewById(R.id.tab_active),
                findViewById(R.id.tab_out_of_stock), findViewById(R.id.tab_hidden)};
        tabStatuses = new String[]{null, SellerProduct.STATUS_ACTIVE,
                SellerProduct.STATUS_OUT_OF_STOCK, SellerProduct.STATUS_HIDDEN};
        for (int i = 0; i < tabs.length; i++) {
            final int index = i;
            tabs[i].setOnClickListener(v -> selectTab(index));
        }
        selectTab(0);
    }

    private void selectTab(int selected) {
        currentStatus = tabStatuses[selected];
        for (int i = 0; i < tabs.length; i++) {
            boolean isSelected = i == selected;
            tabs[i].setBackgroundResource(isSelected ? R.drawable.seller_bg_tab_selected : R.drawable.seller_bg_tab);
            tabs[i].setTextColor(ContextCompat.getColor(this, isSelected ? R.color.white : R.color.seller_gray_text));
            tabs[i].setTypeface(ResourcesCompat.getFont(this,
                    isSelected ? R.font.montserrat_semibold : R.font.montserrat_bold));
        }
        applyFilter();
    }

    private void applyFilter() {
        adapter.filter(currentStatus, etSearch.getText().toString());
    }
}
