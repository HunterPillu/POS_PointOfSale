package com.prinkal.currencypicker

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.preference.MultiSelectListPreference
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.widget.LinearLayoutCompat
import com.prinkal.currencypicker.ExtendedCurrency.Companion.allCurrencies
import java.util.*

class MultiCurrencyPreference(context: Context?, attrs: AttributeSet?) : MultiSelectListPreference(context, attrs) {
    private var searchEditText: EditText? = null
    private var currencyListView: ListView? = null
    private var currencyName: Array<CharSequence>?=null
    private var currencyCode: Array<CharSequence>?=null
    private var adapter: MultiCurrencyListAdapter? = null
    private val currentIndex = 0
    private val currenciesList: MutableList<ExtendedCurrency?> = ArrayList()
    private var selectedCurrenciesList: MutableList<ExtendedCurrency?> = ArrayList()
    var preferences: SharedPreferences
    var mEditor: SharedPreferences.Editor
    override fun onPrepareDialogBuilder(builder: AlertDialog.Builder) {
        Log.v("VALUES", values.toString())
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
        val linearLayout = view.findViewById<View>(R.id.linear_layout) as LinearLayoutCompat
        val currencyListView = view.findViewById<View>(R.id.currency_code_picker_listview) as ListView
        selectedCurrenciesList = ArrayList(currenciesList.size)
        selectedCurrenciesList.addAll(currenciesList)
        adapter = MultiCurrencyListAdapter(context, selectedCurrenciesList, values)
        currencyListView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        currencyListView.adapter = adapter
        builder.setView(view)
        builder.setNegativeButton(null, null)
        builder.setPositiveButton("Ok", null)
        currencyCode = entries
        currencyName = entryValues
        check(!(currencyName == null || currencyCode == null || currencyCode!!.size != currencyName!!.size)) { "Preference requires an entries array and an entryValues array which are both the same length" }
    }

    override fun onDialogClosed(positiveResult: Boolean) {
        mEditor.putStringSet(key, values)
        mEditor.commit()
        super.onDialogClosed(positiveResult)
    }

    @SuppressLint("DefaultLocale")
    private fun search(text: String) {
        selectedCurrenciesList.clear()
        for (currency in currenciesList) {
            if (currency!!.name!!.toLowerCase(Locale.ENGLISH).contains(text.toLowerCase())) {
                selectedCurrenciesList.add(currency)
            }
        }
        adapter!!.notifyDataSetChanged()
    }

    fun setCurrenciesList(newCurrencies: List<ExtendedCurrency?>?) {
        currenciesList.clear()
        currenciesList.addAll(newCurrencies!!)
    }

    init {
        setCurrenciesList(allCurrencies)
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
        mEditor = preferences.edit()
    }
}