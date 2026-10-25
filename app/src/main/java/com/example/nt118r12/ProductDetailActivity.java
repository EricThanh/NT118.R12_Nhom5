package com.example.nt118r12;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.example.nt118r12.adapters.ProductAdapter;
import com.example.nt118r12.models.Product;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    private Product product;
    private View layoutHeader;
    private View layoutBottomAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_product_detail);

        layoutHeader = findViewById(R.id.layout_header);
        layoutBottomAction = findViewById(R.id.layout_bottom_action);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            layoutHeader.setPadding(0, insets.top, 0, 0);
            layoutBottomAction.setPadding(0, 0, 0, insets.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        product = (Product) getIntent().getSerializableExtra("PRODUCT_EXTRA");
        if (product == null) {
            product = new Product("Giày Thể Thao Mẫu A", 250000.0, false);
        }

        bindData();
        setupEvents();
        setupDummyData();
    }

    private void bindData() {
        TextView tvHeaderTitle = findViewById(R.id.tv_header_title);
        TextView tvHeaderPrice = findViewById(R.id.tv_header_price);
        TextView tvProductName = findViewById(R.id.tv_product_name);
        TextView tvDiscountPrice = findViewById(R.id.tv_discount_price);

        tvHeaderTitle.setText(product.getName());
        tvHeaderPrice.setText(String.format("%,.0f đ", product.getPrice()));
        tvProductName.setText(product.getName());
        tvDiscountPrice.setText(String.format("%,.0f đ", product.getPrice()));

        TextView tvOriginalPrice = findViewById(R.id.tv_original_price);
        if (tvOriginalPrice != null) {
            double originalPrice = product.getPrice() / 0.75;
            tvOriginalPrice.setText(String.format("%,.0f đ", originalPrice));

            tvOriginalPrice.setPaintFlags(tvOriginalPrice.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
        }
        ViewPager2 viewPagerImages = findViewById(R.id.view_pager_images);
        viewPagerImages.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                android.widget.ImageView iv = new android.widget.ImageView(parent.getContext());
                iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                iv.setScaleType(android.widget.ImageView.ScaleType.CENTER_CROP);
                return new RecyclerView.ViewHolder(iv) {};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                ((android.widget.ImageView) holder.itemView).setImageResource(R.drawable.placeholder_shoe);
            }

            @Override
            public int getItemCount() {
                return 3;
            }
        });

    }

    private void setupEvents() {
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        findViewById(R.id.btn_add_to_cart).setOnClickListener(v -> showBottomSheet("ADD_CART"));
        findViewById(R.id.btn_buy_now).setOnClickListener(v -> showBottomSheet("BUY_NOW"));
    }

    private void showBottomSheet(String mode) {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_product_options, null);
        dialog.setContentView(view);

        TextView tvOptionPrice = view.findViewById(R.id.tvOptionPrice);
        tvOptionPrice.setText(String.format("%,.0f đ", product.getPrice()));

        int[] sizeIds = {
            R.id.tvSize36, R.id.tvSize37, R.id.tvSize38, R.id.tvSize39,
            R.id.tvSize40, R.id.tvSize41, R.id.tvSize42, R.id.tvSize43, R.id.tvSize44
        };

        List<TextView> tvSizes = new ArrayList<>();
        final String[] selectedSize = {null};
        androidx.appcompat.widget.AppCompatButton btnConfirm = view.findViewById(R.id.btnConfirm);
        btnConfirm.setEnabled(false);
        btnConfirm.setBackgroundResource(R.color.bg_gray_light);

        for (int id : sizeIds) {
            TextView tv = view.findViewById(id);
            if (tv != null) {
                tvSizes.add(tv);
                tv.setOnClickListener(v -> {
                    for (TextView t : tvSizes) {
                        t.setBackgroundResource(R.drawable.bg_chip_unselected);
                    }
                    tv.setBackgroundResource(R.drawable.bg_chip_selected);
                    selectedSize[0] = tv.getText().toString();
                    
                    btnConfirm.setEnabled(true);
                    btnConfirm.setBackgroundResource(R.color.primary);
                });
            }
        }

        TextView tvQuantity = view.findViewById(R.id.tvQuantity);
        final int[] quantity = {1};

        view.findViewById(R.id.btnDecreaseQty).setOnClickListener(v -> {
            if (quantity[0] > 1) {
                quantity[0]--;
                tvQuantity.setText(String.valueOf(quantity[0]));
            }
        });

        view.findViewById(R.id.btnIncreaseQty).setOnClickListener(v -> {
            quantity[0]++;
            tvQuantity.setText(String.valueOf(quantity[0]));
        });

        btnConfirm.setOnClickListener(v -> {
            if (mode.equals("ADD_CART")) {
                Toast.makeText(this, "Đã thêm vào giỏ hàng: size " + selectedSize[0] + ", SL: " + quantity[0], Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                dialog.dismiss();
                startActivity(new Intent(this, CheckoutActivity.class));
            }
        });

        dialog.show();
    }

    private void setupDummyData() {
        // Similar products
        RecyclerView rvSimilar = findViewById(R.id.rv_similar_products);
        rvSimilar.setLayoutManager(new GridLayoutManager(this, 2));
        List<Product> similarProducts = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            similarProducts.add(new Product("Giày Tương Tự " + i, product.getPrice() - 10000, false));
        }
        ProductAdapter adapter = new ProductAdapter(similarProducts, false, p -> {
            Intent intent = new Intent(this, ProductDetailActivity.class);
            intent.putExtra("PRODUCT_EXTRA", p);
            startActivity(intent);
        });
        rvSimilar.setAdapter(adapter);


        RecyclerView rvReviews = findViewById(R.id.rv_reviews);
        rvReviews.setLayoutManager(new LinearLayoutManager(this));
        rvReviews.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
                return new RecyclerView.ViewHolder(v) {};
            }
            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                TextView tvUser = holder.itemView.findViewById(R.id.tv_review_user);
                TextView tvContent = holder.itemView.findViewById(R.id.tv_review_content);
                if (tvUser != null) tvUser.setText("Người dùng " + (position + 1));
                if (tvContent != null) tvContent.setText("Sản phẩm 5 sao");
            }
            @Override
            public int getItemCount() {
                return 3;
            }
        });
    }
}
