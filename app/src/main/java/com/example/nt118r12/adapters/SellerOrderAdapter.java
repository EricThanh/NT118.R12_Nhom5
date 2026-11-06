package com.example.nt118r12.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118r12.R;
import com.example.nt118r12.models.SellerOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SellerOrderAdapter extends RecyclerView.Adapter<SellerOrderAdapter.ViewHolder> {
    private final List<SellerOrder> allOrders;
    private final List<SellerOrder> shownOrders = new ArrayList<>();
    private final OnOrderActionListener listener;

    public interface OnOrderActionListener {
        void onConfirmClick(SellerOrder order);
        void onRejectClick(SellerOrder order);
        void onOrderClick(SellerOrder order);
    }

    public SellerOrderAdapter(List<SellerOrder> orders, OnOrderActionListener listener) {
        this.allOrders = orders;
        this.listener = listener;
        filter(SellerOrder.STATUS_NEW, "");
    }

    public void filter(String status, String query) {
        String q = query == null ? "" : query.trim().toLowerCase(Locale.ROOT);
        shownOrders.clear();
        for (SellerOrder o : allOrders) {
            boolean statusOk = status == null || status.equalsIgnoreCase(o.getStatus());
            boolean searchOk = q.isEmpty()
                    || o.getOrderId().toLowerCase(Locale.ROOT).contains(q)
                    || o.getBuyerName().toLowerCase(Locale.ROOT).contains(q);
            if (statusOk && searchOk) {
                shownOrders.add(o);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seller_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SellerOrder order = shownOrders.get(position);
        holder.tvOrderId.setText(order.getOrderId());
        holder.tvOrderDate.setText(order.getOrderDate());
        holder.tvBuyerName.setText(order.getBuyerName());
        holder.tvTotalPrice.setText(String.format(Locale.US, "$%.2f", order.getTotalPrice()));

        if (SellerOrder.STATUS_COMPLETE.equals(order.getStatus())) {
            holder.layoutActions.setVisibility(View.GONE);
        } else {
            holder.layoutActions.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onOrderClick(order);
        });

        holder.btnConfirm.setOnClickListener(v -> {
            if (listener != null) listener.onConfirmClick(order);
        });

        holder.btnReject.setOnClickListener(v -> {
            if (listener != null) listener.onRejectClick(order);
        });
    }

    @Override
    public int getItemCount() {
        return shownOrders.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvOrderDate, tvBuyerName, tvTotalPrice;
        TextView btnReject, btnConfirm;
        View layoutActions;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tv_order_id);
            tvOrderDate = itemView.findViewById(R.id.tv_order_date);
            tvBuyerName = itemView.findViewById(R.id.tv_buyer_name);
            tvTotalPrice = itemView.findViewById(R.id.tv_total_price);
            btnReject = itemView.findViewById(R.id.btn_reject_order);
            btnConfirm = itemView.findViewById(R.id.btn_confirm_order);
            layoutActions = itemView.findViewById(R.id.layout_order_actions);
        }
    }
}
