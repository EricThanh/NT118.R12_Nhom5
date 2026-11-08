package com.example.nt118r12.seller;

import android.app.Activity;
import android.view.Window;

import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;

import com.example.nt118r12.R;

final class SellerUi {
    private SellerUi() {
    }

    static void applyLightStatusBar(Activity activity) {
        Window window = activity.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(activity, R.color.white));
        WindowCompat.getInsetsController(window, window.getDecorView()).setAppearanceLightStatusBars(true);
    }
}
