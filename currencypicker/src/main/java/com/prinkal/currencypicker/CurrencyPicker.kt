package com.prinkal.currencypicker

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.EditText
import android.widget.ListView
import androidx.fragment.app.DialogFragment
import java.util.*

class CurrencyPicker : DialogFragment() {
    private var searchEditText: EditText? = null
    private var currencyListView: ListView? = null
    private var adapter: CurrencyListAdapter? = null
    private val currenciesList: MutableList<ExtendedCurrency> = ArrayList()
    private var selectedCurrenciesList: MutableList<ExtendedCurrency> = ArrayList()
    private var listener: CurrencyPickerListener? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.currency_picker, null)
        val args = arguments
        if (args != null && dialog != null) {
            val dialogTitle = args.getString("dialogTitle")
            dialog!!.setTitle(dialogTitle)
            val width = resources.getDimensionPixelSize(R.dimen.cp_dialog_width)
            val height = resources.getDimensionPixelSize(R.dimen.cp_dialog_height)
            dialog!!.window!!.setLayout(width, height)
        }
        searchEditText = view.findViewById<View>(R.id.currency_code_picker_search) as EditText
        currencyListView = view.findViewById<View>(R.id.currency_code_picker_listview) as ListView
        selectedCurrenciesList = ArrayList(currenciesList.size)
        selectedCurrenciesList.addAll(currenciesList)
        adapter = CurrencyListAdapter(activity!!, selectedCurrenciesList)
        currencyListView!!.adapter = adapter
        currencyListView!!.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            if (listener != null) {
                val currency = selectedCurrenciesList[position]
                listener!!.onSelectCurrency(currency.name, currency.code, currency.symbol,
                        currency.flag)
            }
        }
        searchEditText!!.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                search(s.toString())
            }
        })
        return view
    }

    override fun dismiss() {
        if (dialog != null) {
            super.dismiss()
        } else {
            fragmentManager!!.popBackStack()
        }
    }

    fun setListener(listener: CurrencyPickerListener?) {
        this.listener = listener
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

    fun setCurrenciesList(savedCurrencies: Set<String?>) {
        currenciesList.clear()
        for (code in savedCurrencies) {
            currenciesList.add(ExtendedCurrency.getCurrencyByISO(code))
        }
    }

    companion object {
        /**
         * To support show as dialog
         */
        @JvmStatic
        fun newInstance(dialogTitle: String?): CurrencyPicker {
            val picker = CurrencyPicker()
            val bundle = Bundle()
            bundle.putString("dialogTitle", dialogTitle)
            picker.arguments = bundle
            return picker
        }
    }

    init {
        setCurrenciesList(ExtendedCurrency.allCurrencies)
    }
}