package com.prinkal.pos.app.handlers;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prinkal.pos.app.activity.CurrencyActivity;
import com.prinkal.pos.app.db.entity.Currency;
import com.prinkal.pos.app.helper.AppSharedPref;

import java.lang.reflect.Type;

public class CurrencyHandler {

    private Context context;

    public CurrencyHandler(Context context) {
        this.context = context;
    }

    public void onSelectCurrency(Currency data) {
        AppSharedPref.setTempSelectedCurrency(context, new Gson().toJson(data));
        ((CurrencyActivity) context).setSelectedCurrencies(data.getCode());
    }

    public void saveSelectedCurrency() {
        if (!AppSharedPref.getTempSelectedCurrency(context).equalsIgnoreCase("")) {
            Gson gson = new Gson();
            Type type = new TypeToken<Currency>() {
            }.getType();
            Currency currency = gson.fromJson(AppSharedPref.getTempSelectedCurrency(context), type);
            AppSharedPref.setSelectedCurrency(context, currency.getCode());
            AppSharedPref.setSelectedCurrencyRate(context, currency.getRate());
            AppSharedPref.setSelectedCurrencySymbol(context, currency.getSymbol());
            AppSharedPref.clearTempSelectedCurrency(context);
        }
        ((CurrencyActivity) context).finish();
    }
}
