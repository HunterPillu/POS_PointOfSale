package com.prinkal.pos.app.handlers;

import android.content.Context;
import android.os.Bundle;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.activity.CashDrawerActivity;
import com.prinkal.pos.app.db.entity.CashDrawerModel;
import com.prinkal.pos.app.fragment.CashDrawerHistoryFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class CashDrawerHandler {
    private Context context;

    public CashDrawerHandler(Context context) {
        this.context = context;
    }

    public void onClickCashData(CashDrawerModel cashDrawerModelData) {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        Fragment fragment;
        fragment = fragmentManager.findFragmentByTag(CashDrawerHistoryFragment.class.getSimpleName());
        if (fragment == null)
            fragment = new CashDrawerHistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("cashDrawerModelData", cashDrawerModelData);
        fragment.setArguments(bundle);
        fragmentTransaction.add(((CashDrawerActivity) context).binding.cashDrawerFl.getId(), fragment, fragment.getClass().getSimpleName());
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName()).commit();
    }
}
