package com.prinkal.pos.app.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.databinding.ActivityCheckoutBinding;
import com.prinkal.pos.app.handlers.CheckoutHandler;
import com.prinkal.pos.app.helper.AppSharedPref;
import com.prinkal.pos.app.model.CartModel;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

public class Checkout extends BaseActivity {
    public ActivityCheckoutBinding checkoutBinding;
    CartModel cartData;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_checkout);
        setSupportActionBar(checkoutBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getExtras().containsKey("cartData")) {
            cartData = (CartModel) getIntent().getExtras().getSerializable("cartData");
            checkoutBinding.setData(cartData);
        }
        checkoutBinding.setVisibility(AppSharedPref.isCashEnabled(this, true));
        checkoutBinding.setHandler(new CheckoutHandler(this));
        checkoutBinding.setHasReturn(AppSharedPref.isReturnCart(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.menu_item_search).setVisible(false);
        menu.findItem(R.id.menu_item_scan_barcode).setVisible(false);
        return true;
    }

}
