package com.prinkal.currencypicker

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.prinkal.currencypicker.ExtendedCurrency.Companion.getCurrencyByISO

class MultiCurrencyListAdapter(private val mContext: Context, var currencies: List<ExtendedCurrency?>, var selectedCurrencies: MutableSet<String?>) : BaseAdapter(), CompoundButton.OnCheckedChangeListener {
    var inflater: LayoutInflater
    private val checkedCurrencies: SparseBooleanArray
    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        setChecked(buttonView.tag as Int, isChecked)
    }

    fun isChecked(position: Int): Boolean {
        return checkedCurrencies[position, false]
    }

    fun setChecked(position: Int, isChecked: Boolean) {
        checkedCurrencies.put(position, isChecked)
        if (isChecked) {
            selectedCurrencies.add(currencies[position]!!.code)
        } else {
            selectedCurrencies.remove(currencies[position]!!.code)
        }
    }

    fun toggle(position: Int) {
        setChecked(position, !isChecked(position))
    }

    override fun getCount(): Int {
        return currencies.size
    }

    override fun getItem(arg0: Int): Any? {
        return null
    }

    override fun getItemId(arg0: Int): Long {
        return 0
    }

    override fun getView(position: Int, view: View, parent: ViewGroup): View {
        val currency = currencies[position]
        val cell = Cell.from(view)
        cell!!.textView!!.text = currency!!.name
        cell.checkBox!!.tag = position
        cell.checkBox!!.setOnCheckedChangeListener(this)
        cell.checkBox!!.isChecked = checkedCurrencies[position, false]
        /*
        if(selectedCurrencies.contains(currency.getCode())){
            //cell.checkBox.setChecked(true);
        }

        cell.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("VALUES",currency.getCode());
                if(isChecked){
                    //selectedCurrencies.add(currency.getCode());
                }
                else{
                    //selectedCurrencies.remove(currency.getCode());
                }
            }
        });

        */currency.loadFlagByCode(mContext)
        if (currency.flag != -1) cell.imageView!!.setImageResource(currency.flag)
        return view
    }

    internal class Cell {
        var textView: TextView? = null
        var imageView: AppCompatImageView? = null
        var checkBox: CheckBox? = null

        companion object {
            fun from(view: View?): Cell? {
                if (view == null) return null
                return if (view.tag == null) {
                    val cell = Cell()
                    cell.textView = view.findViewById<View>(R.id.row_title) as TextView
                    cell.imageView = view.findViewById<View>(R.id.row_icon) as AppCompatImageView
                    cell.checkBox = view.findViewById<View>(R.id.checkBox) as CheckBox
                    cell.checkBox!!.visibility = View.VISIBLE
                    view.tag = cell
                    cell
                } else {
                    view.tag as Cell
                }
            }
        }
    }

    init {
        checkedCurrencies = SparseBooleanArray(currencies.size)
        for (code in selectedCurrencies) {
            val position = currencies.indexOf(getCurrencyByISO(code!!))
            checkedCurrencies.put(position, true)
        }
        inflater = LayoutInflater.from(mContext)
    }
}