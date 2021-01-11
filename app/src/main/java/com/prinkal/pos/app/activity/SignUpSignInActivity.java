package com.prinkal.pos.app.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.databinding.ActivitySignUpSignInBinding;
import com.prinkal.pos.app.fragment.SignInFragment;
import com.prinkal.pos.app.fragment.SignUpFragment;
import com.prinkal.pos.app.helper.AppSharedPref;
import com.prinkal.pos.app.helper.ToastHelper;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static com.prinkal.pos.app.constants.ApplicationConstants.DEFAULT_BACK_PRESSED_TIME_TO_CLOSE;

public class SignUpSignInActivity extends BaseActivity {

    private ActivitySignUpSignInBinding mBinding;
    private long mBackPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // AppSharedPref.removeAllPref(this);
        mBinding = DataBindingUtil.setContentView(SignUpSignInActivity.this, R.layout.activity_sign_up_sign_in);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        Fragment fragment;
        if (!AppSharedPref.isSignedUp(this, false)) {
            fragment = new SignUpFragment();
        } else {
            fragment = new SignInFragment();
        }
        fragmentTransaction.add(mBinding.fragmentContainer.getId(), fragment, fragment.getClass().getSimpleName());
        fragmentTransaction.addToBackStack(SignUpFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        long time = System.currentTimeMillis();
        if (time - mBackPressedTime > DEFAULT_BACK_PRESSED_TIME_TO_CLOSE) {
            mBackPressedTime = time;
            ToastHelper.showToast(this, getString(R.string.press_back_to_exit)
                    , Toast.LENGTH_SHORT);
        } else {
            finish();
        }
    }
}
