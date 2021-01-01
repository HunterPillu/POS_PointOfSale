package com.prinkal.pos.app.handlers;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.activity.BaseActivity;
import com.prinkal.pos.app.activity.CategoryActivity;
import com.prinkal.pos.app.db.entity.Category;
import com.prinkal.pos.app.fragment.AddCategoryFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class CategoryHandler {

    private Context context;
    private Category category;

    public CategoryHandler(Context context, Category category) {
        this.context = context;
        this.category = category;
    }

    public CategoryHandler(Context context) {
        this.context = context;
    }

    public void addCategory(View v) {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        Fragment fragment;
        fragment = ((BaseActivity) context).mSupportFragmentManager.findFragmentByTag(AddCategoryFragment.class.getSimpleName());
        if (fragment == null)
            fragment = new AddCategoryFragment();
        if (!fragment.isAdded()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("category", category);
            fragment.setArguments(bundle);
            Log.d("name", fragment.getClass().getSimpleName() + "");
            fragmentTransaction.add(((CategoryActivity) context).binding.categoryFl.getId(), fragment, fragment.getClass().getSimpleName());
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName()).commit();
        }
    }

    public void onClickCategory(Category category) {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        Fragment fragment;
        fragment = ((BaseActivity) context).mSupportFragmentManager.findFragmentByTag(AddCategoryFragment.class.getSimpleName());
        if (fragment == null)
            fragment = new AddCategoryFragment();
        if (!fragment.isAdded()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("category", category);
            bundle.putBoolean("edit", true);
            fragment.setArguments(bundle);
            Log.d("name", fragment.getClass().getSimpleName() + "");
            fragmentTransaction.add(((CategoryActivity) context).binding.categoryFl.getId(), fragment, fragment.getClass().getSimpleName());
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName()).commit();
        }
    }
}
