package com.prinkal.pos.app.handlers;

import android.content.Context;
import android.view.Gravity;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.activity.MainActivity;
import com.prinkal.pos.app.db.entity.Category;
import com.prinkal.pos.app.fragment.HomeFragment;

public class MainActivityHandler {

    private Context context;

    public MainActivityHandler(Context context) {
        this.context = context;
    }

    public void onClickCategory(Category category) {
        ((MainActivity) context).mMainBinding.drawerLayout.closeDrawer(Gravity.START);
        HomeFragment homeFragment = (HomeFragment) ((MainActivity) context).mSupportFragmentManager.findFragmentByTag(HomeFragment.class.getSimpleName());
        if (homeFragment == null)
            homeFragment = new HomeFragment();
        if (!(((MainActivity) context).mSupportFragmentManager.findFragmentById(R.id.main_frame) instanceof HomeFragment)) {
            ((MainActivity) context).mMainBinding.bottomNavView.setSelectedItemId(R.id.bottom_nav_item_home);
            ((MainActivity) context).mSupportFragmentManager.popBackStack();
        }
        homeFragment.showCategorySelectedProducts(category.getCId() + "");
    }
}
