package com.prinkal.pos.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.activity.TaxActivity;
import com.prinkal.pos.app.databinding.FragmentAddTaxBinding;
import com.prinkal.pos.app.db.entity.Tax;
import com.prinkal.pos.app.handlers.AddTaxFragmentHandler;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class AddTaxFragment extends Fragment {
    private static final String ARG_PARAM1 = "tax";
    private static final String ARG_PARAM2 = "edit";

    private Tax tax;
    private boolean isEdit;
    public FragmentAddTaxBinding binding;

    public AddTaxFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tax = (Tax) getArguments().getSerializable(ARG_PARAM1);
            isEdit = getArguments().getBoolean(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_tax, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.setData(tax);
        binding.setIsEdit(isEdit);
        binding.setHandler(new AddTaxFragmentHandler(getActivity()));
        if (isEdit) {
            ((TaxActivity) getContext())
                    .setTitle(getContext().getString(R.string.edit_tax_title));
        } else {
            ((TaxActivity) getContext())
                    .setTitle(getContext().getString(R.string.add_tax_title));
        }
        ((TaxActivity) getActivity()).binding.setIsEdit(isEdit);
        ((TaxActivity) getActivity()).binding.setData(tax);
        ((TaxActivity) getActivity()).binding.save.setVisibility(View.VISIBLE);
        ((TaxActivity) getActivity()).binding.setHandler2(new AddTaxFragmentHandler(getActivity()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((TaxActivity) getActivity()).binding.save.setVisibility(View.GONE);
        if (getContext() != null) {
            ((TaxActivity) getContext())
                    .setTitle(getContext().getString(R.string.title_activity_tax));
        }
        ((TaxActivity) getActivity()).setTax();
    }
}
