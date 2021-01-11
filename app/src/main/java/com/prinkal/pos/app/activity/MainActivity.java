package com.prinkal.pos.app.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.prinkal.pos.app.R;
import com.prinkal.pos.app.adapter.DrawerAdapter;
import com.prinkal.pos.app.customviews.CustomDialogClass;
import com.prinkal.pos.app.databinding.ActivityMainBinding;
import com.prinkal.pos.app.db.DataBaseController;
import com.prinkal.pos.app.db.entity.Category;
import com.prinkal.pos.app.db.entity.Product;
import com.prinkal.pos.app.fragment.HoldFragment;
import com.prinkal.pos.app.fragment.HomeFragment;
import com.prinkal.pos.app.fragment.MoreFragment;
import com.prinkal.pos.app.fragment.OrdersFragment;
import com.prinkal.pos.app.helper.AppSharedPref;
import com.prinkal.pos.app.helper.Helper;
import com.prinkal.pos.app.helper.ToastHelper;
import com.prinkal.pos.app.interfaces.DataBaseCallBack;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.prinkal.pos.app.constants.ApplicationConstants.IS_DEMO_USER_ENABLED;
import static com.prinkal.pos.app.helper.Helper.DB_NAME;
import static com.prinkal.pos.app.helper.Helper.DB_PATH;

public class MainActivity extends BaseActivity {

    public ActivityMainBinding mMainBinding;
    private ActionBarDrawerToggle mDrawerToggle;
    public List<Product> products;
    private List<Category> categories;
    private DrawerAdapter drawerAdapter;
    private SearchView searchView;
    private long backPressedTime = 0;
    long networkTS = 0;
    private long storedTime;
    private SweetAlertDialog sweetAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initDrawerToggle();
        initBottomNavView();
        loadHomeFragment();
        DataBaseController.getInstance().getCashHistoryByDate(this, Helper.getCurrentDate(), new DataBaseCallBack() {
            @Override
            public void onSuccess(Object responseData, String successMsg) {
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                openingBalance();
            }
        });

        if (IS_DEMO_USER_ENABLED) {
            reminderMsg();
            getCurrentTime();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        loadHomeFragment();
        loadDrawerData();
    }

    private void openingBalance() {
        if (AppSharedPref.getDate(this).isEmpty() || !AppSharedPref.getDate(this).equalsIgnoreCase(Helper.getCurrentDate())) {
            CustomDialogClass customDialogClass = new CustomDialogClass(this);
            customDialogClass.show();
        }
    }

    private void reminderMsg() {
        if (!AppSharedPref.isReminderMsgShown(this, false)) {
            sweetAlert = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE);
            sweetAlert.setTitleText(getString(R.string.warning))
                    .setContentText(getResources().getString(R.string.demo_app_popup))
                    .setConfirmText(getResources().getString(R.string.never))
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            AppSharedPref.setReminderMsgShown(MainActivity.this, true);
                        }
                    })
                    .setCancelText(getResources().getString(R.string.remind_me_later))
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();
            sweetAlert.setCancelable(false);
        }
    }

    private void getCurrentTime() {
        GetNetworkTime getNetworkTime = new GetNetworkTime(this);
        getNetworkTime.execute();
    }

    class GetNetworkTime extends AsyncTask<Void, Void,
            Long> {

        private Context context;

        GetNetworkTime(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoader();
        }

        @Override
        protected Long doInBackground(Void... voids) {
            networkTS = Helper.getCurrentNetworkTime();
            return networkTS;
        }

        @Override
        protected void onPostExecute(Long l) {
            super.onPostExecute(l);
            hideLoader();
            storedTime = AppSharedPref.getTime(context, 0);
//            Log.d(TAG, "onCreate: " + networkTS + "----" + storedTime + "---" + (networkTS - storedTime));
            if (storedTime == 0) {
                AppSharedPref.setTime(context, networkTS);
            } else {
                if ((networkTS - storedTime) >= (60 * 60 * 1000)) {
                    destoryDbForDemoUser();
                }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void destoryDbForDemoUser() {
//        DataBaseController.getInstanse().deleteAllTables(MainActivity.this);
        AppSharedPref.deleteCartData(this);
//        AppSharedPref.deleteSignUpdata(this);
//        AppSharedPref.removeAllPref(this);
        AppSharedPref.setTime(this, 0);
        AppSharedPref.setDate(this, "");
        AppSharedPref.setReminderMsgShown(this, false);
        deleteDB();
        Helper.setDefaultDataBase(this);
    }

    public void deleteDB() {
        File fdelete = new File(DB_PATH + DB_NAME);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                Log.d(TAG, "POSDB: Deleted Successful!");
            } else {
                Log.d(TAG, "POSDB: Deletion Unsuccessful!");
            }
        }
    }

    public void loadDrawerData() {
        if (categories == null)
            categories = new ArrayList<>();
        setCategories();
    }

    public void setCategories() {
        DataBaseController.getInstance().getIncludedCategoryForDrawer(this, new DataBaseCallBack() {
            @Override
            public void onSuccess(Object responseData, String msg) {
                if (!(categories.toString().equalsIgnoreCase(responseData.toString()))) {
                    if (categories.size() > 0)
                        categories.clear();
                    categories.addAll((List<Category>) responseData);
                    if (drawerAdapter == null) {
                        drawerAdapter = new DrawerAdapter(MainActivity.this, categories);
                        mMainBinding.categoryRvDrawer.setAdapter(drawerAdapter);
                    } else {
                        drawerAdapter.notifyDataSetChanged();
                    }
                    mMainBinding.setVisibility(true);
                } else {
                    mMainBinding.setVisibility(false);
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                ToastHelper.showToast(MainActivity.this, errorMsg + "", Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDrawerData();
    }

    private void initDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mMainBinding.drawerLayout
                , R.string.drawer_open, R.string.drawer_close);
        mMainBinding.drawerLayout.addDrawerListener(mDrawerToggle);
    }

    private void initBottomNavView() {
        mMainBinding.bottomNavView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.bottom_nav_item_home:
                                loadHomeFragment();
                                break;
                            case R.id.bottom_nav_item_orders:
                                loadOrdersFragment();
                                break;
                            case R.id.bottom_nav_item_hold:
                                loadHoldFragment();
                                break;
                            case R.id.bottom_nav_item_more:
                                loadMoreFragment();
                                break;
                        }
                        return true;
                    }
                });
        removeBottomNavShiftMode();
    }

    private void loadHomeFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, HomeFragment.newInstance()
                , HomeFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    private void loadOrdersFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, OrdersFragment.newInstance()
                , OrdersFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    private void loadHoldFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, HoldFragment.newInstance()
                , HoldFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    private void loadMoreFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, MoreFragment.newInstance()
                , MoreFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    @SuppressLint("RestrictedApi")
    public void removeBottomNavShiftMode() {
        try {
            BottomNavigationMenuView menuView =
                    (BottomNavigationMenuView) mMainBinding.bottomNavView.getChildAt(0);
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (mMainBinding.drawerLayout != null &&
                mMainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mMainBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else if (!(getSupportFragmentManager().findFragmentById(R.id.main_frame) instanceof HomeFragment)) {
            mMainBinding.bottomNavView.setSelectedItemId(R.id.bottom_nav_item_home);
        } else {
            long t = System.currentTimeMillis();
            if (t - backPressedTime > 2000) { //2 secs
                backPressedTime = t;
                ToastHelper.showToast(this, getResources().getString(R.string.press_back_to_exit), Toast.LENGTH_SHORT);
            } else {
                finish();
            }
        }
    }

    public void showLoader() {
        mMainBinding.loader.setVisibility(View.VISIBLE);
    }

    public void hideLoader() {
        mMainBinding.loader.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            HomeFragment homeFragment = (HomeFragment) mSupportFragmentManager.findFragmentByTag(HomeFragment.class.getSimpleName());
            homeFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}