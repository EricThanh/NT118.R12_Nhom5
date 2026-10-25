package com.example.nt118r12.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nt118r12.R;
import com.example.nt118r12.models.Product;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private boolean isHorizontal;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onAddClick(Product product);
    }

    public ProductAdapter(List<Product> productList, boolean isHorizontal, OnItemClickListener listener) {
        this.productList = productList;
        this.isHorizontal = isHorizontal;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = isHorizontal ? R.layout.item_product_horizontal : R.layout.item_product_vertical;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText("$" + product.getPrice());
        // For demonstration, use placeholder
        if(holder.ivProduct != null) holder.ivProduct.setImageResource(R.layout.item_product_horizontal == R.layout.item_product_horizontal ? R.drawable.placeholder_shoe : R.drawable.placeholder_shoe); // dummy logic

        if (holder.ivAdd != null) {
            holder.ivAdd.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onAddClick(product);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProduct;
        ImageView ivAdd;
        TextView tvName;
        TextView tvPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.ivProduct);
            ivAdd = itemView.findViewById(R.id.ivAdd);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
