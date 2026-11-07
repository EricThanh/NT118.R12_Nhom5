package com.example.nt118r12.seller;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.example.nt118r12.R;

final class SellerNavHelper {
    private SellerNavHelper() {
    }

    static void setup(Activity activity, int selectedNavId) {
        int[] navIds = {R.id.nav_home, R.id.nav_wishlist, R.id.nav_cart, R.id.nav_account};
        int[] icons = {R.drawable.ic_nav_home, R.drawable.ic_nav_dashboard,
                R.drawable.ic_nav_products, R.drawable.ic_nav_orders};
        int selectedColor = ContextCompat.getColor(activity, R.color.seller_ink);
        int unselectedColor = ContextCompat.getColor(activity, R.color.seller_gray_text);

        for (int i = 0; i < navIds.length; i++) {
            final int id = navIds[i];
            ImageView nav = activity.findViewById(id);
            nav.setImageResource(icons[i]);
            nav.setImageTintList(ColorStateList.valueOf(id == selectedNavId ? selectedColor : unselectedColor));
            nav.setOnClickListener(v -> {
                if (id == selectedNavId) return;
                Intent intent = new Intent(activity, targetFor(id));
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                activity.startActivity(intent);
                activity.finish();
                activity.overridePendingTransition(0, 0);
            });
        }
    }

    private static Class<? extends Activity> targetFor(int navId) {
        if (navId == R.id.nav_home) return SellerHomepageActivity.class;
        if (navId == R.id.nav_wishlist) return SellerDashboardActivity.class;
        if (navId == R.id.nav_cart) return SellerProductActivity.class;
        return SellerOrdersActivity.class;
    }
}