package com.prinkal.pos.app.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.prinkal.pos.app.databinding.DialogSimpleProgressBinding

class SimpleProgressDialog(context: Context) : Dialog(context) {
    private val mContext: Context? = null
    private val binding: DialogSimpleProgressBinding

    companion object {
        fun show(context: Context, title: String?, subTitle: String?): SimpleProgressDialog {
            val dialog = SimpleProgressDialog(context)
            dialog.binding.heading.text = title
            dialog.binding.subheading.text = subTitle
            dialog.show()
            return dialog
        }
    }

    init {
        requestWindowFeature(1)
        binding = DialogSimpleProgressBinding.inflate(LayoutInflater.from(context), null, false)
        setContentView(binding.root)
    }
}