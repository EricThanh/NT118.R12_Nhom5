package com.example.nt118r12.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nt118r12.R;
import com.example.nt118r12.models.CartItem;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItem> cartItemList;
    private OnQuantityChangeListener quantityChangeListener;

    public interface OnQuantityChangeListener {
        void onQuantityChanged();
    }

    public CartAdapter(List<CartItem> cartItemList, OnQuantityChangeListener listener) {
        this.cartItemList = cartItemList;
        this.quantityChangeListener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItemList.get(position);
        holder.tvName.setText(item.getProduct().getName());
        holder.tvPrice.setText("$" + item.getProduct().getPrice());
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));

        holder.ivPlus.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            notifyItemChanged(position);
            if (quantityChangeListener != null) {
                quantityChangeListener.onQuantityChanged();
            }
        });

        holder.ivMinus.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                notifyItemChanged(position);
                if (quantityChangeListener != null) {
                    quantityChangeListener.onQuantityChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList == null ? 0 : cartItemList.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProduct, ivPlus, ivMinus;
        TextView tvName, tvPrice, tvQuantity;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.ivProduct);
            ivPlus = itemView.findViewById(R.id.ivPlus);
            ivMinus = itemView.findViewById(R.id.ivMinus);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
        }
    }
}
