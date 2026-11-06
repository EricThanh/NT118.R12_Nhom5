package com.example.nt118r12.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118r12.R;
import com.example.nt118r12.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductGridAdapter extends RecyclerView.Adapter<ProductGridAdapter.ViewHolder> {
    private final List<Product> originalList;
    private final List<Product> filteredList = new ArrayList<>();
    private final OnProductClickListener listener;

    public interface OnProductClickListener {
        void onProductClick(Product product);
        void onFavoriteClick(Product product, int position);
        void onAddToCartClick(Product product);
    }

    public ProductGridAdapter(List<Product> products, OnProductClickListener listener) {
        this.originalList = products;
        this.listener = listener;
        this.filteredList.addAll(products);
    }

    public void filter(String query) {
        String q = query == null ? "" : query.trim().toLowerCase(Locale.ROOT);
        filteredList.clear();
        for (Product p : originalList) {
            if (q.isEmpty() || p.getName().toLowerCase(Locale.ROOT).contains(q)) {
                filteredList.add(p);
            }
        }
        notifyDataSetChanged();
    }

    public int getFilteredCount() {
        return filteredList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = filteredList.get(position);
        holder.tvName.setText(product.getName());
        holder.tvColors.setText(product.getColorsCount() + " Colors");
        holder.tvPrice.setText(String.format(Locale.US, "$%.2f", product.getPrice()));

        updateFavoriteIcon(holder.ivFavorite, product.isFavorite());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onProductClick(product);
        });

        holder.ivFavorite.setOnClickListener(v -> {
            product.setFavorite(!product.isFavorite());
            updateFavoriteIcon(holder.ivFavorite, product.isFavorite());
            if (listener != null) listener.onFavoriteClick(product, holder.getAdapterPosition());
        });

        holder.btnQuickAdd.setOnClickListener(v -> {
            if (listener != null) listener.onAddToCartClick(product);
        });
    }

    private void updateFavoriteIcon(ImageView ivFavorite, boolean isFav) {
        ivFavorite.setColorFilter(ContextCompat.getColor(ivFavorite.getContext(),
                isFav ? android.R.color.holo_red_dark : R.color.seller_ink));
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumb, ivFavorite;
        TextView tvName, tvColors, tvPrice, btnQuickAdd;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumb = itemView.findViewById(R.id.iv_product_thumb);
            ivFavorite = itemView.findViewById(R.id.iv_favorite);
            tvName = itemView.findViewById(R.id.tv_product_name);
            tvColors = itemView.findViewById(R.id.tv_product_colors);
            tvPrice = itemView.findViewById(R.id.tv_product_price);
            btnQuickAdd = itemView.findViewById(R.id.btn_quick_add);
        }
    }
}
