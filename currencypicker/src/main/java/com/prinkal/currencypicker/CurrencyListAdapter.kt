package com.prinkal.currencypicker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView

class CurrencyListAdapter(private val mContext: Context, var currencies: List<ExtendedCurrency>) : BaseAdapter() {
    var inflater: LayoutInflater = LayoutInflater.from(mContext)
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
        cell!!.textView!!.text = currency.name
        currency.loadFlagByCode(mContext)
        if (currency.flag != -1) cell.imageView!!.setImageResource(currency.flag)
        return view
    }

    internal class Cell {
        var textView: TextView? = null
        var imageView: AppCompatImageView? = null

        companion object {
            fun from(view: View?): Cell? {
                if (view == null) return null
                return if (view.tag == null) {
                    val cell = Cell()
                    cell.textView = view.findViewById<View>(R.id.row_title) as TextView
                    cell.imageView = view.findViewById<View>(R.id.row_icon) as AppCompatImageView
                    view.tag = cell
                    cell
                } else {
                    view.tag as Cell
                }
            }
        }
    }

}