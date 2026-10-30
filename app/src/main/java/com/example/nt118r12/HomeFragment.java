package com.example.nt118r12;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import com.example.nt118r12.adapters.ProductAdapter;
import com.example.nt118r12.models.Product;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView rvNewProducts = view.findViewById(R.id.rv_new_products);

        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Nike React Miler", 170.0, false));
        productList.add(new Product("Nike Air Max 270", 112.0, true));

        ProductAdapter productAdapter = new ProductAdapter(productList, true, product -> {
            Intent intent = new Intent(getContext(), com.example.nt118r12.ProductDetailActivity.class);
            intent.putExtra("PRODUCT_EXTRA", product);
            startActivity(intent);
        });
        rvNewProducts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvNewProducts.setAdapter(productAdapter);

        view.findViewById(R.id.iv_filter).setOnClickListener(v -> FilterSortBottomSheet.show(requireContext()));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View headerContainer = view.findViewById(R.id.header_container);

        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            int topInset = insets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout()).top;

            int extraPaddingPx = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    16,
                    getResources().getDisplayMetrics()
            );

            headerContainer.setPadding(
                    headerContainer.getPaddingLeft(),
                    topInset + extraPaddingPx,
                    headerContainer.getPaddingRight(),
                    headerContainer.getPaddingBottom()
            );

            return insets;
        });
    }
}
