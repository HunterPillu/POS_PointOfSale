package com.prinkal.pos.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.databinding.FragmentSignUpBinding;
import com.prinkal.pos.app.db.entity.Administrator;
import com.prinkal.pos.app.handlers.SignUpSignInHandler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class SignUpFragment extends Fragment {
    public FragmentSignUpBinding binding;

    public SignUpFragment() {
    }

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sign_up, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Administrator administrator = new Administrator();
        administrator.setEmail("admin");
        administrator.setFirstName("admin");
        administrator.setLastName("admin");
        administrator.setPassword("admin");
        binding.setData(administrator);
        binding.setHandler(new SignUpSignInHandler(getActivity(), administrator));
    }
}

