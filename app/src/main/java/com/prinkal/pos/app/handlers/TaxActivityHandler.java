package com.prinkal.pos.app.handlers;

import android.content.Context;
import android.os.Bundle;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.activity.BaseActivity;
import com.prinkal.pos.app.activity.TaxActivity;
import com.prinkal.pos.app.db.entity.Tax;
import com.prinkal.pos.app.fragment.AddTaxFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class TaxActivityHandler {

    private Context context;

    public TaxActivityHandler(Context context) {
        this.context = context;
    }

    public void addTax() {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        Fragment fragment;
        fragment = ((BaseActivity) context).mSupportFragmentManager.findFragmentByTag(AddTaxFragment.class.getSimpleName());
        if (fragment == null)
            fragment = new AddTaxFragment();
        if (!fragment.isAdded()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("tax", new Tax());
            bundle.putBoolean("edit", false);
            fragment.setArguments(bundle);
            fragmentTransaction.add(((TaxActivity) context).binding.taxFl.getId(), fragment, fragment.getClass().getSimpleName());
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName()).commit();
        }
    }

    public void editTaxRate(Tax tax) {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        Fragment fragment;
        fragment = ((BaseActivity) context).mSupportFragmentManager.findFragmentByTag(AddTaxFragment.class.getSimpleName());
        if (fragment == null)
            fragment = new AddTaxFragment();
        if (!fragment.isAdded()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("tax", tax);
            bundle.putBoolean("edit", true);
            fragment.setArguments(bundle);
            fragmentTransaction.add(((TaxActivity) context).binding.taxFl.getId(), fragment, fragment.getClass().getSimpleName());
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName()).commit();
        }
    }
}
