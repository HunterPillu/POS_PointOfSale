package com.prinkal.pos.app.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.adapter.CustomerAdapter;
import com.prinkal.pos.app.databinding.ActivityCustomerBinding;
import com.prinkal.pos.app.db.DataBaseController;
import com.prinkal.pos.app.db.entity.Customer;
import com.prinkal.pos.app.handlers.CustomerHandler;
import com.prinkal.pos.app.helper.ToastHelper;
import com.prinkal.pos.app.interfaces.DataBaseCallBack;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;

public class CustomerActivity extends BaseActivity {
    public ActivityCustomerBinding binding;
    CustomerAdapter customerAdapter;
    private List<Customer> customers;
    private boolean isChooseCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        customers = new ArrayList<>();
        Customer customer = new Customer();
        binding.setData(customer);
        binding.setHandler(new CustomerHandler(this));
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("choose_customer")) {
            isChooseCustomer = true;
        }
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("customer")) {
            customer = (Customer) getIntent().getExtras().getSerializable("customer");
        }
        setCustomers(customer);
    }

    private void setCustomers(final Customer customer) {
        DataBaseController.getInstanse().getCustomer(this, new DataBaseCallBack() {
            @Override
            public void onSuccess(Object responseData, String successMsg) {
                if (!responseData.toString().equalsIgnoreCase("[]")) {
                    if (!(customers.toString().equalsIgnoreCase(responseData.toString()))) {
                        if (customers.size() > 0)
                            customers.clear();
                        customers.addAll((List<Customer>) responseData);
                        if (customerAdapter == null) {
                            customerAdapter = new CustomerAdapter(CustomerActivity.this, customers, customer, isChooseCustomer);
                            binding.customerRv.setAdapter(customerAdapter);
                        } else {
                            customerAdapter.notifyDataSetChanged();
                        }
                    }
                    binding.setVisibility(true);
                } else {
                    binding.setVisibility(false);
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                ToastHelper.showToast(CustomerActivity.this, errorMsg, Toast.LENGTH_SHORT);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}