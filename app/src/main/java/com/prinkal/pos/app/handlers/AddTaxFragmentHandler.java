package com.prinkal.pos.app.handlers;

import android.content.Context;
import android.widget.Toast;

import com.prinkal.pos.app.activity.BaseActivity;
import com.prinkal.pos.app.db.DataBaseController;
import com.prinkal.pos.app.db.entity.Tax;
import com.prinkal.pos.app.fragment.AddTaxFragment;
import com.prinkal.pos.app.helper.ToastHelper;
import com.prinkal.pos.app.interfaces.DataBaseCallBack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class AddTaxFragmentHandler {

    private Context context;

    public AddTaxFragmentHandler(Context context) {
        this.context = context;
    }

    public void save(Tax data, boolean isEdit) {
        if (isValidated(data)) {
            if (!isEdit) {
                DataBaseController.getInstance().addTaxRate(context, data, new DataBaseCallBack() {
                    @Override
                    public void onSuccess(Object responseData, String successMsg) {
                        Fragment fragment = ((BaseActivity) context).mSupportFragmentManager.findFragmentByTag(AddTaxFragment.class.getSimpleName());
                        FragmentTransaction ft = ((BaseActivity) context).mSupportFragmentManager.beginTransaction();
                        ft.detach(fragment);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        ft.commit();
                        ((BaseActivity) context).mSupportFragmentManager.popBackStackImmediate();
                        ToastHelper.showToast(context, successMsg, Toast.LENGTH_LONG);
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMsg) {
                        ToastHelper.showToast(context, errorMsg, Toast.LENGTH_LONG);
                    }
                });
            } else {
                DataBaseController.getInstance().updateTax(context, data, new DataBaseCallBack() {
                    @Override
                    public void onSuccess(Object responseData, String successMsg) {
                        Fragment fragment = ((BaseActivity) context).mSupportFragmentManager.findFragmentByTag(AddTaxFragment.class.getSimpleName());
                        FragmentTransaction ft = ((BaseActivity) context).mSupportFragmentManager.beginTransaction();
                        ft.detach(fragment);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        ft.commit();
                        ((BaseActivity) context).mSupportFragmentManager.popBackStackImmediate();
                        ToastHelper.showToast(context, successMsg, Toast.LENGTH_LONG);
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMsg) {
                        ToastHelper.showToast(context, errorMsg + "", Toast.LENGTH_LONG);
                    }
                });
            }
        } else
            Toast.makeText(context, "Please check the form carefully!!", Toast.LENGTH_SHORT).show();
    }

    public void deleteTax(Tax data) {
        if (data != null) {
            DataBaseController.getInstance().deleteTax(context, data, new DataBaseCallBack() {
                @Override
                public void onSuccess(Object responseData, String successMsg) {
                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                    fragmentManager.popBackStackImmediate();
                    ToastHelper.showToast(context, successMsg, Toast.LENGTH_LONG);
                }

                @Override
                public void onFailure(int errorCode, String errorMsg) {
                    ToastHelper.showToast(context, errorMsg, Toast.LENGTH_LONG);
                }
            });
        }
    }

    public boolean isValidated(Tax tax) {
        tax.setDisplayError(true);
        Fragment fragment = ((BaseActivity) context).mSupportFragmentManager.findFragmentByTag(AddTaxFragment.class.getSimpleName());
        if (fragment != null && fragment.isAdded()) {
            AddTaxFragment taxFragment = ((AddTaxFragment) fragment);
            if (!tax.getTaxNameError().isEmpty()) {
                taxFragment.binding.taxName.requestFocus();
                return false;
            }
            if (!tax.getTaxRateError().isEmpty()) {
                taxFragment.binding.taxRate.requestFocus();
                return false;
            }
            tax.setDisplayError(false);
            return true;
        }
        return false;
    }
}
