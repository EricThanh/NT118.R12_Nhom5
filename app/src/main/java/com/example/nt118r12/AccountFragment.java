package com.example.nt118r12;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class AccountFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        final int rootPadTop = view.getPaddingTop();
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            Insets bars = windowInsets.getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            v.setPadding(v.getPaddingLeft(), rootPadTop + bars.top,
                    v.getPaddingRight(), v.getPaddingBottom());
            return windowInsets;
        });
        ViewCompat.requestApplyInsets(view);

        view.findViewById(R.id.menu_orders).setOnClickListener(v ->
                startActivity(new Intent(getActivity(), CustomerOrdersActivity.class)));
        view.findViewById(R.id.menu_address).setOnClickListener(v ->
                startActivity(new Intent(getActivity(), DeliveryAddressActivity.class)));

        AppCompatButton btnLogout = view.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        view.findViewById(R.id.menu_my_shop).setOnClickListener(v ->
                startActivity(new Intent(getActivity(), com.example.nt118r12.seller.SellerHomepageActivity.class)));

        return view;
    }
}