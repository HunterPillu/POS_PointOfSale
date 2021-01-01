package com.prinkal.pos.app.handlers;

import android.content.Context;
import android.widget.Toast;

import com.prinkal.pos.app.activity.BaseActivity;
import com.prinkal.pos.app.db.DataBaseController;
import com.prinkal.pos.app.db.entity.Category;
import com.prinkal.pos.app.fragment.AddCategoryFragment;
import com.prinkal.pos.app.helper.ToastHelper;
import com.prinkal.pos.app.interfaces.DataBaseCallBack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AddNEditCategoryHandler {

    private final Context context;

    public AddNEditCategoryHandler(Context context) {
        this.context = context;
    }

    public void save(Category data, boolean isEdit) {
        if (isValidated(data)) {
            if (!isEdit) {
                data.setParentId(0);
                data.setLevel(1);
                data.setPath("0_" + data.getCId());
                DataBaseController.getInstanse().addCategoryDetails(context, data, new DataBaseCallBack() {
                    @Override
                    public void onSuccess(Object responseData, String successMsg) {
                        Fragment fragment = ((BaseActivity) context).mSupportFragmentManager.findFragmentByTag(AddCategoryFragment.class.getSimpleName());
                        FragmentTransaction ft = ((BaseActivity) context).mSupportFragmentManager.beginTransaction();
                        ft.detach(fragment);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        ft.commit();
                        ((BaseActivity) context).mSupportFragmentManager.popBackStackImmediate();
                        ToastHelper.showToast(context, successMsg, Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMsg) {

                    }
                });
            } else {
                DataBaseController.getInstanse().updateCategory(context, data, new DataBaseCallBack() {
                    @Override
                    public void onSuccess(Object responseData, String successMsg) {
                        Fragment fragment = ((BaseActivity) context).mSupportFragmentManager.findFragmentByTag(AddCategoryFragment.class.getSimpleName());
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

    public void delete(Category data) {
        if (data != null) {
            DataBaseController.getInstanse().deleteCategory(context, data, new DataBaseCallBack() {
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

    public boolean isValidated(Category category) {
        category.setDisplayError(true);
        Fragment fragment = ((BaseActivity) context).mSupportFragmentManager.findFragmentByTag(AddCategoryFragment.class.getSimpleName());
        if (fragment != null && fragment.isAdded()) {
            AddCategoryFragment categoryFragment = ((AddCategoryFragment) fragment);
            if (!category.getCategoryNameError().isEmpty()) {
                categoryFragment.binding.categoryName.requestFocus();
                return false;
            }
            category.setDisplayError(false);
            return true;
        }
        return false;
    }

}
