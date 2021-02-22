package com.prinkal.pos.app.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.adapter.CustomPagerAdapter;
import com.prinkal.pos.app.customviews.AutoScrollableView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;

import static com.prinkal.pos.app.activity.BaseActivity.TAG;

public class WalkthroughActivity extends AppCompatActivity {

    private AutoScrollableView autoScrollableView;
    private CustomPagerAdapter mCustomPagerAdapter;
    int res[] = {R.drawable.screen_1, R.drawable.screen_1, R.drawable.screen_1, R.drawable.screen_1, R.drawable.screen_1};
    int heading[] = {R.string.welcome_to_stand_alone_pos, R.string.easily_manage_the_cart, R.string.many_options, R.string.manage_products, R.string.category_management};
    int[] subheading = {R.string.user_can_add_product_to_cart_by_simply_tab_and_by_search, R.string.subheading_cart_wk_msg, R.string.manage_customer_product_categoies_taxes_and_a_lot_more, R.string.simply_manage_the_all_types_of_products, R.string.you_can_simply_add_and_manage_the_categoies};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);
        setSupportActionBar(findViewById(R.id.toolbar));
        autoScrollableView = findViewById(R.id.viewpager);
        mCustomPagerAdapter = new CustomPagerAdapter(this, res, heading, subheading, autoScrollableView);
        autoScrollableView.showBullets();
        autoScrollableView.setAdapter(mCustomPagerAdapter);
    }

    public void clickLeft(View v) {
        if (autoScrollableView.getMyCustomViewPager().getCurrentItem() != 0) {
            autoScrollableView.getMyCustomViewPager().setCurrentItem(autoScrollableView.getMyCustomViewPager().getCurrentItem() - 1);
            autoScrollableView.getMyCustomViewPager().getAdapter().notifyDataSetChanged();
            Log.d(TAG, "onTouch: " + "Left" + autoScrollableView.getMyCustomViewPager().getCurrentItem());
        }
    }

    public void clickRight(View v) {
        if (autoScrollableView.getMyCustomViewPager().getCurrentItem() != res.length) {
            autoScrollableView.getMyCustomViewPager().setCurrentItem(autoScrollableView.getMyCustomViewPager().getCurrentItem() + 1);
            autoScrollableView.getMyCustomViewPager().getAdapter().notifyDataSetChanged();
            Log.d(TAG, "onTouch: " + "Right" + autoScrollableView.getMyCustomViewPager().getCurrentItem());
        }
    }
}