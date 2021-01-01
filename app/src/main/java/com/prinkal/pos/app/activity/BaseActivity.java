package com.prinkal.pos.app.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.db.AppDatabase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.prinkal.pos.app.db.AppDatabase.destroyDbInstance;
import static com.prinkal.pos.app.db.AppDatabase.getAppDatabase;

public abstract class BaseActivity extends AppCompatActivity {
    @SuppressWarnings("unused")
    public static final String TAG = "BaseActivity";
    public SweetAlertDialog mSweetAlertDialog;
    public FragmentManager mSupportFragmentManager;
    public AppDatabase db;
    public static Context context;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSupportFragmentManager = getSupportFragmentManager();
        db = getDb();
        context = this;
        showBackButton(true);
    }

    public AppDatabase getDb() {
        return getAppDatabase(this);
    }

    public static Context getContext() {
        return context;
    }

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    protected void showBackButton(boolean show) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(show);
        }
    }

    public void setActionbarTitle(@Nullable String title) {
        if (title == null) {
            return;
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        mItemBag = menu.findItem(R.id.menu_item_bag);
//        LayerDrawable icon = (LayerDrawable) mItemBag.getIcon();
////        Helper.setBadgeCount(this, icon, AppSharedPref.getCartCount(this, 0));
//        return true;
//    }


    @Override
    protected void onStop() {
        super.onStop();
        destroyDbInstance();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() == 0) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (mItemBag != null) {
//            LayerDrawable icon = (LayerDrawable) mItemBag.getIcon();
////            Helper.setBadgeCount(this, icon, AppSharedPref.getCartCount(this, 0));
//        }
//    }


//    public void updateCartBadge(int cartCount) {
//        AppSharedPref.setCartCount(this, cartCount);
//        if (mItemBag != null) {
//            LayerDrawable icon = (LayerDrawable) mItemBag.getIcon();
//            Helper.setBadgeCount(this, icon, AppSharedPref.getCartCount(this, 0));
//        }
//    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        mCompositeDisposable.clear();
//        RetrofitClient.getDispatcher().cancelAll();
//    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mSqLiteDatabase.close();
//        AlertDialogHelper.dismiss(this);
//    }
}