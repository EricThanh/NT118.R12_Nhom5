package com.example.nt118r12.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118r12.R;
import com.example.nt118r12.models.Order;

import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    public interface OnOrderActionListener {
        void onAction(Order order);
    }

    private List<Order> orders;
    private final OnOrderActionListener listener;

    public OrderAdapter(List<Order> orders, OnOrderActionListener listener) {
        this.orders = orders;
        this.listener = listener;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_customer_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);

        holder.tvOrderId.setText("Đơn #" + order.getId());
        holder.tvOrderDate.setText(order.getDate());
        holder.tvProductName.setText(order.getProductName());
        holder.tvProductMeta.setText(order.getProductMeta());
        holder.tvProductPrice.setText(formatPrice(order.getProductPrice()));
        holder.tvTotal.setText(formatPrice(order.getTotal()));

        if (order.getItemCount() > 1) {
            holder.tvMoreItems.setVisibility(View.VISIBLE);
            holder.tvMoreItems.setText("+" + (order.getItemCount() - 1) + " sản phẩm khác");
        } else {
            holder.tvMoreItems.setVisibility(View.GONE);
        }

        int statusBg;
        int statusColor;
        String statusText;
        String actionText;
        boolean primaryAction;

        switch (order.getStatus()) {
            case Order.STATUS_SHIPPING:
                statusBg = R.drawable.bg_status_shipping;
                statusColor = R.color.primary;
                statusText = "Đang giao";
                actionText = "Theo dõi";
                primaryAction = true;
                break;
            case Order.STATUS_DELIVERED:
                statusBg = R.drawable.bg_status_delivered;
                statusColor = R.color.success;
                statusText = "Đã giao";
                actionText = "Mua lại";
                primaryAction = true;
                break;
            case Order.STATUS_CANCELLED:
                statusBg = R.drawable.bg_status_cancelled;
                statusColor = R.color.error;
                statusText = "Đã huỷ";
                actionText = "Xem chi tiết";
                primaryAction = false;
                break;
            case Order.STATUS_PENDING:
            default:
                statusBg = R.drawable.bg_status_pending;
                statusColor = R.color.warning;
                statusText = "Chờ xác nhận";
                actionText = "Xem chi tiết";
                primaryAction = false;
                break;
        }

        holder.tvStatus.setBackgroundResource(statusBg);
        holder.tvStatus.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), statusColor));
        holder.tvStatus.setText(statusText);

        holder.btnAction.setText(actionText);
        if (primaryAction) {
            holder.btnAction.setBackgroundResource(R.drawable.bg_btn_primary);
            holder.btnAction.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
        } else {
            holder.btnAction.setBackgroundResource(R.drawable.bg_btn_outline_primary);
            holder.btnAction.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.primary));
        }

        holder.btnAction.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAction(order);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders == null ? 0 : orders.size();
    }

    private static String formatPrice(double price) {
        return String.format(Locale.US, "$%.2f", price);
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvOrderDate, tvStatus, tvProductName, tvProductMeta,
                tvProductPrice, tvMoreItems, tvTotal;
        AppCompatButton btnAction;

        OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductMeta = itemView.findViewById(R.id.tvProductMeta);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvMoreItems = itemView.findViewById(R.id.tvMoreItems);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            btnAction = itemView.findViewById(R.id.btnAction);
        }
    }
}
