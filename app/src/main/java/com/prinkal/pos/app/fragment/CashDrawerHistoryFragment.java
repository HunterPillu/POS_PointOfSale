package com.prinkal.pos.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.adapter.CashDrawerHistoryAdapter;
import com.prinkal.pos.app.databinding.FragmentCashDrawerHistoryBinding;
import com.prinkal.pos.app.db.entity.CashDrawerModel;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


public class CashDrawerHistoryFragment extends Fragment {
    private static final String ARG_PARAM1 = "cashDrawerModelData";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private CashDrawerModel cashDrawerModelData;
    private String mParam2;
    FragmentCashDrawerHistoryBinding binding;

    public CashDrawerHistoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cashDrawerModelData = (CashDrawerModel) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cash_drawer_history, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.setData(cashDrawerModelData);
        CashDrawerHistoryAdapter cashDrawerHistoryAdapter = new CashDrawerHistoryAdapter(getActivity(), cashDrawerModelData.getCashDrawerItems());
        binding.cashDrawerHistoryRv.setAdapter(cashDrawerHistoryAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
