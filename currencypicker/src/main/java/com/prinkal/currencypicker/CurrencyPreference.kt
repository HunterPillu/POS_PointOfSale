package com.prinkal.currencypicker

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.preference.ListPreference
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.EditText
import android.widget.ListView
import java.util.*

class CurrencyPreference(context: Context, attrs: AttributeSet?) : ListPreference(context, attrs), OnSharedPreferenceChangeListener {
    private var searchEditText: EditText? = null
    private var currencyListView: ListView? = null
    private var currencyName: Array<CharSequence>?=null
    private var currencyCode: Array<CharSequence>?=null
    private var adapter: CurrencyListAdapter? = null
    private val currentIndex = 0
    private val currenciesList: MutableList<ExtendedCurrency> = ArrayList()
    private var selectedCurrenciesList: MutableList<ExtendedCurrency> = ArrayList()
    private val selectedCurrencyCode: String? = null
    private var defaultCurrencyCode: String? = null
    var preferences: SharedPreferences
    var mEditor: SharedPreferences.Editor

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == "selectedCurrency") {
            summary = value
        }
        if (key == "selectedCurrencies") {
            setCurrenciesList(preferences.getStringSet("selectedCurrencies", HashSet()))
        }
    }

    override fun onPrepareDialogBuilder(builder: AlertDialog.Builder) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.currency_picker, null)
        searchEditText = view.findViewById<View>(R.id.currency_code_picker_search) as EditText
        currencyListView = view.findViewById<View>(R.id.currency_code_picker_listview) as ListView
        searchEditText!!.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                search(s.toString())
            }
        })
        val currencyListView = view.findViewById<View>(R.id.currency_code_picker_listview) as ListView
        selectedCurrenciesList = ArrayList(currenciesList.size)
        selectedCurrenciesList.addAll(currenciesList)
        adapter = CurrencyListAdapter(context, selectedCurrenciesList)
        currencyListView.adapter = adapter
        currencyListView.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            val currency = selectedCurrenciesList[position]
            value = currency.code
            summary = currency.code
            mEditor.putString(key, currency.code)
            mEditor.commit()
            dialog.dismiss()
        }
        builder.setView(view)
        builder.setNegativeButton("Cancel", null)
        builder.setPositiveButton(null, null)
        currencyCode = entries
        currencyName = entryValues
        check(!(currencyName == null || currencyCode == null || currencyCode!!.size != currencyName!!.size)) { "Preference requires an entries array and an entryValues array which are both the same length" }
    }

    @SuppressLint("DefaultLocale")
    private fun search(text: String) {
        selectedCurrenciesList.clear()
        for (currency in currenciesList) {
            if (currency.name.toLowerCase(Locale.ENGLISH).contains(text.toLowerCase())) {
                selectedCurrenciesList.add(currency)
            }
        }
        adapter!!.notifyDataSetChanged()
    }

    fun setCurrenciesList(newCurrencies: List<ExtendedCurrency>?) {
        currenciesList.clear()
        currenciesList.addAll(newCurrencies!!)
    }

    fun setCurrenciesList(savedCurrencies: Set<String?>?) {
        currenciesList.clear()
        for (code in savedCurrencies!!) {
            currenciesList.add(ExtendedCurrency.getCurrencyByISO(code)!!)
        }
    }

    init {
        //setDialogLayoutResource(R.layout.currency_picker);
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
        setCurrenciesList(ExtendedCurrency.allCurrencies)
        mEditor = preferences.edit()
        summary = preferences.getString(key, value)
        //        this.setEnabled(false);
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.attrs_currency, 0, 0)
        defaultCurrencyCode = try {
            a.getString(R.styleable.attrs_currency_currencyCode)
        } finally {
            a.recycle()
        }
    }
}