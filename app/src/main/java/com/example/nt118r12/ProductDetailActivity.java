package com.example.nt118r12;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.nt118r12.models.Product;

import java.util.Arrays;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {
    public static final String PRODUCT_EXTRA = "PRODUCT_EXTRA";

    private Product product;
    private int quantity = 1;
    private boolean isDescriptionExpanded = false;
    private String selectedColorName = "Trắng / Xanh dương";
    private String selectedSize = "41";

    private TextView tvProductName, tvProductPrice, tvSelectedColor, tvDescription, tvReadMore, tvQuantity, btnAddToCart;
    private ImageView btnFavorite;
    private ViewPager2 vpSlider;
    private TextView tvSliderIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        product = (Product) getIntent().getSerializableExtra(PRODUCT_EXTRA);
        if (product == null) {
            product = new Product("Nike Air Max 270", 4000000.0, true);
        }

        initViews();
        setupImageSlider();
        setupColorSwatches();
        setupSizeChips();
        setupDescriptionToggle();
        setupQuantityControls();
    }

    private void initViews() {
        tvProductName = findViewById(R.id.tv_product_name);
        tvProductPrice = findViewById(R.id.tv_product_price);
        tvSelectedColor = findViewById(R.id.tv_selected_color);
        tvDescription = findViewById(R.id.tv_description);
        tvReadMore = findViewById(R.id.tv_read_more);
        tvQuantity = findViewById(R.id.tv_quantity);
        btnAddToCart = findViewById(R.id.btn_add_to_cart);
        btnFavorite = findViewById(R.id.btn_favorite);
        vpSlider = findViewById(R.id.vp_product_slider);
        tvSliderIndicator = findViewById(R.id.tv_slider_indicator);

        tvProductName.setText(product.getName());
        tvProductPrice.setText(MoneyUtil.vnd(product.getPrice()));

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        updateFavoriteIcon(product.isFavorite());
        btnFavorite.setOnClickListener(v -> {
            product.setFavorite(!product.isFavorite());
            updateFavoriteIcon(product.isFavorite());
            Toast.makeText(this, product.isFavorite() ? "Đã thêm vào yêu thích" : "Đã bỏ khỏi yêu thích", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.tv_size_guide).setOnClickListener(v ->
                Toast.makeText(this, "Bảng quy đổi kích cỡ giày EU", Toast.LENGTH_SHORT).show());

        findViewById(R.id.tv_see_all_reviews).setOnClickListener(v ->
                Toast.makeText(this, "Đang mở danh sách đánh giá", Toast.LENGTH_SHORT).show());
    }

    private void updateFavoriteIcon(boolean isFav) {
        btnFavorite.setColorFilter(ContextCompat.getColor(this,
                isFav ? android.R.color.holo_red_dark : R.color.seller_ink));
    }

    private void setupImageSlider() {
        List<Integer> dummyImages = Arrays.asList(
                R.drawable.placeholder_shoe,
                R.drawable.placeholder_shoe,
                R.drawable.placeholder_shoe
        );

        vpSlider.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                ImageView iv = new ImageView(parent.getContext());
                iv.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                iv.setPadding(32, 32, 32, 32);
                return new RecyclerView.ViewHolder(iv) {};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                ((ImageView) holder.itemView).setImageResource(dummyImages.get(position));
            }

            @Override
            public int getItemCount() {
                return dummyImages.size();
            }
        });

        vpSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tvSliderIndicator.setText((position + 1) + "/" + dummyImages.size());
            }
        });
    }

    private static class ColorItem {
        final String name;
        final int color;
        ColorItem(String name, int color) { this.name = name; this.color = color; }
    }

    private void setupColorSwatches() {
        RecyclerView rvColors = findViewById(R.id.rv_color_swatches);
        rvColors.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        List<ColorItem> colors = Arrays.asList(
                new ColorItem("Trắng / Xanh dương", Color.parseColor("#EBF3FC")),
                new ColorItem("Đen Obsidian", Color.parseColor("#1F1F1F")),
                new ColorItem("Đỏ Crimson", Color.parseColor("#E53935")),
                new ColorItem("Xanh ô liu", Color.parseColor("#708238"))
        );

        final int[] selectedIndex = {0};
        selectedColorName = colors.get(0).name;
        tvSelectedColor.setText("Màu: " + selectedColorName);

        rvColors.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color_swatch, parent, false);
                return new RecyclerView.ViewHolder(v) {};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                View ring = holder.itemView.findViewById(R.id.view_swatch_ring);
                View colorView = holder.itemView.findViewById(R.id.view_swatch_color);

                ColorItem item = colors.get(position);

                GradientDrawable bg = new GradientDrawable();
                bg.setShape(GradientDrawable.OVAL);
                bg.setColor(item.color);
                bg.setStroke(2, ContextCompat.getColor(ProductDetailActivity.this, R.color.seller_border));
                colorView.setBackground(bg);

                ring.setVisibility(position == selectedIndex[0] ? View.VISIBLE : View.GONE);

                holder.itemView.setOnClickListener(v -> {
                    int prev = selectedIndex[0];
                    selectedIndex[0] = holder.getAdapterPosition();
                    selectedColorName = item.name;
                    tvSelectedColor.setText("Màu: " + selectedColorName);
                    notifyItemChanged(prev);
                    notifyItemChanged(selectedIndex[0]);
                });
            }

            @Override
            public int getItemCount() {
                return colors.size();
            }
        });
    }

    private void setupSizeChips() {
        RecyclerView rvSizes = findViewById(R.id.rv_sizes);
        rvSizes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        List<String> sizes = Arrays.asList("38", "39", "40", "41", "42", "43", "44");
        final int[] selectedSizeIndex = {3};

        rvSizes.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_size_chip, parent, false);
                return new RecyclerView.ViewHolder(v) {};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                TextView tvChip = (TextView) holder.itemView;
                String size = sizes.get(position);
                tvChip.setText(size);

                boolean isSelected = position == selectedSizeIndex[0];
                tvChip.setBackgroundResource(isSelected ? R.drawable.seller_bg_ink_r8 : R.drawable.seller_bg_btn_outlined);
                tvChip.setTextColor(ContextCompat.getColor(ProductDetailActivity.this,
                        isSelected ? R.color.white : R.color.seller_ink));

                holder.itemView.setOnClickListener(v -> {
                    int prev = selectedSizeIndex[0];
                    selectedSizeIndex[0] = holder.getAdapterPosition();
                    selectedSize = size;
                    notifyItemChanged(prev);
                    notifyItemChanged(selectedSizeIndex[0]);
                });
            }

            @Override
            public int getItemCount() {
                return sizes.size();
            }
        });
    }

    private void setupDescriptionToggle() {
        tvReadMore.setOnClickListener(v -> {
            isDescriptionExpanded = !isDescriptionExpanded;
            tvDescription.setMaxLines(isDescriptionExpanded ? Integer.MAX_VALUE : 3);
            tvReadMore.setText(isDescriptionExpanded ? "Thu gọn" : "Xem thêm");
        });
    }

    private void setupQuantityControls() {
        updateBottomBarPrice();

        findViewById(R.id.btn_decrease_qty).setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                tvQuantity.setText(String.valueOf(quantity));
                updateBottomBarPrice();
            }
        });

        findViewById(R.id.btn_increase_qty).setOnClickListener(v -> {
            quantity++;
            tvQuantity.setText(String.valueOf(quantity));
            updateBottomBarPrice();
        });

        btnAddToCart.setOnClickListener(v -> {
            String summary = "Đã thêm " + quantity + " x " + product.getName()
                    + " (" + selectedColorName + ", cỡ " + selectedSize + ") vào giỏ hàng";
            Toast.makeText(this, summary, Toast.LENGTH_LONG).show();
        });
    }

    private void updateBottomBarPrice() {
        double total = product.getPrice() * quantity;
        btnAddToCart.setText("Thêm vào giỏ - " + MoneyUtil.vnd(total));
    }
}
