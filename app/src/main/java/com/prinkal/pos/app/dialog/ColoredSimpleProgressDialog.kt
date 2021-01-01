package com.prinkal.pos.app.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import com.prinkal.pos.app.R
import com.prinkal.pos.app.databinding.DialogProgressColoredBinding

class ColoredSimpleProgressDialog @JvmOverloads constructor(context: Context, cancelable: Boolean = false, cancelListener: DialogInterface.OnCancelListener? = null as DialogInterface.OnCancelListener?) : Dialog(context, cancelable, cancelListener) {
    private val binding: DialogProgressColoredBinding
    private val mContext: Context

    @ColorRes
    private var mBackGroundColorResource = 0

    @ColorRes
    private var mProgressBarColorResource = 0
    private var mBackGroundDrawable: Drawable? = null
    private var mProgressBarDrawable: Drawable? = null
    private var mSeparatorDrawable: Drawable? = null
    private var mTextViewText: String? = null
    private var mTitleText: String? = null
    private var mTextViewTextSize = 0
    private var mTitleTextSize = 0
    private var TEXT_VIEW_SCALE_TYPE = 0
    private var TITLE_TEXT_VIEW_SCALE_TYPE = 0
    private var mTextBackgroundDrawable: Drawable? = null
    private var mShowTitle = false
    private var mShowTitleSeparator = true
    fun setBackGroundColor(@ColorRes color: Int): ColoredSimpleProgressDialog {
        mBackGroundColorResource = color
        return this
    }

    fun setBackGroundColor(color: Color): ColoredSimpleProgressDialog {
        mBackGroundDrawable = ColorDrawable(Color.parseColor(color.toString()))
        return this
    }

    fun setBackGroundColor(color: String?): ColoredSimpleProgressDialog {
        mBackGroundDrawable = ColorDrawable(Color.parseColor(color))
        return this
    }

    fun setBackGroundDrawable(@DrawableRes drawableInt: Int): ColoredSimpleProgressDialog {
        mBackGroundDrawable = ContextCompat.getDrawable(mContext, drawableInt)
        return this
    }

    fun setBackGroundDrawable(drawable: Drawable?): ColoredSimpleProgressDialog {
        mBackGroundDrawable = drawable
        return this
    }

    fun setProgressBarColor(@ColorRes color: Int): ColoredSimpleProgressDialog {
        mProgressBarColorResource = color
        return this
    }

    fun setProgressBarColor(color: Color): ColoredSimpleProgressDialog {
        mProgressBarDrawable = ColorDrawable(Color.parseColor(color.toString()))
        return this
    }

    fun setProgressBarColor(color: String?): ColoredSimpleProgressDialog {
        mProgressBarDrawable = ColorDrawable(Color.parseColor(color))
        return this
    }

    fun setProgressBarDrawable(drawable: Drawable?): ColoredSimpleProgressDialog {
        mProgressBarDrawable = drawable
        return this
    }

    fun setText(text: String?): ColoredSimpleProgressDialog {
        mTextViewText = text
        return this
    }

    fun setText(@StringRes text: Int): ColoredSimpleProgressDialog {
        mTextViewText = mContext.getString(text)
        return this
    }

    fun setTitleText(text: String?): ColoredSimpleProgressDialog {
        mTitleText = text
        return this
    }

    fun setTitleText(@StringRes text: Int): ColoredSimpleProgressDialog {
        mTitleText = mContext.getString(text)
        return this
    }

    fun setContentTextSize(size: Int): ColoredSimpleProgressDialog {
        mTextViewTextSize = size
        return this
    }

    fun setContentTextSizeInSP(size: Int): ColoredSimpleProgressDialog {
        TEXT_VIEW_SCALE_TYPE = 2
        mTextViewTextSize = size
        return this
    }

    fun setContentTextSizeResource(@DimenRes size: Int) {
        mTextViewTextSize = mContext.resources.getDimensionPixelSize(size)
    }

    fun setTitleTextSize(size: Int): ColoredSimpleProgressDialog {
        mTitleTextSize = size
        return this
    }

    fun setTitleTextSizeWithUnit(size: Int, unit: Int): ColoredSimpleProgressDialog {
        when (unit) {
            1 -> {
                TITLE_TEXT_VIEW_SCALE_TYPE = 1
                mTitleTextSize = size
                return this
            }
            2 -> {
                TITLE_TEXT_VIEW_SCALE_TYPE = 2
                mTitleTextSize = size
                return this
            }
            3 -> {
                TITLE_TEXT_VIEW_SCALE_TYPE = 3
                mTitleTextSize = size
                return this
            }
            4 -> {
                TITLE_TEXT_VIEW_SCALE_TYPE = 4
                mTitleTextSize = size
                return this
            }
            5 -> {
                TITLE_TEXT_VIEW_SCALE_TYPE = 5
                mTitleTextSize = size
                return this
            }
        }
        TITLE_TEXT_VIEW_SCALE_TYPE = 0
        mTitleTextSize = size
        return this
    }

    fun setTitleTextSizeResource(@DimenRes size: Int) {
        mTitleTextSize = mContext.resources.getDimensionPixelSize(size)
    }

    fun setTextBackGroundColor(color: Color): ColoredSimpleProgressDialog {
        mTextBackgroundDrawable = ColorDrawable(Color.parseColor(color.toString()))
        return this
    }

    fun setTextBackGroundColor(color: String?): ColoredSimpleProgressDialog {
        mTextBackgroundDrawable = ColorDrawable(Color.parseColor(color))
        return this
    }

    fun setTextBackGroundDrawable(@DrawableRes drawableInt: Int): ColoredSimpleProgressDialog {
        mTextBackgroundDrawable = ContextCompat.getDrawable(mContext, drawableInt)
        return this
    }

    fun setTextBackGroundDrawable(drawable: Drawable?): ColoredSimpleProgressDialog {
        mTextBackgroundDrawable = drawable
        return this
    }

    fun showTitle(show: Boolean): ColoredSimpleProgressDialog {
        mShowTitle = show
        return this
    }

    fun showTitleSeparator(show: Boolean): ColoredSimpleProgressDialog {
        mShowTitleSeparator = show
        return this
    }

    fun setTitleAndContentSeparatorColor(color: Color): ColoredSimpleProgressDialog {
        mSeparatorDrawable = ColorDrawable(Color.parseColor(color.toString()))
        return this
    }

    fun setTitleAndContentSeparatorColor(color: String?): ColoredSimpleProgressDialog {
        mSeparatorDrawable = ColorDrawable(Color.parseColor(color))
        return this
    }

    fun setTitleAndContentSeparatorColor(@ColorRes color: Int): ColoredSimpleProgressDialog {
        mSeparatorDrawable = ColorDrawable(ContextCompat.getColor(mContext, color))
        return this
    }

    val progressBar: ProgressBar
        get() = binding.progressBar
    val contentView: TextView
        get() = binding.text
    val titleView: TextView
        get() = binding.titleTextView
    val titleSeparatorView: View
        get() = binding.titleAndContentSeparator
    val titleContainerview: LinearLayoutCompat
        get() = binding.titleContainer
    val contentContainerView: LinearLayoutCompat
        get() = binding.contentContainer

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        if (mBackGroundColorResource != 0) binding.window.setBackgroundColor(ContextCompat.getColor(mContext, mBackGroundColorResource))
        if (mBackGroundDrawable != null) if (Build.VERSION.SDK_INT >= 16) {
            binding.window.background = mBackGroundDrawable
        } else {
            binding.window.setBackgroundDrawable(mBackGroundDrawable)
        }
        if (mTextViewText != null) binding.text.text = mTextViewText
        if (mTitleText != null) {
            binding.titleTextView.text = mTitleText
            mShowTitle = true
        }
        if (mShowTitle) {
            binding.titleContainer.visibility = View.VISIBLE
        } else {
            binding.titleContainer.visibility = View.GONE
        }
        if (mShowTitleSeparator) {
            binding.titleAndContentSeparator.visibility = View.VISIBLE
        } else {
            binding.titleAndContentSeparator.visibility = View.GONE
        }
        if (mTextViewTextSize != 0) binding.text.setTextSize(TEXT_VIEW_SCALE_TYPE, mTextViewTextSize.toFloat())
        if (mTitleTextSize != 0) binding.titleTextView.setTextSize(TITLE_TEXT_VIEW_SCALE_TYPE, mTitleTextSize.toFloat())
        if (mTextBackgroundDrawable != null) if (Build.VERSION.SDK_INT >= 16) {
            binding.text.background = mTextBackgroundDrawable
        } else {
            binding.text.setBackgroundDrawable(mTextBackgroundDrawable)
        }
        if (mSeparatorDrawable != null) if (Build.VERSION.SDK_INT >= 16) {
            binding.titleAndContentSeparator.background = mSeparatorDrawable
        } else {
            binding.titleAndContentSeparator.setBackgroundDrawable(mSeparatorDrawable)
        }
        val indeterminate = binding.progressBar.isIndeterminate
        try {
            if (mProgressBarColorResource != 0) if (Build.VERSION.SDK_INT >= 23) {
                if (indeterminate) {
                    binding.progressBar.indeterminateTintList = ColorStateList.valueOf(mContext.resources.getColor(mProgressBarColorResource, null))
                } else {
                    binding.progressBar.progressTintList = ColorStateList.valueOf(mContext.resources.getColor(mProgressBarColorResource, null))
                }
            } else if (Build.VERSION.SDK_INT >= 21) {
                if (indeterminate) {
                    binding.progressBar.indeterminateTintList = ColorStateList.valueOf(mContext.resources.getColor(mProgressBarColorResource))
                } else {
                    binding.progressBar.progressTintList = ColorStateList.valueOf(mContext.resources.getColor(mProgressBarColorResource))
                }
            } else if (indeterminate) {
                binding.progressBar.indeterminateDrawable.setColorFilter(mContext.resources.getColor(mProgressBarColorResource), PorterDuff.Mode.SRC_IN)
            } else {
                binding.progressBar.progressDrawable.setColorFilter(mContext.resources.getColor(mProgressBarColorResource), PorterDuff.Mode.SRC_IN)
            }
            if (mProgressBarDrawable != null) if (mProgressBarDrawable is ColorDrawable) {
                if (indeterminate) {
                    binding.progressBar.indeterminateDrawable.setColorFilter((mProgressBarDrawable as ColorDrawable?)!!.color, PorterDuff.Mode.SRC_IN)
                } else {
                    binding.progressBar.progressDrawable.setColorFilter((mProgressBarDrawable as ColorDrawable?)!!.color, PorterDuff.Mode.SRC_IN)
                }
            } else if (indeterminate) {
                binding.progressBar.indeterminateDrawable = mProgressBarDrawable
            } else {
                binding.progressBar.progressDrawable = mProgressBarDrawable
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        fun getIntstantProgressDialog(context: Context): ColoredSimpleProgressDialog {
            val coloredSimpleProgressDialog = ColoredSimpleProgressDialog(context)
            coloredSimpleProgressDialog.showTitle(true)
            coloredSimpleProgressDialog.showTitleSeparator(true)
            coloredSimpleProgressDialog.setTitleText(R.string.progress_dialog_title_text)
            coloredSimpleProgressDialog.setText(R.string.progress_dialog_content_text)
            coloredSimpleProgressDialog.setCanceledOnTouchOutside(true)
            coloredSimpleProgressDialog.setCancelable(true)
            return coloredSimpleProgressDialog
        }
    }

    init {
        requestWindowFeature(1)
        mContext = context
        binding = DialogProgressColoredBinding.inflate(LayoutInflater.from(context), null, false)
        setContentView(binding.root)
    }
}