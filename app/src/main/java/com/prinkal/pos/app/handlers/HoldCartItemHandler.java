package com.prinkal.pos.app.handlers;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.prinkal.pos.app.activity.CartActivity;
import com.prinkal.pos.app.db.DataBaseController;
import com.prinkal.pos.app.db.entity.HoldCart;
import com.prinkal.pos.app.helper.AppSharedPref;
import com.prinkal.pos.app.helper.Helper;
import com.prinkal.pos.app.helper.ToastHelper;
import com.prinkal.pos.app.interfaces.DataBaseCallBack;
import com.prinkal.pos.app.model.CartModel;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.prinkal.pos.app.helper.Helper.fromStringToCartModel;

public class HoldCartItemHandler {
    private Context context;

    public HoldCartItemHandler(Context context) {
        this.context = context;
    }

    public void addToCart(final HoldCart holdCart) {
        DataBaseController.getInstance().deleteHoldCart(context, holdCart);
        if (fromStringToCartModel(AppSharedPref.getCartData(context)) != null && fromStringToCartModel(AppSharedPref.getCartData(context)).getProducts().size() > 0) {
            CartModel cartData = fromStringToCartModel(AppSharedPref.getCartData(context));
            final HoldCart holdCart1 = new HoldCart();
            holdCart1.setCartData(fromStringToCartModel(AppSharedPref.getCartData(context)));
            holdCart1.setQty(cartData.getTotals().getQty());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMMM-yy");
            String date = simpleDateFormat.format(new Date());
            holdCart1.setDate(date);
            simpleDateFormat = new SimpleDateFormat("hh:mm a");
            String currentTime = simpleDateFormat.format(new Date());
            holdCart1.setTime(currentTime + "");

            DataBaseController.getInstance().addHoldCart(context, holdCart1, new DataBaseCallBack() {
                @Override
                public void onSuccess(Object responseData, String successMsg) {
                    AppSharedPref.deleteCartData(context);
                    AppSharedPref.setCartData(context, Helper.fromCartModelToString(holdCart.getCartData()));
                    Intent i = new Intent(context, CartActivity.class);
                    context.startActivity(i);
                }

                @Override
                public void onFailure(int errorCode, String errorMsg) {
                    ToastHelper.showToast(context, errorMsg + "", Toast.LENGTH_LONG);
                }
            });
        } else {
            AppSharedPref.setCartData(context, Helper.fromCartModelToString(holdCart.getCartData()));
            Intent i = new Intent(context, CartActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);
        }
    }
}