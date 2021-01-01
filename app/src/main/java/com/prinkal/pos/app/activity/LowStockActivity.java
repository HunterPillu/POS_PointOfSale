package com.prinkal.pos.app.activity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.adapter.HomePageProductAdapter;
import com.prinkal.pos.app.databinding.ActivityLowStockBinding;
import com.prinkal.pos.app.db.DataBaseController;
import com.prinkal.pos.app.db.entity.Product;
import com.prinkal.pos.app.helper.ToastHelper;
import com.prinkal.pos.app.interfaces.DataBaseCallBack;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;

public class LowStockActivity extends BaseActivity {

    private ActivityLowStockBinding binding;
    private List<Product> products;
    private HomePageProductAdapter lowStockAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_low_stock);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        products = new ArrayList<>();
        loadLowStockProduct();
    }

    private void loadLowStockProduct() {
        DataBaseController.getInstanse().getAllLowStockProducts(this, 5, new DataBaseCallBack() {
            @Override
            public void onSuccess(Object responseData, String msg) {
                if (!responseData.toString().equalsIgnoreCase("[]")) {
                    if (!(products.toString().equalsIgnoreCase(responseData.toString()))) {
                        if (products.size() > 0)
                            products.clear();
                        products.addAll((List<Product>) responseData);
                        if (lowStockAdapter == null) {
                            lowStockAdapter = new HomePageProductAdapter(LowStockActivity.this, products, true);
                            binding.lowStockProductRv.setAdapter(lowStockAdapter);
                        } else {
                            lowStockAdapter.notifyDataSetChanged();
                        }
                    }
                    binding.setVisibility(true);
                } else {
                    binding.setVisibility(false);
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                ToastHelper.showToast(LowStockActivity.this, errorMsg + "", Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.menu_item_search).setVisible(false);
        menu.findItem(R.id.menu_item_scan_barcode).setVisible(false);
        return true;
    }

}
