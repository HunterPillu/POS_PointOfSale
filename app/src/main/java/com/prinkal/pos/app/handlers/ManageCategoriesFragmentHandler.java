package com.prinkal.pos.app.handlers;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.prinkal.pos.app.activity.BaseActivity;
import com.prinkal.pos.app.databinding.ManageCategoryItemBinding;
import com.prinkal.pos.app.db.entity.Category;
import com.prinkal.pos.app.db.entity.Product;
import com.prinkal.pos.app.fragment.ManageCategoriesFragment;
import com.prinkal.pos.app.model.ProductCategoryModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import static com.prinkal.pos.app.activity.BaseActivity.TAG;

public class ManageCategoriesFragmentHandler {

    private Context context;
    private ManageCategoryItemBinding manageCategoryItemBinding;
    public static HashMap<String, Category> categoryHashMap;

    public ManageCategoriesFragmentHandler(Context context, ManageCategoryItemBinding binding) {
        this.context = context;
        this.manageCategoryItemBinding = binding;
        initialize();
    }

    public ManageCategoriesFragmentHandler(Context context) {
        this.context = context;
        initialize();
    }

    void initialize() {
        if (categoryHashMap == null)
            categoryHashMap = new HashMap<>();
    }

    public void saveCategoryToProduct(Product product, boolean isEdit) {
        if (categoryHashMap != null) {
            Iterator iterator = categoryHashMap.keySet().iterator();
            List<ProductCategoryModel> productCategories = new ArrayList<>();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                Category value = categoryHashMap.get(key);
                ProductCategoryModel data = new ProductCategoryModel();
                data.setcId(value.getCId() + "");
                data.setName(value.getCategoryName() + "");
                productCategories.add(data);
            }
            if (product != null)
                product.setProductCategories(productCategories);
            Fragment fragment = ((BaseActivity) context).mSupportFragmentManager.findFragmentByTag(ManageCategoriesFragment.class.getSimpleName());
            FragmentTransaction ft = ((BaseActivity) context).mSupportFragmentManager.beginTransaction();
            ft.detach(fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            ft.commit();
            ((BaseActivity) context).mSupportFragmentManager.popBackStackImmediate();
        }
    }

    public void onCategorySelect(Category category) {
        if (categoryHashMap.containsKey(category.getCId() + "")) {
            category.setSelected(false);
            categoryHashMap.remove(category.getCId() + "");
            manageCategoryItemBinding.selectedCategory.setVisibility(View.GONE);
        } else {
            category.setSelected(true);
            categoryHashMap.put(category.getCId() + "", category);
            manageCategoryItemBinding.selectedCategory.setVisibility(View.VISIBLE);
        }
        Log.d(TAG, "onCategorySelect: " + categoryHashMap);
    }
}
