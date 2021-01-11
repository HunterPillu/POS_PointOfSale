package com.prinkal.pos.app.handlers;

import android.content.Context;
import android.widget.Toast;

import com.prinkal.pos.app.activity.MyAccountInfo;
import com.prinkal.pos.app.db.DataBaseController;
import com.prinkal.pos.app.db.entity.Administrator;
import com.prinkal.pos.app.helper.Helper;
import com.prinkal.pos.app.helper.ToastHelper;
import com.prinkal.pos.app.interfaces.DataBaseCallBack;

public class MyAccountInfoHandler {

    private Context context;

    public MyAccountInfoHandler(Context context) {
        this.context = context;
    }

    public void onClickSubmit(Administrator administrator) {
        if (isFormValidated(administrator)) {
            DataBaseController.getInstance().updateAdmin(context, administrator, new DataBaseCallBack() {
                @Override
                public void onSuccess(Object responseData, String successMsg) {
                    ToastHelper.showToast(context, successMsg, Toast.LENGTH_LONG);
                    ((MyAccountInfo) context).finish();
                }

                @Override
                public void onFailure(int errorCode, String errorMsg) {
                    ToastHelper.showToast(context, errorMsg, Toast.LENGTH_LONG);
                }
            });
        }

    }

    public boolean isFormValidated(Administrator administrator) {
        administrator.setDisplayError(true);
        MyAccountInfo myAccountInfo = ((MyAccountInfo) context);
        if (myAccountInfo != null) {
            if (!administrator.getFirstNameError().isEmpty()) {
                myAccountInfo.binding.firstName.requestFocus();
                Helper.shake(context, myAccountInfo.binding.customerFirstNameTnl);
                return false;
            }
            if (!administrator.getLastNameError().isEmpty()) {
                myAccountInfo.binding.lastName.requestFocus();
                Helper.shake(context, myAccountInfo.binding.customerLastNameTnl);
                return false;
            }
            administrator.setDisplayError(false);
            return true;
        }
        return false;
    }

//    public void signOut() {
//        AppSharedPref.getSharedPreferenceEditor(context, USER_PREF).clear().apply();
//        Intent i = new Intent(context, SignUpSignInActivity.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        context.startActivity(i);
//        ((MyAccountInfo) context).finish();
//    }

}
