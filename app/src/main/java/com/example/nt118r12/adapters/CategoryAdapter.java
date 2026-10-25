package com.example.nt118r12.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nt118r12.R;
import com.example.nt118r12.models.Category;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> categoryList;

    public CategoryAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.tvName.setText(category.getName());
        holder.ivIcon.setImageResource(category.getIconResId());
    }

    @Override
    public int getItemCount() {
        return categoryList == null ? 0 : categoryList.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
