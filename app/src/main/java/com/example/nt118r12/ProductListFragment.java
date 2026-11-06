package com.example.nt118r12;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118r12.adapters.ProductGridAdapter;
import com.example.nt118r12.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductListFragment extends Fragment {
    private ProductGridAdapter adapter;
    private TextView tvItemsFound;
    private EditText etSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        tvItemsFound = view.findViewById(R.id.tv_items_found);
        etSearch = view.findViewById(R.id.et_search_products);

        List<Product> products = new ArrayList<>();
        products.add(new Product("Nike Air Max 270", 160.00, 5, false));
        products.add(new Product("Nike React Miler", 130.00, 3, true));
        products.add(new Product("Nike Air Force 1 '07", 110.00, 6, false));
        products.add(new Product("Nike Free Metcon 4", 120.00, 4, false));
        products.add(new Product("Nike Blazer Mid '77", 105.00, 2, false));
        products.add(new Product("Nike Pegasus 40", 140.00, 7, true));
        products.add(new Product("Nike Invincible 3", 180.00, 3, false));
        products.add(new Product("Nike Dunk Low Retro", 115.00, 4, false));

        RecyclerView rvGrid = view.findViewById(R.id.rv_products_grid);
        rvGrid.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        adapter = new ProductGridAdapter(products, new ProductGridAdapter.OnProductClickListener() {
            @Override
            public void onProductClick(Product product) {
                Intent intent = new Intent(requireContext(), ProductDetailActivity.class);
                intent.putExtra(ProductDetailActivity.PRODUCT_EXTRA, product);
                startActivity(intent);
            }

            @Override
            public void onFavoriteClick(Product product, int position) {
                String msg = product.isFavorite() ? "Đã thêm vào yêu thích" : "Đã bỏ khỏi yêu thích";
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAddToCartClick(Product product) {
                Toast.makeText(requireContext(), "Đã thêm " + product.getName() + " vào giỏ hàng", Toast.LENGTH_SHORT).show();
            }
        });
        rvGrid.setAdapter(adapter);
        updateItemsCount();

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
                updateItemsCount();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        view.findViewById(R.id.btn_filter).setOnClickListener(v -> {
            FilterSortBottomSheet.show(requireContext());
        });

        view.findViewById(R.id.layout_sort).setOnClickListener(v ->
                Toast.makeText(requireContext(), "Tính năng sắp xếp sẽ sớm có", Toast.LENGTH_SHORT).show());

        return view;
    }

    private void updateItemsCount() {
        tvItemsFound.setText(adapter.getFilteredCount() + " sản phẩm");
    }
}
