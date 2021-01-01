package com.prinkal.pos.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.databinding.FragmentCashBinding;
import com.prinkal.pos.app.handlers.CheckoutHandler;
import com.prinkal.pos.app.helper.AppSharedPref;
import com.prinkal.pos.app.model.CashModel;
import com.prinkal.pos.app.model.TotalModel;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class CashFragment extends Fragment {
    private static final String ARG_PARAM1 = "total";
    private static final String ARG_PARAM2 = "param2";

    private TotalModel total;
    private String mParam2;
    public FragmentCashBinding cashBinding;

    public CashFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            total = (TotalModel) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cashBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cash, container, false);
        return cashBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(getString(R.string.cash_fragment_title));
        CashModel cashModel = new CashModel();
        cashModel.setTotal(total.getRoundTotal());
        cashModel.setFormatedTotal(total.getFormatedRoundTotal());
        cashBinding.setData(cashModel);
        cashBinding.setHasReturn(AppSharedPref.isReturnCart(getActivity()));
        cashBinding.setTotal(total);
        cashBinding.setHandler(new CheckoutHandler(getActivity()));
        if (AppSharedPref.isReturnCart(getActivity())) {
            cashBinding.cashCollectedTil.setHint(String.format(getString(R.string.cash_refunded), AppSharedPref.getSelectedCurrencySymbol(getActivity())));
            cashModel.setCollectedCash(cashModel.getTotal());
        } else
            cashBinding.cashCollectedTil.setHint(String.format(getString(R.string.cash_collected), AppSharedPref.getSelectedCurrencySymbol(getActivity())));
    }

    public void onDetach() {
        super.onDetach();
        getActivity().setTitle(getString(R.string.title_activity_checkout));
    }

}
