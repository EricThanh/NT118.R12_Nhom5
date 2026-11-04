package com.example.nt118r12.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118r12.R;
import com.example.nt118r12.models.SellerProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SellerProductAdapter extends RecyclerView.Adapter<SellerProductAdapter.ViewHolder> {
    private final List<SellerProduct> allProducts;
    private final List<SellerProduct> shownProducts = new ArrayList<>();
    private final OnProductActionListener listener;

    public interface OnProductActionListener {
        void onEditClick(SellerProduct product);

        void onDeleteClick(SellerProduct product);
    }

    public SellerProductAdapter(List<SellerProduct> products, OnProductActionListener listener) {
        this.allProducts = products;
        this.listener = listener;
        filter(null, "");
    }

    // status == null nghĩa là hiện tất cả trạng thái
    public void filter(String status, String query) {
        String q = query == null ? "" : query.trim().toLowerCase(Locale.ROOT);
        shownProducts.clear();
        for (SellerProduct p : allProducts) {
            boolean statusOk = status == null || status.equals(p.getStatus());
            boolean nameOk = q.isEmpty() || p.getName().toLowerCase(Locale.ROOT).contains(q);
            if (statusOk && nameOk) {
                shownProducts.add(p);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seller_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SellerProduct product = shownProducts.get(position);
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(String.format(Locale.US, "$%.2f", product.getPrice()));
        holder.tvStatus.setText(product.getStatus());
        holder.tvStock.setText("Stock: " + product.getStock());
        holder.ivEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEditClick(product);
        });
        holder.ivDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteClick(product);
        });
    }

    @Override
    public int getItemCount() {
        return shownProducts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvStatus, tvStock;
        ImageView ivEdit, ivDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_seller_product_name);
            tvPrice = itemView.findViewById(R.id.tv_seller_product_price);
            tvStatus = itemView.findViewById(R.id.tv_seller_product_status);
            tvStock = itemView.findViewById(R.id.tv_seller_product_stock);
            ivEdit = itemView.findViewById(R.id.iv_seller_edit);
            ivDelete = itemView.findViewById(R.id.iv_seller_delete);
        }
    }
}
