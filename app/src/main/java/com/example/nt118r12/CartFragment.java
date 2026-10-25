package com.example.nt118r12;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118r12.adapters.CartAdapter;
import com.example.nt118r12.models.CartItem;
import com.example.nt118r12.models.Product;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private TextView tvTotalAmount;
    private List<CartItem> cartItemList;
    private CartAdapter cartAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        EditText etVoucher = view.findViewById(R.id.et_voucher);
        AppCompatButton btnApplyVoucher = view.findViewById(R.id.btn_apply_voucher);
        AppCompatButton btnCheckout = view.findViewById(R.id.btn_checkout);
        tvTotalAmount = view.findViewById(R.id.tv_total_amount);
        RecyclerView rvCart = view.findViewById(R.id.rv_cart);


        cartItemList = new ArrayList<>();
        cartItemList.add(new CartItem(new Product("Nike React Miler", 170.0, false), 1));
        cartItemList.add(new CartItem(new Product("Nike Air Max 270", 112.0, false), 2));

        cartAdapter = new CartAdapter(cartItemList, this::calculateTotal);
        rvCart.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCart.setAdapter(cartAdapter);

        calculateTotal();

        etVoucher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    btnApplyVoucher.setEnabled(true);
                    btnApplyVoucher.setBackgroundResource(R.drawable.bg_btn_primary);
                } else {
                    btnApplyVoucher.setEnabled(false);
                    btnApplyVoucher.setBackgroundResource(R.drawable.bg_btn_disabled);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnCheckout.setOnClickListener(v -> {

        });

        return view;
    }

    private void calculateTotal() {
        double total = 0;
        for (CartItem item : cartItemList) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        tvTotalAmount.setText("$" + String.format("%.2f", total));
    }
}
