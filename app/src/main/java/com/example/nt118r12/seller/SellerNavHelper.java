package com.example.nt118r12.seller;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.nt118r12.R;

// Dùng lại layout_bottom_nav của khách hàng: home -> Homepage, heart -> Dashboard,
// bag -> Products, user -> Account (chưa có màn).
final class SellerNavHelper {
    private SellerNavHelper() {
    }

    static void setup(Activity activity, int selectedNavId) {
        int[] navIds = {R.id.nav_home, R.id.nav_wishlist, R.id.nav_cart, R.id.nav_account};
        int selectedColor = ContextCompat.getColor(activity, R.color.seller_ink);
        int unselectedColor = ContextCompat.getColor(activity, R.color.seller_gray_text);

        for (int id : navIds) {
            ImageView nav = activity.findViewById(id);
            nav.setImageTintList(ColorStateList.valueOf(id == selectedNavId ? selectedColor : unselectedColor));
            nav.setOnClickListener(v -> {
                if (id == selectedNavId) return;
                Class<? extends Activity> target = targetFor(id);
                if (target == null) {
                    Toast.makeText(activity, "Chưa có màn này", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(activity, target);
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
        return null;
    }
}
