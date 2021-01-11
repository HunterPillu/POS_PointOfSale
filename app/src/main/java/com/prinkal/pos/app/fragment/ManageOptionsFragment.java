package com.prinkal.pos.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.activity.ProductActivity;
import com.prinkal.pos.app.adapter.ManageOptionsAdapter;
import com.prinkal.pos.app.databinding.FragmentManageOptionsBinding;
import com.prinkal.pos.app.db.DataBaseController;
import com.prinkal.pos.app.db.entity.Options;
import com.prinkal.pos.app.db.entity.Product;
import com.prinkal.pos.app.handlers.ManageOptionFragmentHandler;
import com.prinkal.pos.app.helper.ToastHelper;
import com.prinkal.pos.app.interfaces.DataBaseCallBack;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class ManageOptionsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "product";
    private static final String ARG_PARAM2 = "edit";
    private static String TAG = "ManageOptionsFragment";
    private List<Options> options;
    private Product product;
    private boolean isEdit;
    public FragmentManageOptionsBinding binding;
    public ManageOptionsAdapter manageOptionsAdapter;
    private SweetAlertDialog sweetAlert;

    public ManageOptionsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            product = (Product) getArguments().getSerializable(ARG_PARAM1);
            isEdit = getArguments().getBoolean(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_manage_options, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        options = product.getOptions();
        ((ProductActivity) getContext())
                .setTitle(getContext().getString(R.string.choose_options));
        setProductOptions();
        ((ProductActivity) getActivity()).binding.setData(product);
        ((ProductActivity) getActivity()).binding.setManageOptionFragmentHandler(new ManageOptionFragmentHandler(getActivity()));
        ((ProductActivity) getActivity()).binding.deleteProduct.setVisibility(View.GONE);
        ((ProductActivity) getActivity()).binding.saveProduct.setVisibility(View.GONE);
        ((ProductActivity) getActivity()).binding.saveSelectedOptios.setVisibility(View.GONE);
    }

    public void setProductOptions() {
        DataBaseController.getInstance().getOptions(getActivity(), new DataBaseCallBack() {
            @Override
            public void onSuccess(Object responseData, String msg) {
                if (!responseData.toString().equalsIgnoreCase("[]")) {
                    if (!(options.size() > 0))
                        options.addAll((List<Options>) responseData);
                    manageOptionsAdapter = new ManageOptionsAdapter(getActivity(), options, product);
                    binding.manageOptionsRv.setAdapter(manageOptionsAdapter);
                } else {
                    sweetAlert = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);
                    sweetAlert.setTitleText(getString(R.string.no_options))
                            .setContentText(getResources().getString(R.string.no_options_subtitle))
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
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                ToastHelper.showToast(getActivity(), errorMsg, Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (isEdit)
            ((ProductActivity) getActivity()).binding.deleteProduct.setVisibility(View.VISIBLE);
        ((ProductActivity) getActivity()).binding.saveProduct.setVisibility(View.VISIBLE);
        ((AddProductFragment) ((ProductActivity) getActivity()).mSupportFragmentManager.findFragmentByTag(AddProductFragment.class.getSimpleName())).setProductOptions();
        getActivity().setTitle(getContext().getString(R.string.add_product_title));
    }
}