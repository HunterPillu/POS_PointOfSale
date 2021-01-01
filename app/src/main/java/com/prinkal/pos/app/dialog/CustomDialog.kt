package com.prinkal.pos.app.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.prinkal.pos.app.R
import com.prinkal.pos.app.databinding.DialogCustomBinding

class CustomDialog @JvmOverloads constructor(private val mContext: Context, type: Type? = Type.NORMAL_TYPE_DIALOG) : Dialog(mContext), View.OnClickListener {
    private val binding: DialogCustomBinding
    private var mTitleHeading = ""
    private var mSubTitle = ""
    private var mPositiveButtontext = ""
    private var mNegativeButtontext = ""
    private var mIcon: Drawable? = null
    private var mTitleTextSize = 20
    private var mSubTitleTextSize = 16
    private var mButtonEnabled = false
    private var mNegativeButtonEnabled = false
    private var dismissOnBackPress = true
    private var mPositiveButtonBackgroundDrawable: Drawable? = null
    private var mNegativeButtonBackgroundDrawable: Drawable? = null

    @ColorInt
    private var mPositiveButtonBackgroundColor = 0

    @ColorInt
    private var mButtonTextColor = 0

    @ColorInt
    private var mNegativeButtonBackgroundColor = 0
    private val mIconUrl: String? = null
    private var positiveButtonClickListener: CustomDialogButtonClickListener? = null
    private var negativeButtonClickListener: CustomDialogButtonClickListener? = null
    fun setType(type: Type?): CustomDialog {
        when (type) {
            Type.SUCCESS_TYPE_DIALOG -> setIcon(R.drawable.ic_vector_custom_dialog_success_icon)
            Type.ERROR_TYPE_DIALOG -> setIcon(R.drawable.ic_vector_custom_dialog_error_icon)
            Type.WARNING_TYPE_DIALOG -> setIcon(R.drawable.ic_vector_custom_dialog_warning_icon)
        }
        return this
    }

    fun setTitleHeading(@StringRes text: Int): CustomDialog {
        mTitleHeading = mContext.getString(text)
        return this
    }

    fun setTitleHeading(text: String): CustomDialog {
        mTitleHeading = text
        return this
    }

    fun setSubTitle(@StringRes text: Int): CustomDialog {
        mSubTitle = mContext.getString(text)
        return this
    }

    fun setSubTitle(text: String): CustomDialog {
        mSubTitle = text
        return this
    }

    fun setTitleTextSize(testSize: Int): CustomDialog {
        mTitleTextSize = testSize
        return this
    }

    fun setSubTitleTextSize(testSize: Int): CustomDialog {
        mSubTitleTextSize = testSize
        return this
    }

    fun setIcon(@DrawableRes resource: Int): CustomDialog {
        val drawable = ContextCompat.getDrawable(mContext, resource)
        return setIcon(drawable)
    }

    fun setIcon(drawable: Drawable?): CustomDialog {
        mIcon = drawable
        return this
    }

    fun setPositiveButtonText(@StringRes text: Int): CustomDialog {
        mPositiveButtontext = mContext.getString(text)
        return this
    }

    fun setNegativeButtonText(@StringRes text: Int): CustomDialog {
        mNegativeButtontext = mContext.getString(text)
        return this
    }

    fun setPositiveButtonText(text: String): CustomDialog {
        mPositiveButtontext = text
        return this
    }

    fun setNegativeButtonText(text: String): CustomDialog {
        mNegativeButtontext = text
        return this
    }

    fun setPositiveButtonBackground(@DrawableRes resource: Int): CustomDialog {
        val drawable = ContextCompat.getDrawable(mContext, resource)
        return setPositiveButtonBackground(drawable)
    }

    fun setNegativeButtonBackground(@DrawableRes resource: Int): CustomDialog {
        val drawable = ContextCompat.getDrawable(mContext, resource)
        return setNegativeButtonBackground(drawable)
    }

    fun setPositiveButtonBackground(drawable: Drawable?): CustomDialog {
        mPositiveButtonBackgroundDrawable = drawable
        return this
    }

    fun setNegativeButtonBackground(drawable: Drawable?): CustomDialog {
        mNegativeButtonBackgroundDrawable = drawable
        return this
    }

    fun setPositiveButtonBackgroundColor(@ColorInt color: Int): CustomDialog {
        mPositiveButtonBackgroundColor = color
        return this
    }

    fun setNegativeButtonBackgroundColor(@ColorInt color: Int): CustomDialog {
        mNegativeButtonBackgroundColor = color
        return this
    }

    fun setButtonEnabled(enabled: Boolean): CustomDialog {
        mButtonEnabled = enabled
        return this
    }

    fun setNegativeButtonEnabled(enabled: Boolean): CustomDialog {
        mNegativeButtonEnabled = enabled
        return this
    }

    fun setButtonTextColor(@ColorInt color: Int): CustomDialog {
        mButtonTextColor = color
        return this
    }

    fun setDismissOnBackPress(dismissOnBackPress: Boolean): CustomDialog {
        this.dismissOnBackPress = dismissOnBackPress
        return this
    }

    override fun onBackPressed() {
        if (dismissOnBackPress) super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        if (!mTitleHeading.isEmpty()) {
            binding.heading.visibility = View.VISIBLE
            binding.heading.text = mTitleHeading
        } else {
            binding.heading.visibility = View.GONE
        }
        if (!mSubTitle.isEmpty()) {
            binding.subheading.visibility = View.VISIBLE
            binding.subheading.text = mSubTitle
        } else {
            binding.subheading.visibility = View.GONE
        }
        binding.heading.textSize = mTitleTextSize.toFloat()
        binding.subheading.textSize = mSubTitleTextSize.toFloat()
        if (mIcon != null) {
            binding.icon.visibility = View.VISIBLE
            binding.icon.setImageDrawable(mIcon)
        } else if (binding.icon.drawable != null) {
            binding.icon.visibility = View.VISIBLE
        } else {
            binding.icon.visibility = View.GONE
        }
        binding.positiveButton.visibility = if (mButtonEnabled) View.VISIBLE else View.GONE
        binding.negativeButton.visibility = if (mNegativeButtonEnabled) View.VISIBLE else View.GONE
        binding.positiveButton.text = mPositiveButtontext
        binding.negativeButton.text = mNegativeButtontext
        if (mButtonTextColor != 0) {
            binding.positiveButton.setTextColor(mButtonTextColor)
            binding.negativeButton.setTextColor(mButtonTextColor)
        }
        if (mPositiveButtonBackgroundColor != 0) binding.positiveButton.setBackgroundColor(mPositiveButtonBackgroundColor)
        if (mNegativeButtonBackgroundColor != 0) binding.negativeButton.setBackgroundColor(mNegativeButtonBackgroundColor)
        if (mPositiveButtonBackgroundDrawable != null) if (Build.VERSION.SDK_INT >= 16) {
            binding.positiveButton.background = mPositiveButtonBackgroundDrawable
        } else {
            binding.positiveButton.setBackgroundDrawable(mPositiveButtonBackgroundDrawable)
        }
        if (mNegativeButtonBackgroundDrawable != null) if (Build.VERSION.SDK_INT >= 16) {
            binding.negativeButton.background = mNegativeButtonBackgroundDrawable
        } else {
            binding.negativeButton.setBackgroundDrawable(mNegativeButtonBackgroundDrawable)
        }
        binding.positiveButton.setOnClickListener(this)
        binding.negativeButton.setOnClickListener(this)
    }

    fun setPositiveButtonClickListener(positiveButtonClickListener: CustomDialogButtonClickListener?): CustomDialog {
        this.positiveButtonClickListener = positiveButtonClickListener
        return this
    }

    fun setNegativeButtonClickListener(negativeButtonClickListener: CustomDialogButtonClickListener?): CustomDialog {
        this.negativeButtonClickListener = negativeButtonClickListener
        return this
    }

    override fun onClick(v: View) {
        val viewId = v.id
        if (viewId == R.id.positiveButton) {
            if (positiveButtonClickListener != null) {
                positiveButtonClickListener!!.onClick(this)
            } else {
                dismiss()
            }
        } else if (viewId == R.id.negativeButton) {
            if (negativeButtonClickListener != null) {
                negativeButtonClickListener!!.onClick(this)
            } else {
                dismiss()
            }
        }
    }

    val iconview: AppCompatImageView
        get() = binding.icon

    fun addMoreViews(view: View?): CustomDialog {
        binding.extraViewContainer.addView(view)
        return this
    }

    interface CustomDialogButtonClickListener {
        fun onClick(param1CustomDialog: CustomDialog?)
    }

    enum class Type {
        SUCCESS_TYPE_DIALOG, ERROR_TYPE_DIALOG, WARNING_TYPE_DIALOG, NORMAL_TYPE_DIALOG, CUSTOM_TYPE_DIALOG
    }

    companion object {
        fun getInstantDialog(context: Context, type: Type): CustomDialog {
            val customDialog = CustomDialog(context, type)
            customDialog.setPositiveButtonText(R.string.yes)
            customDialog.setButtonEnabled(true)
            customDialog.setTitleTextSize(22)
            customDialog.setSubTitleTextSize(16)
            customDialog.setCanceledOnTouchOutside(false)
            customDialog.setDismissOnBackPress(false)
            when (type) {
                Type.SUCCESS_TYPE_DIALOG -> {
                    customDialog.setIcon(R.drawable.ic_vector_custom_dialog_success_icon)
                    customDialog.setTitleHeading(R.string.success)
                    customDialog.setSubTitle(R.string.process_is_successful)
                    customDialog.setButtonEnabled(false)
                    customDialog.setCanceledOnTouchOutside(true)
                    customDialog.setDismissOnBackPress(true)
                }
                Type.ERROR_TYPE_DIALOG -> {
                    customDialog.setIcon(R.drawable.ic_vector_custom_dialog_error_icon)
                    customDialog.setTitleHeading(R.string.error)
                    customDialog.setSubTitle(R.string.error_occured_please_try_again)
                }
                Type.WARNING_TYPE_DIALOG -> {
                    customDialog.setIcon(R.drawable.ic_vector_custom_dialog_warning_icon)
                    customDialog.setTitleHeading(R.string.warning)
                    customDialog.setSubTitle(R.string.warning_mesaage)
                }
            }
            if (Build.VERSION.SDK_INT >= 23) {
                customDialog.setButtonTextColor(context.resources.getColor(R.color.colorPrimary, null))
            } else {
                customDialog.setButtonTextColor(context.resources.getColor(R.color.colorPrimary))
            }
            return customDialog
        }
    }

    init {
        if (window != null) {
            requestWindowFeature(1)
            window!!.setBackgroundDrawable(ColorDrawable(0) as Drawable)
        }
        binding = DialogCustomBinding.inflate(LayoutInflater.from(mContext), null, false)
        setContentView(binding.root)
        setType(type)
    }
}