package com.example.nt118r12;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class FilterSortBottomSheet {

    private static final float DEFAULT_MIN_PRICE = 50f;
    private static final float DEFAULT_MAX_PRICE = 250f;

    private final Context context;
    private final BottomSheetDialog dialog;
    private final View view;

    private final List<TextView> sortChips = new ArrayList<>();
    private final List<TextView> categoryChips = new ArrayList<>();
    private final List<TextView> sizeChips = new ArrayList<>();
    private final Set<TextView> selectedSizes = new HashSet<>();
    private final List<FrameLayout> swatches = new ArrayList<>();

    private RangeSlider sliderPrice;
    private TextView tvPriceMin, tvPriceMax;
    private AppCompatButton btnApply;

    public static void show(Context context) {
        new FilterSortBottomSheet(context).dialog.show();
    }

    private FilterSortBottomSheet(Context context) {
        this.context = context;
        dialog = new BottomSheetDialog(context);
        view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_filter_sort, null);
        dialog.setContentView(view);

        dialog.setOnShowListener(d -> {
            View sheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (sheet != null) {
                sheet.setBackgroundResource(android.R.color.transparent);
                BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(sheet);
                behavior.setSkipCollapsed(true);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        setupSort();
        setupPrice();
        setupCategories();
        setupSizes();
        setupColors();

        view.findViewById(R.id.tvReset).setOnClickListener(v -> reset());

        btnApply = view.findViewById(R.id.btnApplyFilter);
        btnApply.setOnClickListener(v -> {
            Toast.makeText(context, "Đã áp dụng bộ lọc", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        updateResultCount();
    }

    private void setupSort() {
        int[] ids = {R.id.chipSortPopular, R.id.chipSortBestSeller, R.id.chipSortPriceAsc,
                R.id.chipSortPriceDesc, R.id.chipSortNewest};
        for (int id : ids) {
            TextView chip = view.findViewById(id);
            sortChips.add(chip);
            chip.setOnClickListener(v -> selectSingle(sortChips, chip));
        }
    }

    private void setupPrice() {
        sliderPrice = view.findViewById(R.id.sliderPrice);
        tvPriceMin = view.findViewById(R.id.tvPriceMin);
        tvPriceMax = view.findViewById(R.id.tvPriceMax);
        sliderPrice.addOnChangeListener((slider, value, fromUser) -> {
            List<Float> values = slider.getValues();
            tvPriceMin.setText(formatPrice(values.get(0)));
            tvPriceMax.setText(formatPrice(values.get(1)));
            updateResultCount();
        });
    }

    private void setupCategories() {
        int[] ids = {R.id.chipCatRunning, R.id.chipCatLifestyle, R.id.chipCatBasketball, R.id.chipCatTraining};
        for (int id : ids) {
            TextView chip = view.findViewById(id);
            categoryChips.add(chip);
            chip.setOnClickListener(v -> {
                selectSingle(categoryChips, chip);
                updateResultCount();
            });
        }
    }

    private void setupSizes() {
        int[] ids = {R.id.chipSize39, R.id.chipSize40, R.id.chipSize41, R.id.chipSize42, R.id.chipSize43};
        for (int id : ids) {
            TextView chip = view.findViewById(id);
            sizeChips.add(chip);
            chip.setOnClickListener(v -> {
                if (selectedSizes.contains(chip)) {
                    selectedSizes.remove(chip);
                    setChipSelected(chip, false);
                } else {
                    selectedSizes.add(chip);
                    setChipSelected(chip, true);
                }
                updateResultCount();
            });
        }

        selectedSizes.add(view.findViewById(R.id.chipSize41));
        selectedSizes.add(view.findViewById(R.id.chipSize42));
    }

    private void setupColors() {
        int[] ids = {R.id.swatchBlack, R.id.swatchWhite, R.id.swatchGray, R.id.swatchBlue,
                R.id.swatchRed, R.id.swatchBeige};
        for (int id : ids) {
            FrameLayout swatch = view.findViewById(id);
            swatches.add(swatch);
            swatch.setOnClickListener(v -> selectSwatch(swatch));
        }
    }

    private void selectSwatch(FrameLayout selected) {
        for (FrameLayout swatch : swatches) {
            swatch.setBackgroundResource(swatch == selected
                    ? R.drawable.bg_swatch_ring_selected
                    : R.drawable.bg_swatch_ring_unselected);
        }
        updateResultCount();
    }

    private void reset() {
        selectSingle(sortChips, sortChips.get(0));
        selectSingle(categoryChips, categoryChips.get(0));
        selectedSizes.clear();
        for (TextView chip : sizeChips) {
            setChipSelected(chip, false);
        }
        selectSwatch(swatches.get(0));
        sliderPrice.setValues(DEFAULT_MIN_PRICE, DEFAULT_MAX_PRICE);
        updateResultCount();
    }

    private void selectSingle(List<TextView> group, TextView selected) {
        for (TextView chip : group) {
            setChipSelected(chip, chip == selected);
        }
    }

    private void setChipSelected(TextView chip, boolean selected) {
        chip.setBackgroundResource(selected ? R.drawable.bg_chip_selected : R.drawable.bg_chip_unselected);
        chip.setTextColor(ContextCompat.getColor(context, selected ? R.color.white : R.color.text_primary));
    }

    private void updateResultCount() {
        if (btnApply == null || sliderPrice == null) {
            return;
        }
        List<Float> values = sliderPrice.getValues();
        float range = values.get(1) - values.get(0);
        int count = Math.max(1, Math.round(range / 20f) + selectedSizes.size());
        btnApply.setText("Hiển thị " + count + " kết quả");
    }

    private static String formatPrice(float value) {
        return String.format(Locale.US, "$%.0f", value);
    }
}
