package com.example.nt118r12.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118r12.R;
import com.example.nt118r12.models.OrderItem;

import java.util.List;
import java.util.Locale;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {
    private final List<OrderItem> items;

    public OrderItemAdapter(List<OrderItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderItem item = items.get(position);
        holder.tvName.setText(item.getProductName());
        holder.tvSize.setText("Kích cỡ: " + item.getSize());
        holder.tvPrice.setText(String.format(Locale.US, "$%.2f", item.getPrice()));
        holder.tvQuantity.setText("x" + item.getQuantity());
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvSize, tvPrice, tvQuantity;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_product_name);
            tvSize = itemView.findViewById(R.id.tv_product_size);
            tvPrice = itemView.findViewById(R.id.tv_product_price);
            tvQuantity = itemView.findViewById(R.id.tv_product_quantity);
        }
    }
}
