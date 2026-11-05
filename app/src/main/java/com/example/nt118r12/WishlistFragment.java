package com.example.nt118r12;

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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WishlistFragment extends Fragment {

    private final List<WishlistItem> allItems = new ArrayList<>();
    private final List<WishlistItem> shownItems = new ArrayList<>();
    private WishlistAdapter adapter;
    private TextView tvEmpty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wishlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View header = view.findViewById(R.id.header_wishlist);
        final int headerPadTop = header.getPaddingTop();
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            Insets bars = windowInsets.getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            header.setPadding(header.getPaddingLeft(), headerPadTop + bars.top,
                    header.getPaddingRight(), header.getPaddingBottom());
            return windowInsets;
        });
        ViewCompat.requestApplyInsets(view);

        view.findViewById(R.id.btn_back).setOnClickListener(
                v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());

        tvEmpty = view.findViewById(R.id.tv_empty);
        RecyclerView rv = view.findViewById(R.id.rv_wishlist);
        EditText etSearch = view.findViewById(R.id.et_search);

        allItems.add(new WishlistItem("Nike Air Max 270", 112.00));
        allItems.add(new WishlistItem("Nike React Miler", 170.00));
        allItems.add(new WishlistItem("Nike Air Force 1", 120.00));
        shownItems.addAll(allItems);

        adapter = new WishlistAdapter(shownItems);
        rv.setAdapter(adapter);
        updateEmpty();

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        view.findViewById(R.id.btn_clear_all).setOnClickListener(v -> {
            allItems.clear();
            shownItems.clear();
            adapter.notifyDataSetChanged();
            updateEmpty();
        });

        view.findViewById(R.id.btn_add_all_to_cart).setOnClickListener(v ->
                Toast.makeText(requireContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show());
    }

    private void filter(String keyword) {
        String key = keyword.trim().toLowerCase(Locale.getDefault());
        shownItems.clear();
        for (WishlistItem item : allItems) {
            if (item.name.toLowerCase(Locale.getDefault()).contains(key)) {
                shownItems.add(item);
            }
        }
        adapter.notifyDataSetChanged();
        updateEmpty();
    }

    private void updateEmpty() {
        tvEmpty.setVisibility(shownItems.isEmpty() ? View.VISIBLE : View.GONE);
    }

    static class WishlistItem {
        final String name;
        final double price;
        int quantity = 1;

        WishlistItem(String name, double price) {
            this.name = name;
            this.price = price;
        }
    }

    static class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.Holder> {

        private final List<WishlistItem> items;

        WishlistAdapter(List<WishlistItem> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_wishlist_product, parent, false);
            return new Holder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder h, int position) {
            WishlistItem item = items.get(position);
            h.tvName.setText(item.name);
            h.tvPrice.setText(String.format(Locale.US, "$%.2f", item.price));
            h.tvQuantity.setText(String.valueOf(item.quantity));

            h.tvPlus.setOnClickListener(v -> {
                int pos = h.getBindingAdapterPosition();
                if (pos == RecyclerView.NO_POSITION) return;
                items.get(pos).quantity++;
                notifyItemChanged(pos);
            });

            h.tvMinus.setOnClickListener(v -> {
                int pos = h.getBindingAdapterPosition();
                if (pos == RecyclerView.NO_POSITION) return;
                WishlistItem current = items.get(pos);
                if (current.quantity > 1) {
                    current.quantity--;
                    notifyItemChanged(pos);
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        static class Holder extends RecyclerView.ViewHolder {
            final TextView tvName;
            final TextView tvPrice;
            final TextView tvQuantity;
            final TextView tvMinus;
            final TextView tvPlus;

            Holder(@NonNull View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tv_name);
                tvPrice = itemView.findViewById(R.id.tv_price);
                tvQuantity = itemView.findViewById(R.id.tv_quantity);
                tvMinus = itemView.findViewById(R.id.tv_minus);
                tvPlus = itemView.findViewById(R.id.tv_plus);
            }
        }
    }
}