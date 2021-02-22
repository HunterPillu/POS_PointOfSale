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
import com.prinkal.pos.app.adapter.MoreAdapter;
import com.prinkal.pos.app.databinding.FragmentMoreBinding;
import com.prinkal.pos.app.handlers.MoreFragmentHandler;
import com.prinkal.pos.app.model.MoreData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_CASH_DRAWER;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_CATEGORIES;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_CATEGORIES_ENABLED;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_CUSTOMERS;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_CUSTOMERS_ENABLED;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_DISCOUNTS_AND_CART_RULES;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_DISCOUNTS_AND_CART_RULES_ENABLED;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_GIFT_CARD;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_GIFT_CARD_ENABLED;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_MY_ACCOUNT_INFO;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_OPTIONS;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_OPTIONS_ENABLED;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_OTHERS;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_PAYMENT_METHODS;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_PAYMENT_METHODS_ENABLED;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_POS_USERS;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_POS_USERS_ENABLED;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_PRODUCTS;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_PRODUCTS_ENABLED;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_SALES_AND_REPORTING;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_TAXES;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_TAXES_ENABLED;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_USER_ROLES;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_USER_ROLES_ENABLED;

public class MoreFragment extends Fragment {

    public FragmentMoreBinding binding;
    String[] label;
    int[] icons;
    int[] menus;

    public static MoreFragment newInstance(/*String param1, String param2*/) {
        MoreFragment fragment = new MoreFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false);
        MoreAdapter moreAdpater = new MoreAdapter(getActivity(), createData());
        binding.moreRv.setAdapter(moreAdpater);
        moreAdpater = new MoreAdapter(getActivity(), createDataForQuickManage());
        binding.moreQuicklyManageRv.setAdapter(moreAdpater);
        binding.moreRv.setNestedScrollingEnabled(false);
        binding.moreQuicklyManageRv.setNestedScrollingEnabled(false);
        binding.setHandler(new MoreFragmentHandler(getActivity()));
        return binding.getRoot();
    }

    List<MoreData> createData() {
        label = new String[]{getString(R.string.cash_drawer), getString(R.string.sales_and_reporting), getString(R.string.my_account_info)};
        icons = new int[]{R.drawable.icon_drawer_light, R.drawable.icon_sales, R.drawable.icon_my_account};
        menus = new int[]{MORE_MENU_CASH_DRAWER,
                MORE_MENU_SALES_AND_REPORTING,
                MORE_MENU_MY_ACCOUNT_INFO};
        boolean[] enabled = {true, true, true};

        List<MoreData> moreData = new ArrayList<>();
        for (int i = 0; i < label.length; i++) {
            moreData.add(new MoreData(label[i], icons[i], menus[i], enabled[i]));
        }
        return moreData;
    }


    List<MoreData> createDataForQuickManage() {
        label = new String[]{getString(R.string.customers), getString(R.string.categories), getString(R.string.products), "Options", getString(R.string.gift_card)
                , getString(R.string.discounts_and_cart_rules), getString(R.string.taxes), getString(R.string.payment_methods)
                , getString(R.string.pos_users), getString(R.string.user_roles), "Others"};
        icons = new int[]{R.drawable.icon_customers, R.drawable.icon_category, R.drawable.icon_box, R.drawable.ic_more_options, R.drawable.icon_gift, R.drawable.icon_discount
                , R.drawable.icon_tax, R.drawable.icon_card, R.drawable.icon_users, R.drawable.icon_roles, R.drawable.ic_more_vert_white_24px};
        menus = new int[]{MORE_MENU_CUSTOMERS,
                MORE_MENU_CATEGORIES,
                MORE_MENU_PRODUCTS,
                MORE_MENU_OPTIONS,
                MORE_MENU_GIFT_CARD,
                MORE_MENU_DISCOUNTS_AND_CART_RULES,
                MORE_MENU_TAXES,
                MORE_MENU_PAYMENT_METHODS,
                MORE_MENU_POS_USERS,
                MORE_MENU_USER_ROLES,
                MORE_MENU_OTHERS};

        boolean[] enabled = {
                MORE_MENU_CUSTOMERS_ENABLED,
                MORE_MENU_CATEGORIES_ENABLED,
                MORE_MENU_PRODUCTS_ENABLED,
                MORE_MENU_OPTIONS_ENABLED,
                MORE_MENU_GIFT_CARD_ENABLED,
                MORE_MENU_DISCOUNTS_AND_CART_RULES_ENABLED,
                MORE_MENU_TAXES_ENABLED,
                MORE_MENU_PAYMENT_METHODS_ENABLED,
                MORE_MENU_POS_USERS_ENABLED,
                MORE_MENU_USER_ROLES_ENABLED,
                MORE_MENU_TAXES_ENABLED
        };

        List<MoreData> moreData = new ArrayList<>();
        for (int i = 0; i < label.length; i++) {
            moreData.add(new MoreData(label[i], icons[i], menus[i], enabled[i]));
        }
        return moreData;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getContext() != null) {
            ((BaseActivity) getContext())
                    .setActionbarTitle(getContext().getString(R.string.more_fragment_title));
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