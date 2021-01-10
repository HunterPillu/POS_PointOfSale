package com.prinkal.pos.app.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.prinkal.currencypicker.CurrencyPreference;
import com.prinkal.currencypicker.ExtendedCurrency;
import com.prinkal.pos.app.R;
import com.prinkal.pos.app.adapter.CurrencyAdapter;
import com.prinkal.pos.app.connections.ApiUtils;
import com.prinkal.pos.app.databinding.ActivityCurrencyBinding;
import com.prinkal.pos.app.db.entity.Currency;
import com.prinkal.pos.app.fragment.CurrencyPreferenceFragment;
import com.prinkal.pos.app.handlers.CurrencyHandler;
import com.prinkal.pos.app.helper.AppSharedPref;
import com.prinkal.pos.app.helper.Helper;
import com.prinkal.pos.app.helper.SweetAlertBox;
import com.prinkal.pos.app.model.OpenExchangeRate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.databinding.DataBindingUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.prinkal.pos.app.constants.OpenExchangeRatesConstants.APP_ID;
import static com.prinkal.pos.app.helper.AppSharedPref.APP_PREF;

public class CurrencyActivity extends BaseActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private ActivityCurrencyBinding binding;
    SharedPreferences preferences;
    private String TAG = "CurrencyActivity";
    private CurrencyPreference currencyPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_currency);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        AppSharedPref.getSharedPreference(this, APP_PREF).registerOnSharedPreferenceChangeListener(this);
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("currency_config")) {
            this.getFragmentManager().beginTransaction().add(R.id.currency_fl, new CurrencyPreferenceFragment()).addToBackStack("CurrencyPreferenceFragment").commit();
        } else {
            SweetAlertBox.getInstance().showProgressDialog(this);
            if (AppSharedPref.getCurrencyRate(this, "").equalsIgnoreCase("")) {
                ApiUtils.getExchangeAPIService().getRates(APP_ID, "USD").enqueue(new Callback<OpenExchangeRate>() {
                    @Override
                    public void onResponse(Call<OpenExchangeRate> call, Response<OpenExchangeRate> response) {
                        AppSharedPref.setCurrencyRate(CurrencyActivity.this, new Gson().toJson(response.body()) + "");
                        setSelectedCurrencies(AppSharedPref.getSelectedCurrency(CurrencyActivity.this));
                        SweetAlertBox.getInstance().dissmissSweetAlert();
                    }

                    @Override
                    public void onFailure(Call<OpenExchangeRate> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            } else {
                setSelectedCurrencies(AppSharedPref.getSelectedCurrency(CurrencyActivity.this));
                SweetAlertBox.getInstance().dissmissSweetAlert();
            }
            binding.setHandler(new CurrencyHandler(this));
        }
    }

    public void setSelectedCurrencies(String selectedCurrency) {
        Set<String> selectedCurrencies = preferences.getStringSet("selectedCurrencies", new HashSet<String>());

        CurrencyAdapter currencyAdapter = new CurrencyAdapter(this, getSelectedCurrencies(selectedCurrencies, selectedCurrency));
        binding.currencyRv.setAdapter(currencyAdapter);
    }

    List<Currency> getSelectedCurrencies(Set<String> selectedCurrencies, String selectedCurrency) {
        List<Currency> selectedCurrencyList = new ArrayList<>();
        try {
            JSONObject rateValues = new JSONObject(AppSharedPref.getCurrencyRate(this, ""));
            if (selectedCurrencies.size() == 0) {
                String code = "USD";
                ExtendedCurrency extendedCurrency = ExtendedCurrency.getCurrencyByISO(code);
                Currency currency = new Currency();
                currency.setCode(code);
                currency.setName(extendedCurrency.getName());
                currency.setFlag(extendedCurrency.getFlag());
                currency.setSymbol(extendedCurrency.getSymbol());
                JSONObject rates = rateValues.getJSONObject("rates");
                currency.setRate(Float.parseFloat(rates.getString(code)));
                currency.setFormatedRate(Helper.currencyFormater(Double.parseDouble(rates.getString(code)), code));
                if (code.equalsIgnoreCase(selectedCurrency))
                    currency.setSelected(true);
                else
                    currency.setSelected(false);
                selectedCurrencyList.add(currency);
            } else
                for (String code : selectedCurrencies) {
                    ExtendedCurrency extendedCurrency = ExtendedCurrency.getCurrencyByISO(code);
                    Currency currency = new Currency();
                    currency.setCode(code);
                    currency.setName(extendedCurrency.getName());
                    currency.setFlag(extendedCurrency.getFlag());
                    currency.setSymbol(extendedCurrency.getSymbol());
                    JSONObject rates = rateValues.getJSONObject("rates");
                    currency.setRate(Float.parseFloat(rates.getString(code)));
                    currency.setFormatedRate(Helper.currencyFormater(Double.parseDouble(rates.getString(code)), code));
                    if (code.equalsIgnoreCase(selectedCurrency))
                        currency.setSelected(true);
                    else
                        currency.setSelected(false);
                    selectedCurrencyList.add(currency);
                }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return selectedCurrencyList;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Log.d(TAG, "onSharedPreferenceChanged: " + s);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.menu_item_search).setVisible(false);
        menu.findItem(R.id.menu_item_scan_barcode).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}
