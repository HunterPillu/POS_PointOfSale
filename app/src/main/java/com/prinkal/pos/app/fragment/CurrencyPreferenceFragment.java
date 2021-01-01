package com.prinkal.pos.app.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.View;

import com.prinkal.currencypicker.CurrencyPicker;
import com.prinkal.currencypicker.CurrencyPickerListener;
import com.prinkal.currencypicker.CurrencyPreference;
import com.prinkal.pos.app.R;

import java.util.HashSet;

public class CurrencyPreferenceFragment extends PreferenceFragment implements CurrencyPickerListener, SharedPreferences.OnSharedPreferenceChangeListener {
    SharedPreferences preferences;
    private CurrencyPicker mCurrencyPicker;
    private CurrencyPreference currencyPreference;
    private static final String TAG = "CurrencyPreference";
    HashSet<String> currencyHash;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        currencyPreference = (CurrencyPreference) findPreference("selectedCurrency");
        currencyHash = new HashSet<>();
        currencyHash.add("USD");

        //remove default currency method...
        final PreferenceScreen preferenceScreen = getPreferenceScreen();
        preferenceScreen.removePreference((ListPreference) findPreference("selectedCurrency"));

        currencyPreference.setCurrenciesList(preferences.getStringSet("selectedCurrencies", currencyHash));
        mCurrencyPicker = CurrencyPicker.newInstance("Select Currency");
        mCurrencyPicker.setListener(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(getResources().getColor(R.color.white));
        view.setPadding(0, 20, 0, 0);
    }

    @Override
    public void onResume() {
        preferences.registerOnSharedPreferenceChangeListener(this);
        Log.d(TAG, "onSharedPrefeenceChanged: " + currencyPreference);
        super.onResume();
    }

    @Override
    public void onPause() {
        preferences.registerOnSharedPreferenceChangeListener(this);
        Log.d(TAG, "onSharedPreferenceChanged: " + currencyPreference);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("selectedCurrencies")) {
            currencyPreference.setCurrenciesList(preferences.getStringSet("selectedCurrencies", currencyHash));
        }
    }

    @Override
    public void onSelectCurrency(String name, String code, String symbol, int flagDrawableResID) {
        currencyPreference.setValue(code);
        Log.d(TAG, "onSelectCurrency: " + currencyPreference);
    }
}