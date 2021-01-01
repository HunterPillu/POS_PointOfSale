package com.prinkal.pos.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.activity.BaseActivity;
import com.prinkal.pos.app.adapter.HoldCartAdapter;
import com.prinkal.pos.app.databinding.FragmentHoldBinding;
import com.prinkal.pos.app.db.DataBaseController;
import com.prinkal.pos.app.db.entity.HoldCart;
import com.prinkal.pos.app.interfaces.DataBaseCallBack;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class HoldFragment extends Fragment {
    FragmentHoldBinding binding;
    List<HoldCart> holdCartList;

    public static HoldFragment newInstance(/*String param1, String param2*/) {
        HoldFragment fragment = new HoldFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hold, container, false);
        binding.setVisibility(true);
        holdCartList = new ArrayList<>();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHoldCartData();
    }

    void setHoldCartData() {
        DataBaseController.getInstanse().getHoldCart(getActivity(), new DataBaseCallBack() {
            @Override
            public void onSuccess(Object responseData, String successMsg) {
                if (((List<HoldCart>) responseData).size() > 0) {
                    if (holdCartList.size() > 0)
                        holdCartList.clear();
                    holdCartList.addAll((List<HoldCart>) responseData);
                    HoldCartAdapter holdCartAdapter = new HoldCartAdapter(getActivity(), holdCartList);
                    binding.holdCartRv.setAdapter(holdCartAdapter);
                    binding.setVisibility(true);
                } else {
                    binding.setVisibility(false);
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getContext() != null) {
            ((BaseActivity) getContext())
                    .setActionbarTitle(getContext().getString(R.string.hold_fragment_title));
            setHoldCartData();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        final MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final MenuItem barcodeItem = menu.findItem(R.id.menu_item_scan_barcode);
        searchItem.setVisible(false);
        barcodeItem.setVisible(false);
    }
}