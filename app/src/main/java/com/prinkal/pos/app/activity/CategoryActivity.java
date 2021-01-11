package com.prinkal.pos.app.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.databinding.ActivityCategoryBinding;
import com.prinkal.pos.app.db.DataBaseController;
import com.prinkal.pos.app.db.entity.Category;
import com.prinkal.pos.app.handlers.AddNEditCategoryHandler;
import com.prinkal.pos.app.handlers.CategoryHandler;
import com.prinkal.pos.app.interfaces.DataBaseCallBack;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;

public class CategoryActivity extends BaseActivity {

    public ActivityCategoryBinding binding;
    private List<Category> categories;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category);
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Category category = new Category();
        binding.setData(category);
        binding.setHandler(new CategoryHandler(this, category));
        binding.setHandler2(new AddNEditCategoryHandler(this));
        categories = new ArrayList<>();
        setCategory();
    }

    public void setCategory() {
        DataBaseController.getInstance().getCategory(this, new DataBaseCallBack() {
            @Override
            public void onSuccess(Object responseData, String msg) {
                if (!responseData.toString().equalsIgnoreCase("[]")) {
                    if (!(categories.toString().equalsIgnoreCase(responseData.toString()))) {
                        if (categories.size() > 0)
                            categories.clear();
                        categories.addAll((List<Category>) responseData);
                        if (categoryAdapter == null) {
                            categoryAdapter = new CategoryAdapter(CategoryActivity.this, categories);
                            binding.categoryRv.setAdapter(categoryAdapter);
                        } else {
                            categoryAdapter.notifyDataSetChanged();
                        }
                    }
                    binding.setVisibility(true);
                } else {
                    binding.setVisibility(false);
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                Toast.makeText(CategoryActivity.this, errorMsg + "", Toast.LENGTH_SHORT).show();
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