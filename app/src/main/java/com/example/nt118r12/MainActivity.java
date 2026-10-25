package com.example.nt118r12;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    private ImageView navHome, navWishlist, navCart, navAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navHome = findViewById(R.id.nav_home);
        navWishlist = findViewById(R.id.nav_wishlist);
        navCart = findViewById(R.id.nav_cart);
        navAccount = findViewById(R.id.nav_account);

        navHome.setOnClickListener(v -> switchFragment(new HomeFragment(), navHome));
        navWishlist.setOnClickListener(v -> switchFragment(new WishlistFragment(), navWishlist));
        navCart.setOnClickListener(v -> switchFragment(new CartFragment(), navCart));
        navAccount.setOnClickListener(v -> switchFragment(new AccountFragment(), navAccount));

        // Load default fragment
        if (savedInstanceState == null) {
            switchFragment(new HomeFragment(), navHome);
        }
    }

    private void switchFragment(Fragment fragment, ImageView selectedNav) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();

        updateBottomNav(selectedNav);
    }

    private void updateBottomNav(ImageView selectedNav) {

        int unselectedColor = ContextCompat.getColor(this, R.color.text_secondary);
        navHome.setImageTintList(ColorStateList.valueOf(unselectedColor));
        navWishlist.setImageTintList(ColorStateList.valueOf(unselectedColor));
        navCart.setImageTintList(ColorStateList.valueOf(unselectedColor));
        navAccount.setImageTintList(ColorStateList.valueOf(unselectedColor));


        int selectedColor = ContextCompat.getColor(this, R.color.primary);
        selectedNav.setImageTintList(ColorStateList.valueOf(selectedColor));
    }
}