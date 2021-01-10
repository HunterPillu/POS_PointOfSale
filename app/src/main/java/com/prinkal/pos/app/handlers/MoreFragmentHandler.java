package com.prinkal.pos.app.handlers;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.activity.CashDrawerActivity;
import com.prinkal.pos.app.activity.CategoryActivity;
import com.prinkal.pos.app.activity.CustomerActivity;
import com.prinkal.pos.app.activity.MainActivity;
import com.prinkal.pos.app.activity.MyAccountInfo;
import com.prinkal.pos.app.activity.OptionsActivity;
import com.prinkal.pos.app.activity.OtherActivity;
import com.prinkal.pos.app.activity.PaymentMethodActivity;
import com.prinkal.pos.app.activity.ProductActivity;
import com.prinkal.pos.app.activity.SalesAndReportingActivity;
import com.prinkal.pos.app.activity.SignUpSignInActivity;
import com.prinkal.pos.app.activity.TaxActivity;
import com.prinkal.pos.app.helper.AppSharedPref;
import com.prinkal.pos.app.helper.ToastHelper;
import com.prinkal.pos.app.model.MoreData;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_CASH_DRAWER;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_CATEGORIES;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_CUSTOMERS;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_MY_ACCOUNT_INFO;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_OPTIONS;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_OTHERS;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_PAYMENT_METHODS;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_PRODUCTS;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_SALES_AND_REPORTING;
import static com.prinkal.pos.app.constants.ApplicationConstants.MORE_MENU_TAXES;
import static com.prinkal.pos.app.helper.AppSharedPref.USER_PREF;

public class MoreFragmentHandler {

    private Context context;

    public MoreFragmentHandler(Context context) {
        this.context = context;
    }

    public void performAction(MoreData moreData) {
        Intent i;
        switch (moreData.getId()) {
            case MORE_MENU_CASH_DRAWER:
                i = new Intent(context, CashDrawerActivity.class);
                context.startActivity(i);
                break;
            case MORE_MENU_SALES_AND_REPORTING:
                i = new Intent(context, SalesAndReportingActivity.class);
                context.startActivity(i);
                break;
            case MORE_MENU_CUSTOMERS:
                i = new Intent(context, CustomerActivity.class);
                context.startActivity(i);
                break;
            case MORE_MENU_CATEGORIES:
                i = new Intent(context, CategoryActivity.class);
                context.startActivity(i);
                break;
            case MORE_MENU_PRODUCTS:
                i = new Intent(context, ProductActivity.class);
                context.startActivity(i);
                break;
            case MORE_MENU_MY_ACCOUNT_INFO:
                i = new Intent(context, MyAccountInfo.class);
                context.startActivity(i);
                break;
            case MORE_MENU_OPTIONS:
                i = new Intent(context, OptionsActivity.class);
                context.startActivity(i);
                break;
            case MORE_MENU_TAXES:
                i = new Intent(context, TaxActivity.class);
                context.startActivity(i);
                break;
            case MORE_MENU_PAYMENT_METHODS:
                i = new Intent(context, PaymentMethodActivity.class);
                context.startActivity(i);
                break;
            case MORE_MENU_OTHERS:
                i = new Intent(context, OtherActivity.class);
                context.startActivity(i);
                break;
            default:
                ToastHelper.showToast(context, "THIS OPTION IS UNDER DEVELOPMENT MODE!!", Toast.LENGTH_LONG);
        }
    }


    public void signOut() {
        SweetAlertDialog sweetAlert = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        sweetAlert.setTitleText(context.getString(R.string.warning))
                .setContentText("Do you want to logout?" /*+ " Do you want to see?"*/)
                .setConfirmText(context.getResources().getString(R.string.yes))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        AppSharedPref.removeAllPref(context);
                        //AppSharedPref.getSharedPreferenceEditor(context, USER_PREF).clear().apply();
                        Intent i = new Intent(context, SignUpSignInActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(i);
                        ((MainActivity) context).finish();
                    }
                })
                .setCancelText(context.getResources().getString(R.string.no))
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }


}