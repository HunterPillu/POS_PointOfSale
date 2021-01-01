package com.prinkal.pos.app.handlers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.activity.CustomerActivity;
import com.prinkal.pos.app.db.entity.Customer;
import com.prinkal.pos.app.fragment.AddCustomerFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class CustomerHandler {

    private Context context;

    public CustomerHandler(Context context) {
        this.context = context;
    }

    public void openAddCustomerFragment(Customer customer) {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        Fragment fragment;
        fragment = fragmentManager.findFragmentByTag(AddCustomerFragment.class.getSimpleName());
        if (fragment == null)
            fragment = new AddCustomerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("customer", customer);
//        bundle.putBoolean("edit", false);
        fragment.setArguments(bundle);
        fragmentTransaction.add(((CustomerActivity) context).binding.customerFl.getId(), fragment, fragment.getClass().getSimpleName());
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName()).commit();
    }

    public void selectCustomer(Customer customer, boolean isChooseCustomer) {
        if (isChooseCustomer) {
            Intent i = ((AppCompatActivity) context).getIntent();
            i.putExtra("customer", customer);
            ((AppCompatActivity) context).setResult(Activity.RESULT_OK, i);
            ((AppCompatActivity) context).finish();
        } else {
//            edit customer stuff
            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
            Fragment fragment;
            fragment = fragmentManager.findFragmentByTag(AddCustomerFragment.class.getSimpleName());
            if (fragment == null)
                fragment = new AddCustomerFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("customer", customer);
            bundle.putBoolean("edit", true);
            fragment.setArguments(bundle);
            fragmentTransaction.add(((CustomerActivity) context).binding.customerFl.getId(), fragment, fragment.getClass().getSimpleName());
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName()).commit();
        }
    }
}
