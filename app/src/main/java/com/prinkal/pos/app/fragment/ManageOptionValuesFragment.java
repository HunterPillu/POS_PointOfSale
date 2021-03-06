package com.prinkal.pos.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.activity.ProductActivity;
import com.prinkal.pos.app.databinding.FragmentManageOptionValuesBinding;
import com.prinkal.pos.app.db.entity.Options;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class ManageOptionValuesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "options";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Options options;
    private String mParam2;
    private FragmentManageOptionValuesBinding binding;
    private SweetAlertDialog sweetAlert;

    public ManageOptionValuesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            options = (Options) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_manage_option_values, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (options.getOptionValues().size() > 0) {
            ManageOptionValuesAdapter manageOptionValuesAdapter = new ManageOptionValuesAdapter(getActivity(), options.getOptionValues());
            binding.manageOptionValuesRv.setAdapter(manageOptionValuesAdapter);
        } else {
            sweetAlert = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);
            sweetAlert.setTitleText(getString(R.string.no_options_values))
                    .setContentText(getResources().getString(R.string.no_options_values_subtitle))
                    .setConfirmText(getResources().getString(R.string.dialog_ok))
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            getActivity().onBackPressed();
                        }
                    })
                    .show();
            sweetAlert.setCancelable(false);
        }
        ((ProductActivity) getActivity()).binding.saveSelectedOptios.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((ProductActivity) getActivity()).binding.saveSelectedOptios.setVisibility(View.GONE);
    }
}
