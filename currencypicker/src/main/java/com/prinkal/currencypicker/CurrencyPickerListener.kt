package com.prinkal.currencypicker

interface CurrencyPickerListener {
    fun onSelectCurrency(name: String?, code: String?, symbol: String?, flagDrawableResID: Int)
}