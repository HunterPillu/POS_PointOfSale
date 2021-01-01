package com.prinkal.pos.app.customviews;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.activity.CartActivity;
import com.prinkal.pos.app.db.entity.Product;
import com.prinkal.pos.app.helper.AppSharedPref;
import com.prinkal.pos.app.helper.Helper;
import com.prinkal.pos.app.model.CartModel;

import java.text.DecimalFormat;

public class CustomDialogClassForDiscount extends Dialog implements
        android.view.View.OnClickListener {

    public Dialog d;
    public Button yes, remove;
    private final Context context;
    private final Product product;
    private final CartModel cartData;

    public CustomDialogClassForDiscount(Context context, Product product, CartModel cartData) {
        super(context);
        this.context = context;
        this.product = product;
        this.cartData = cartData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_discount_dialog);

        yes = findViewById(R.id.btn_yes);
        yes.setOnClickListener(this);
        remove = findViewById(R.id.btn_remove);
        remove.setOnClickListener(this);
        if (product.getDiscount() != 0) {
            ((EditText) findViewById(R.id.discount_et)).setText(product.getDiscount() + "");
            findViewById(R.id.btn_remove).setVisibility(View.VISIBLE);
        } else
            findViewById(R.id.btn_remove).setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                String discountAmount = ((EditText) findViewById(R.id.discount_et)).getText().toString();
                if (!discountAmount.isEmpty() && Double.parseDouble(discountAmount) != 0 && Double.parseDouble(product.getCartProductSubtotal()) > Float.parseFloat(discountAmount)) {
                    product.setDiscount(Float.parseFloat(discountAmount));
                    product.setFormattedDiscount(Helper.currencyFormater(Float.parseFloat(discountAmount), context));
                    product.setCartProductSubtotal(Double.parseDouble(product.getCartProductSubtotal()) - Float.parseFloat(discountAmount) + "");
                    product.setFormattedCartProductSubtotal(Helper.currencyFormater(Double.parseDouble(product.getCartProductSubtotal()), context));
                    DecimalFormat df = new DecimalFormat("####0.00");
                    float totalDiscountByProduct = cartData.getTotals().getTotalDiscountByProduct() + product.getDiscount();
                    double newGrandTotal = Double.parseDouble(cartData.getTotals().getGrandTotal()) - product.getDiscount();

                    cartData.getTotals().setTotalDiscountByProduct(totalDiscountByProduct);
                    cartData.getTotals().setFormatedDiscount(Helper.currencyFormater(totalDiscountByProduct, context));

                    cartData.getTotals().setGrandTotal(df.format(newGrandTotal));
                    cartData.getTotals().setFormatedGrandTotal(Helper.currencyFormater(Double.parseDouble(df.format(newGrandTotal)), context));
                    cartData.getTotals().setRoundTotal(Math.ceil(newGrandTotal) + "");
                    cartData.getTotals().setFormatedRoundTotal(Helper.currencyFormater(Double.parseDouble(df.format(Math.ceil(newGrandTotal))), context) + "");
                    AppSharedPref.setCartData(context, Helper.fromCartModelToString(cartData));
                    dismiss();
                    ((CartActivity) context).recreate();
                } else {
                    Helper.shake(context, findViewById(R.id.dialog_ll));
                    findViewById(R.id.error_text).setVisibility(View.VISIBLE);
                    if (!discountAmount.isEmpty()) {
                        ((TextView) findViewById(R.id.error_text)).setText("Discount Amount should not be zero (0).");
                    }
                }
                break;

            case R.id.btn_remove:
                product.setCartProductSubtotal(Double.parseDouble(product.getCartProductSubtotal()) - product.getDiscount() + "");
                product.setFormattedCartProductSubtotal(Helper.currencyFormater(Double.parseDouble(product.getCartProductSubtotal()), context));
                DecimalFormat df = new DecimalFormat("####0.00");
                float totalDiscountByProduct = cartData.getTotals().getTotalDiscountByProduct() - product.getDiscount();
                double newGrandTotal = Double.parseDouble(cartData.getTotals().getGrandTotal()) + product.getDiscount();

                cartData.getTotals().setTotalDiscountByProduct(totalDiscountByProduct);
                cartData.getTotals().setFormatedDiscount(Helper.currencyFormater(totalDiscountByProduct, context));

                cartData.getTotals().setGrandTotal(df.format(newGrandTotal));
                cartData.getTotals().setFormatedGrandTotal(Helper.currencyFormater(Double.parseDouble(df.format(newGrandTotal)), context));
                cartData.getTotals().setRoundTotal(Math.ceil(newGrandTotal) + "");
                cartData.getTotals().setFormatedRoundTotal(Helper.currencyFormater(Double.parseDouble(df.format(Math.ceil(newGrandTotal))), context) + "");
                product.setDiscount(0);
                product.setFormattedDiscount(Helper.currencyFormater(0, context));
                AppSharedPref.setCartData(context, Helper.fromCartModelToString(cartData));
                dismiss();
                ((CartActivity) context).recreate();
                break;
            default:
                dismiss();
                break;
        }
    }
}