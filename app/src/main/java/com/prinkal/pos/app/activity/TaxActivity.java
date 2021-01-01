package com.prinkal.pos.app.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.adapter.TaxAdapter;
import com.prinkal.pos.app.databinding.ActivityTaxBinding;
import com.prinkal.pos.app.db.DataBaseController;
import com.prinkal.pos.app.db.entity.Tax;
import com.prinkal.pos.app.handlers.TaxActivityHandler;
import com.prinkal.pos.app.interfaces.DataBaseCallBack;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;

public class TaxActivity extends BaseActivity {
    public ActivityTaxBinding binding;
    private List<Tax> taxes;
    private TaxAdapter taxAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tax);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tax tax = new Tax();
        binding.setData(tax);
        binding.setHandler(new TaxActivityHandler(this));
        taxes = new ArrayList<>();
        setTax();
    }

    public void setTax() {
        DataBaseController.getInstanse().getAllTaxes(this, new DataBaseCallBack() {
            @Override
            public void onSuccess(Object responseData, String msg) {
                if (!responseData.toString().equalsIgnoreCase("[]")) {
                    if (!(taxes.toString().equalsIgnoreCase(responseData.toString()))) {
                        if (taxes.size() > 0)
                            taxes.clear();
                        taxes.addAll((List<Tax>) responseData);
                        Log.d(TAG, "onSuccess: " + taxes.size());
                        if (taxAdapter == null) {
                            taxAdapter = new TaxAdapter(TaxActivity.this, taxes);
                            binding.taxRv.setAdapter(taxAdapter);
                        } else {
                            taxAdapter.notifyDataSetChanged();
                        }
                    }
                    binding.setVisibility(true);
                } else {
                    binding.setVisibility(false);
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                Toast.makeText(TaxActivity.this, errorMsg + "", Toast.LENGTH_SHORT).show();
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
