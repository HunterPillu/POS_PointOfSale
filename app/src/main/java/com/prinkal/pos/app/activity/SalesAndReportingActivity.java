package com.prinkal.pos.app.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.prinkal.pos.app.MPAndroidChart.InventoryReportFragment;
import com.prinkal.pos.app.R;
import com.prinkal.pos.app.databinding.ActivityInventoryReportBinding;
import com.prinkal.pos.app.fragment.RevenueReportFragment;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class SalesAndReportingActivity extends BaseActivity {

    ActivityInventoryReportBinding binding;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inventory_report);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPagerAdapter =
                new ViewPagerAdapter(
                        getSupportFragmentManager());
        binding.pager.setAdapter(viewPagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.menu_item_search).setVisible(false);
        menu.findItem(R.id.menu_item_scan_barcode).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment;
            if (i == 0) {
                fragment = new RevenueReportFragment();
            } else
                fragment = new InventoryReportFragment();
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0)
                return "Revenue Report";
            else
                return "Inventory Report";
        }
    }
}