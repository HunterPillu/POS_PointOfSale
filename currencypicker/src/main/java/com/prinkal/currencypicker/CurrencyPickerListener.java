package com.prinkal.currencypicker;

public interface CurrencyPickerListener {
    public void onSelectCurrency(String name, String code, String symbol, int flagDrawableResID);
}
