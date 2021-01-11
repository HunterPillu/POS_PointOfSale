package com.prinkal.pos.app.handlers;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.activity.BaseActivity;
import com.prinkal.pos.app.activity.OptionsActivity;
import com.prinkal.pos.app.db.DataBaseController;
import com.prinkal.pos.app.db.entity.OptionValues;
import com.prinkal.pos.app.db.entity.Options;
import com.prinkal.pos.app.fragment.AddOptionFragment;
import com.prinkal.pos.app.helper.ToastHelper;
import com.prinkal.pos.app.interfaces.DataBaseCallBack;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class OptionHandler {

    private Context context;

    public OptionHandler(Context context) {
        this.context = context;
    }

    public void addOption(Options options) {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        Fragment fragment;
        fragment = ((BaseActivity) context).mSupportFragmentManager.findFragmentByTag(AddOptionFragment.class.getSimpleName());
        if (fragment == null)
            fragment = new AddOptionFragment();
        if (!fragment.isAdded()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("options", options);
            bundle.putBoolean("IS_EDIT", false);
            fragment.setArguments(bundle);
            fragmentTransaction.add(((OptionsActivity) context).binding.optionFl.getId(), fragment, fragment.getClass().getSimpleName());
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName()).commit();
        }
    }

    public void addOptionValue() {
        OptionValues optionValues = new OptionValues();
        AddOptionFragment fragment = (AddOptionFragment) ((BaseActivity) context).mSupportFragmentManager.findFragmentByTag(AddOptionFragment.class.getSimpleName());
        fragment.optionValues.add(optionValues);
        fragment.binding.optionValuesRv.getAdapter().notifyDataSetChanged();
    }

    public void removeOption(OptionValues data) {
        AddOptionFragment fragment = (AddOptionFragment) ((BaseActivity) context).mSupportFragmentManager.findFragmentByTag(AddOptionFragment.class.getSimpleName());
        fragment.optionValues.remove(data);
        fragment.binding.optionValuesRv.getAdapter().notifyDataSetChanged();
    }

    public void saveOption(Options options, boolean isEdit) {
        AddOptionFragment fragment = (AddOptionFragment) ((BaseActivity) context).mSupportFragmentManager.findFragmentByTag(AddOptionFragment.class.getSimpleName());
        options.setOptionValues(fragment.optionValues);
        List<OptionValues> optionValuesList = new ArrayList<>();
        for (OptionValues optionValues : options.getOptionValues()) {
            if (!optionValues.getOptionValueName().isEmpty())
                optionValuesList.add(optionValues);
        }
        options.setOptionValues(optionValuesList);
        if (isValidated(options)) {
            if (!isEdit)
                DataBaseController.getInstance().addOption(context, options, new DataBaseCallBack() {
                    @Override
                    public void onSuccess(Object responseData, String successMsg) {
                        ToastHelper.showToast(context, successMsg, Toast.LENGTH_LONG);
                        ((BaseActivity) context).mSupportFragmentManager.popBackStackImmediate();
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMsg) {
                        ToastHelper.showToast(context, errorMsg, Toast.LENGTH_LONG);
                    }
                });
            else {
                DataBaseController.getInstance().updateOptions(context, options, new DataBaseCallBack() {
                    @Override
                    public void onSuccess(Object responseData, String successMsg) {
                        ToastHelper.showToast(context, successMsg, Toast.LENGTH_LONG);
                        ((BaseActivity) context).mSupportFragmentManager.popBackStackImmediate();
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMsg) {
                        ToastHelper.showToast(context, errorMsg, Toast.LENGTH_LONG);
                    }
                });
            }
        }
    }

    public void onClickOption(Options options) {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        Fragment fragment;
        fragment = ((BaseActivity) context).mSupportFragmentManager.findFragmentByTag(AddOptionFragment.class.getSimpleName());
        if (fragment == null)
            fragment = new AddOptionFragment();
        if (!fragment.isAdded()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("options", options);
            bundle.putBoolean("edit", true);
            fragment.setArguments(bundle);
            Log.d("name", fragment.getClass().getSimpleName() + "");
            fragmentTransaction.add(((OptionsActivity) context).binding.optionFl.getId(), fragment, fragment.getClass().getSimpleName());
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName()).commit();
        }
    }


    public void deleteOption(Options options) {
        if (options != null) {
            DataBaseController.getInstance().deleteOption(context, options, new DataBaseCallBack() {
                @Override
                public void onSuccess(Object responseData, String successMsg) {
                    Fragment fragment = ((BaseActivity) context).mSupportFragmentManager.findFragmentByTag(AddOptionFragment.class.getSimpleName());
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

    public boolean isValidated(Options options) {
        options.setDisplayError(true);
        Fragment fragment = ((BaseActivity) context).mSupportFragmentManager.findFragmentByTag(AddOptionFragment.class.getSimpleName());
        if (fragment != null && fragment.isAdded()) {
            AddOptionFragment categoryFragment = ((AddOptionFragment) fragment);
            if (!options.getOptionNameError().isEmpty()) {
                categoryFragment.binding.optionName.requestFocus();
                return false;
            }
            options.setDisplayError(false);
            return true;
        }
        return false;
    }
}
