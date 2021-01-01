package com.prinkal.pos.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.databinding.DialogCustomBinding;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

public class CustomDialog extends Dialog implements View.OnClickListener {
    private DialogCustomBinding binding;

    private String mTitleHeading = "";

    private String mSubTitle = "";

    private String mPositiveButtontext = "";

    private String mNegativeButtontext = "";

    private Drawable mIcon;

    private Context mContext;

    private int mTitleTextSize = 20;

    private int mSubTitleTextSize = 16;

    private boolean mButtonEnabled = false;

    private boolean mNegativeButtonEnabled = false;

    private boolean dismissOnBackPress = true;

    private Drawable mPositiveButtonBackgroundDrawable;

    private Drawable mNegativeButtonBackgroundDrawable;

    @ColorInt
    private int mPositiveButtonBackgroundColor;

    @ColorInt
    private int mButtonTextColor;

    @ColorInt
    private int mNegativeButtonBackgroundColor;

    private String mIconUrl;

    private CustomDialogButtonClickListener positiveButtonClickListener = null;

    private CustomDialogButtonClickListener negativeButtonClickListener = null;

    public CustomDialog(@NonNull Context context) {
        this(context, Type.NORMAL_TYPE_DIALOG);
    }

    public CustomDialog(@NonNull Context context, Type type) {
        super(context);
        this.mContext = context;
        if (getWindow() != null) {
            requestWindowFeature(1);
            getWindow().setBackgroundDrawable((Drawable) new ColorDrawable(0));
        }
        this.binding = DialogCustomBinding.inflate(LayoutInflater.from(context), null, false);
        setContentView(this.binding.getRoot());
        setType(type);
    }

    public static CustomDialog getInstantDialog(@NonNull Context context, @NonNull Type type) {
        CustomDialog customDialog = new CustomDialog(context, type);
        customDialog.setPositiveButtonText(R.string.yes);
        customDialog.setButtonEnabled(true);
        customDialog.setTitleTextSize(22);
        customDialog.setSubTitleTextSize(16);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setDismissOnBackPress(false);
        switch (type) {
            case SUCCESS_TYPE_DIALOG:
                customDialog.setIcon(R.drawable.ic_vector_custom_dialog_success_icon);
                customDialog.setTitleHeading(R.string.success);
                customDialog.setSubTitle(R.string.process_is_successful);
                customDialog.setButtonEnabled(false);
                customDialog.setCanceledOnTouchOutside(true);
                customDialog.setDismissOnBackPress(true);
                break;
            case ERROR_TYPE_DIALOG:
                customDialog.setIcon(R.drawable.ic_vector_custom_dialog_error_icon);
                customDialog.setTitleHeading(R.string.error);
                customDialog.setSubTitle(R.string.error_occured_please_try_again);
                break;
            case WARNING_TYPE_DIALOG:
                customDialog.setIcon(R.drawable.ic_vector_custom_dialog_warning_icon);
                customDialog.setTitleHeading(R.string.warning);
                customDialog.setSubTitle(R.string.warning_mesaage);
                break;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            customDialog.setButtonTextColor(context.getResources().getColor(R.color.colorPrimary, null));
        } else {
            customDialog.setButtonTextColor(context.getResources().getColor(R.color.colorPrimary));
        }
        return customDialog;
    }

    public CustomDialog setType(Type type) {
        switch (type) {
            case SUCCESS_TYPE_DIALOG:
                setIcon(R.drawable.ic_vector_custom_dialog_success_icon);
                break;
            case ERROR_TYPE_DIALOG:
                setIcon(R.drawable.ic_vector_custom_dialog_error_icon);
                break;
            case WARNING_TYPE_DIALOG:
                setIcon(R.drawable.ic_vector_custom_dialog_warning_icon);
                break;
        }
        return this;
    }

    public CustomDialog setTitleHeading(@StringRes int text) {
        this.mTitleHeading = this.mContext.getString(text);
        return this;
    }

    public CustomDialog setTitleHeading(String text) {
        this.mTitleHeading = text;
        return this;
    }

    public CustomDialog setSubTitle(@StringRes int text) {
        this.mSubTitle = this.mContext.getString(text);
        return this;
    }

    public CustomDialog setSubTitle(String text) {
        this.mSubTitle = text;
        return this;
    }

    public CustomDialog setTitleTextSize(int testSize) {
        this.mTitleTextSize = testSize;
        return this;
    }

    public CustomDialog setSubTitleTextSize(int testSize) {
        this.mSubTitleTextSize = testSize;
        return this;
    }

    public CustomDialog setIcon(@DrawableRes int resource) {
        Drawable drawable = ContextCompat.getDrawable(this.mContext, resource);
        return setIcon(drawable);
    }

    public CustomDialog setIcon(Drawable drawable) {
        this.mIcon = drawable;
        return this;
    }

    public CustomDialog setPositiveButtonText(@StringRes int text) {
        this.mPositiveButtontext = this.mContext.getString(text);
        return this;
    }

    public CustomDialog setNegativeButtonText(@StringRes int text) {
        this.mNegativeButtontext = this.mContext.getString(text);
        return this;
    }

    public CustomDialog setPositiveButtonText(String text) {
        this.mPositiveButtontext = text;
        return this;
    }

    public CustomDialog setNegativeButtonText(String text) {
        this.mNegativeButtontext = text;
        return this;
    }

    public CustomDialog setPositiveButtonBackground(@DrawableRes int resource) {
        Drawable drawable = ContextCompat.getDrawable(this.mContext, resource);
        return setPositiveButtonBackground(drawable);
    }

    public CustomDialog setNegativeButtonBackground(@DrawableRes int resource) {
        Drawable drawable = ContextCompat.getDrawable(this.mContext, resource);
        return setNegativeButtonBackground(drawable);
    }

    public CustomDialog setPositiveButtonBackground(Drawable drawable) {
        this.mPositiveButtonBackgroundDrawable = drawable;
        return this;
    }

    public CustomDialog setNegativeButtonBackground(Drawable drawable) {
        this.mNegativeButtonBackgroundDrawable = drawable;
        return this;
    }

    public CustomDialog setPositiveButtonBackgroundColor(@ColorInt int color) {
        this.mPositiveButtonBackgroundColor = color;
        return this;
    }

    public CustomDialog setNegativeButtonBackgroundColor(@ColorInt int color) {
        this.mNegativeButtonBackgroundColor = color;
        return this;
    }

    public CustomDialog setButtonEnabled(boolean enabled) {
        this.mButtonEnabled = enabled;
        return this;
    }

    public CustomDialog setNegativeButtonEnabled(boolean enabled) {
        this.mNegativeButtonEnabled = enabled;
        return this;
    }

    public CustomDialog setButtonTextColor(@ColorInt int color) {
        this.mButtonTextColor = color;
        return this;
    }

    public CustomDialog setDismissOnBackPress(boolean dismissOnBackPress) {
        this.dismissOnBackPress = dismissOnBackPress;
        return this;
    }

    public void onBackPressed() {
        if (this.dismissOnBackPress)
            super.onBackPressed();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!this.mTitleHeading.isEmpty()) {
            this.binding.heading.setVisibility(View.VISIBLE);
            this.binding.heading.setText(this.mTitleHeading);
        } else {
            this.binding.heading.setVisibility(View.GONE);
        }
        if (!this.mSubTitle.isEmpty()) {
            this.binding.subheading.setVisibility(View.VISIBLE);
            this.binding.subheading.setText(this.mSubTitle);
        } else {
            this.binding.subheading.setVisibility(View.GONE);
        }
        this.binding.heading.setTextSize(this.mTitleTextSize);
        this.binding.subheading.setTextSize(this.mSubTitleTextSize);
        if (this.mIcon != null) {
            this.binding.icon.setVisibility(View.VISIBLE);
            this.binding.icon.setImageDrawable(this.mIcon);
        } else if (this.binding.icon.getDrawable() != null) {
            this.binding.icon.setVisibility(View.VISIBLE);
        } else {
            this.binding.icon.setVisibility(View.GONE);
        }
        this.binding.positiveButton.setVisibility(this.mButtonEnabled ? View.VISIBLE : View.GONE);
        this.binding.negativeButton.setVisibility(this.mNegativeButtonEnabled ? View.VISIBLE : View.GONE);
        this.binding.positiveButton.setText(this.mPositiveButtontext);
        this.binding.negativeButton.setText(this.mNegativeButtontext);
        if (this.mButtonTextColor != 0) {
            this.binding.positiveButton.setTextColor(this.mButtonTextColor);
            this.binding.negativeButton.setTextColor(this.mButtonTextColor);
        }
        if (this.mPositiveButtonBackgroundColor != 0)
            this.binding.positiveButton.setBackgroundColor(this.mPositiveButtonBackgroundColor);
        if (this.mNegativeButtonBackgroundColor != 0)
            this.binding.negativeButton.setBackgroundColor(this.mNegativeButtonBackgroundColor);
        if (this.mPositiveButtonBackgroundDrawable != null)
            if (Build.VERSION.SDK_INT >= 16) {
                this.binding.positiveButton.setBackground(this.mPositiveButtonBackgroundDrawable);
            } else {
                this.binding.positiveButton.setBackgroundDrawable(this.mPositiveButtonBackgroundDrawable);
            }
        if (this.mNegativeButtonBackgroundDrawable != null)
            if (Build.VERSION.SDK_INT >= 16) {
                this.binding.negativeButton.setBackground(this.mNegativeButtonBackgroundDrawable);
            } else {
                this.binding.negativeButton.setBackgroundDrawable(this.mNegativeButtonBackgroundDrawable);
            }
        this.binding.positiveButton.setOnClickListener(this);
        this.binding.negativeButton.setOnClickListener(this);
    }

    public CustomDialog setPositiveButtonClickListener(CustomDialogButtonClickListener positiveButtonClickListener) {
        this.positiveButtonClickListener = positiveButtonClickListener;
        return this;
    }

    public CustomDialog setNegativeButtonClickListener(CustomDialogButtonClickListener negativeButtonClickListener) {
        this.negativeButtonClickListener = negativeButtonClickListener;
        return this;
    }

    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.positiveButton) {
            if (this.positiveButtonClickListener != null) {
                this.positiveButtonClickListener.onClick(this);
            } else {
                dismiss();
            }
        } else if (viewId == R.id.negativeButton) {
            if (this.negativeButtonClickListener != null) {
                this.negativeButtonClickListener.onClick(this);
            } else {
                dismiss();
            }
        }
    }

    public AppCompatImageView getIconview() {
        return this.binding.icon;
    }

    public CustomDialog addMoreViews(View view) {
        this.binding.extraViewContainer.addView(view);
        return this;
    }

    public static interface CustomDialogButtonClickListener {
        void onClick(CustomDialog param1CustomDialog);
    }

    public enum Type {
        SUCCESS_TYPE_DIALOG, ERROR_TYPE_DIALOG, WARNING_TYPE_DIALOG, NORMAL_TYPE_DIALOG, CUSTOM_TYPE_DIALOG;
    }
}
