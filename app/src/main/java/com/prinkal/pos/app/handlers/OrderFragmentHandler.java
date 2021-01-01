package com.prinkal.pos.app.handlers;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.StrictMode;
import android.widget.Toast;

import com.prinkal.pos.app.activity.BaseActivity;
import com.prinkal.pos.app.activity.CartActivity;
import com.prinkal.pos.app.activity.ViewOrderDetails;
import com.prinkal.pos.app.db.entity.OrderEntity;
import com.prinkal.pos.app.helper.AppSharedPref;
import com.prinkal.pos.app.helper.Helper;
import com.prinkal.pos.app.helper.ToastHelper;
import com.prinkal.pos.app.mail.SendMail;

import java.io.File;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static com.prinkal.pos.app.constants.BundleConstants.BUNDLE_ORDER_DATA;

public class OrderFragmentHandler {

    private Context context;

    public OrderFragmentHandler(Context context) {
        this.context = context;
    }

    public void onClickOrderItem(OrderEntity orderData) {
        Intent i = new Intent(context, ViewOrderDetails.class);
        i.putExtra(BUNDLE_ORDER_DATA, orderData);
        context.startActivity(i);
    }

    public void generateInvoice(OrderEntity orderData) {
        ActivityCompat.requestPermissions((BaseActivity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 666);
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            if (Helper.getInstanse().generateInvoice(context, orderData) != null) {
                File file = Helper.getInstanse().generateInvoice(context, orderData);
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                Uri path = Uri.fromFile(file);
                Intent pdfOpenIntent = new Intent(Intent.ACTION_VIEW);
                pdfOpenIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pdfOpenIntent.setDataAndType(path, "application/pdf");
                try {
                    context.startActivity(pdfOpenIntent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    ToastHelper.showToast(context,
                            "No Application Available to View PDF",
                            Toast.LENGTH_SHORT);
                }
            } else
                ToastHelper.showToast(context,
                        "ERROR in generating pdf",
                        Toast.LENGTH_SHORT);
        }
    }

    public void sendInvoice(OrderEntity orderData) {
        ActivityCompat.requestPermissions((BaseActivity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 666);
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            new SendMail(context, orderData).execute();
        }
    }

    public void returnItems(OrderEntity orderData, String returndOrderId) {
        if (returndOrderId.isEmpty()) {
            AppSharedPref.setCartData(context, Helper.fromCartModelToString(orderData.getCartData()));
            AppSharedPref.setReturnCart(context, true);
            AppSharedPref.setReturnOrderId(context, orderData.getOrderId() + "");
            AppSharedPref.setReturnCart(context, true);
            Intent i = new Intent(context, CartActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);
        } else {
            Intent i = new Intent(context, ViewOrderDetails.class);
            i.putExtra("order_id", returndOrderId);
            context.startActivity(i);
        }
    }
}