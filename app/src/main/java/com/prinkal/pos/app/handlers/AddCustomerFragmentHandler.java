package com.prinkal.pos.app.handlers;

import android.content.Context;
import android.widget.Toast;

import com.prinkal.pos.app.activity.BaseActivity;
import com.prinkal.pos.app.db.DataBaseController;
import com.prinkal.pos.app.db.entity.Customer;
import com.prinkal.pos.app.fragment.AddCustomerFragment;
import com.prinkal.pos.app.helper.ToastHelper;
import com.prinkal.pos.app.interfaces.DataBaseCallBack;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class AddCustomerFragmentHandler {

    private Context context;

    public AddCustomerFragmentHandler(Context context) {
        this.context = context;
    }

    public void saveCustomer(Customer customer, boolean isEdit) {

        if (isValidated(customer)) {
            if (!isEdit) {
                DataBaseController.getInstance().addCustomer(context, customer, new DataBaseCallBack() {
                    @Override
                    public void onSuccess(Object responseData, String successMsg) {
                        Fragment fragment = ((BaseActivity) context).mSupportFragmentManager.findFragmentByTag(com.prinkal.pos.app.fragment.AddCustomerFragment.class.getSimpleName());
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
                DataBaseController.getInstance().updateCustomer(context, customer, new DataBaseCallBack() {
                    @Override
                    public void onSuccess(Object responseData, String successMsg) {
                        Fragment fragment = ((BaseActivity) context).mSupportFragmentManager.findFragmentByTag(com.prinkal.pos.app.fragment.AddCustomerFragment.class.getSimpleName());
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
            }
        }
    }

    public boolean isValidated(Customer customer) {
        customer.setDisplayError(true);
        Fragment fragment = ((BaseActivity) context).mSupportFragmentManager.findFragmentByTag(AddCustomerFragment.class.getSimpleName());
        if (fragment != null && fragment.isAdded()) {
            AddCustomerFragment categoryFragment = ((AddCustomerFragment) fragment);
            if (!customer.getFirstNameError().isEmpty()) {
                categoryFragment.binding.firstName.requestFocus();
                return false;
            }
            if (!customer.getLastNameError().isEmpty()) {
                categoryFragment.binding.lastName.requestFocus();
                return false;
            }
            if (!customer.getEmailError().isEmpty()) {
                categoryFragment.binding.customerEmail.requestFocus();
                return false;
            }
            if (!customer.getContactNumberError().isEmpty()) {
                categoryFragment.binding.customerContactNo.requestFocus();
                return false;
            }
            customer.setDisplayError(false);
            return true;
        }
        return false;
    }

    public void deleteCustomer(Customer customer) {
        if (customer != null) {
            DataBaseController.getInstance().deleteCustomer(context, customer, new DataBaseCallBack() {
                @Override
                public void onSuccess(Object responseData, String successMsg) {
                    Fragment fragment = ((BaseActivity) context).mSupportFragmentManager.findFragmentByTag(AddCustomerFragment.class.getSimpleName());
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
        }
    }
}
