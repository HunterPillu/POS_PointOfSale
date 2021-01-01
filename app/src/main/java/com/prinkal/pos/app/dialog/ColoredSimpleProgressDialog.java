package com.prinkal.pos.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.appcompat.widget.LinearLayoutCompat;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.databinding.DialogProgressColoredBinding;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

public class ColoredSimpleProgressDialog extends Dialog {
  private final DialogProgressColoredBinding binding;
  
  private Context mContext;
  
  @ColorRes
  private int mBackGroundColorResource;
  
  @ColorRes
  private int mProgressBarColorResource;
  
  private Drawable mBackGroundDrawable;
  
  private Drawable mProgressBarDrawable;
  
  private Drawable mSeparatorDrawable;
  
  private String mTextViewText;
  
  private String mTitleText;
  
  private int mTextViewTextSize;
  
  private int mTitleTextSize;
  
  private int TEXT_VIEW_SCALE_TYPE = 0;
  
  private int TITLE_TEXT_VIEW_SCALE_TYPE = 0;
  
  private Drawable mTextBackgroundDrawable;
  
  private boolean mShowTitle;
  
  private boolean mShowTitleSeparator = true;
  
  public ColoredSimpleProgressDialog(@NonNull Context context) {
    this(context, false, (OnCancelListener)null);
  }
  
  public ColoredSimpleProgressDialog(@NonNull Context context, boolean cancelable, @Nullable DialogInterface.OnCancelListener cancelListener) {
    super(context, cancelable, cancelListener);
    requestWindowFeature(1);
    this.mContext = context;
    this.binding = DialogProgressColoredBinding.inflate(LayoutInflater.from(context), null, false);
    setContentView(this.binding.getRoot());
  }
  
  public ColoredSimpleProgressDialog setBackGroundColor(@ColorRes int color) {
    this.mBackGroundColorResource = color;
    return this;
  }
  
  public ColoredSimpleProgressDialog setBackGroundColor(Color color) {
    this.mBackGroundDrawable = (Drawable)new ColorDrawable(Color.parseColor(color.toString()));
    return this;
  }
  
  public ColoredSimpleProgressDialog setBackGroundColor(String color) {
    this.mBackGroundDrawable = (Drawable)new ColorDrawable(Color.parseColor(color));
    return this;
  }
  
  public ColoredSimpleProgressDialog setBackGroundDrawable(@DrawableRes int drawableInt) {
    this.mBackGroundDrawable = ContextCompat.getDrawable(this.mContext, drawableInt);
    return this;
  }
  
  public ColoredSimpleProgressDialog setBackGroundDrawable(Drawable drawable) {
    this.mBackGroundDrawable = drawable;
    return this;
  }
  
  public ColoredSimpleProgressDialog setProgressBarColor(@ColorRes int color) {
    this.mProgressBarColorResource = color;
    return this;
  }
  
  public ColoredSimpleProgressDialog setProgressBarColor(Color color) {
    this.mProgressBarDrawable = (Drawable)new ColorDrawable(Color.parseColor(color.toString()));
    return this;
  }
  
  public ColoredSimpleProgressDialog setProgressBarColor(String color) {
    this.mProgressBarDrawable = (Drawable)new ColorDrawable(Color.parseColor(color));
    return this;
  }
  
  public ColoredSimpleProgressDialog setProgressBarDrawable(Drawable drawable) {
    this.mProgressBarDrawable = drawable;
    return this;
  }
  
  public ColoredSimpleProgressDialog setText(String text) {
    this.mTextViewText = text;
    return this;
  }
  
  public ColoredSimpleProgressDialog setText(@StringRes int text) {
    this.mTextViewText = this.mContext.getString(text);
    return this;
  }
  
  public ColoredSimpleProgressDialog setTitleText(String text) {
    this.mTitleText = text;
    return this;
  }
  
  public ColoredSimpleProgressDialog setTitleText(@StringRes int text) {
    this.mTitleText = this.mContext.getString(text);
    return this;
  }
  
  public ColoredSimpleProgressDialog setContentTextSize(int size) {
    this.mTextViewTextSize = size;
    return this;
  }
  
  public ColoredSimpleProgressDialog setContentTextSizeInSP(int size) {
    this.TEXT_VIEW_SCALE_TYPE = 2;
    this.mTextViewTextSize = size;
    return this;
  }
  
  public void setContentTextSizeResource(@DimenRes int size) {
    this.mTextViewTextSize = this.mContext.getResources().getDimensionPixelSize(size);
  }
  
  public ColoredSimpleProgressDialog setTitleTextSize(int size) {
    this.mTitleTextSize = size;
    return this;
  }
  
  public ColoredSimpleProgressDialog setTitleTextSizeWithUnit(int size, int unit) {
    switch (unit) {
      case 1:
        this.TITLE_TEXT_VIEW_SCALE_TYPE = 1;
        this.mTitleTextSize = size;
        return this;
      case 2:
        this.TITLE_TEXT_VIEW_SCALE_TYPE = 2;
        this.mTitleTextSize = size;
        return this;
      case 3:
        this.TITLE_TEXT_VIEW_SCALE_TYPE = 3;
        this.mTitleTextSize = size;
        return this;
      case 4:
        this.TITLE_TEXT_VIEW_SCALE_TYPE = 4;
        this.mTitleTextSize = size;
        return this;
      case 5:
        this.TITLE_TEXT_VIEW_SCALE_TYPE = 5;
        this.mTitleTextSize = size;
        return this;
    } 
    this.TITLE_TEXT_VIEW_SCALE_TYPE = 0;
    this.mTitleTextSize = size;
    return this;
  }
  
  public void setTitleTextSizeResource(@DimenRes int size) {
    this.mTitleTextSize = this.mContext.getResources().getDimensionPixelSize(size);
  }
  
  public ColoredSimpleProgressDialog setTextBackGroundColor(Color color) {
    this.mTextBackgroundDrawable = (Drawable)new ColorDrawable(Color.parseColor(color.toString()));
    return this;
  }
  
  public ColoredSimpleProgressDialog setTextBackGroundColor(String color) {
    this.mTextBackgroundDrawable = (Drawable)new ColorDrawable(Color.parseColor(color));
    return this;
  }
  
  public ColoredSimpleProgressDialog setTextBackGroundDrawable(@DrawableRes int drawableInt) {
    this.mTextBackgroundDrawable = ContextCompat.getDrawable(this.mContext, drawableInt);
    return this;
  }
  
  public ColoredSimpleProgressDialog setTextBackGroundDrawable(Drawable drawable) {
    this.mTextBackgroundDrawable = drawable;
    return this;
  }
  
  public ColoredSimpleProgressDialog showTitle(boolean show) {
    this.mShowTitle = show;
    return this;
  }
  
  public ColoredSimpleProgressDialog showTitleSeparator(boolean show) {
    this.mShowTitleSeparator = show;
    return this;
  }
  
  public ColoredSimpleProgressDialog setTitleAndContentSeparatorColor(Color color) {
    this.mSeparatorDrawable = (Drawable)new ColorDrawable(Color.parseColor(color.toString()));
    return this;
  }
  
  public ColoredSimpleProgressDialog setTitleAndContentSeparatorColor(String color) {
    this.mSeparatorDrawable = (Drawable)new ColorDrawable(Color.parseColor(color));
    return this;
  }
  
  public ColoredSimpleProgressDialog setTitleAndContentSeparatorColor(@ColorRes int color) {
    this.mSeparatorDrawable = (Drawable)new ColorDrawable(ContextCompat.getColor(this.mContext, color));
    return this;
  }
  
  public ProgressBar getProgressBar() {
    return this.binding.progressBar;
  }
  
  public TextView getContentView() {
    return this.binding.text;
  }
  
  public TextView getTitleView() {
    return this.binding.titleTextView;
  }
  
  public View getTitleSeparatorView() {
    return this.binding.titleAndContentSeparator;
  }
  
  public LinearLayoutCompat getTitleContainerview() {
    return this.binding.titleContainer;
  }
  
  public LinearLayoutCompat getContentContainerView() {
    return this.binding.contentContainer;
  }
  
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (this.mBackGroundColorResource != 0)
      this.binding.window.setBackgroundColor(ContextCompat.getColor(this.mContext, this.mBackGroundColorResource)); 
    if (this.mBackGroundDrawable != null)
      if (Build.VERSION.SDK_INT >= 16) {
        this.binding.window.setBackground(this.mBackGroundDrawable);
      } else {
        this.binding.window.setBackgroundDrawable(this.mBackGroundDrawable);
      }  
    if (this.mTextViewText != null)
      this.binding.text.setText(this.mTextViewText); 
    if (this.mTitleText != null) {
      this.binding.titleTextView.setText(this.mTitleText);
      this.mShowTitle = true;
    } 
    if (this.mShowTitle) {
      this.binding.titleContainer.setVisibility(View.VISIBLE);
    } else {
      this.binding.titleContainer.setVisibility(View.GONE);
    } 
    if (this.mShowTitleSeparator) {
      this.binding.titleAndContentSeparator.setVisibility(View.VISIBLE);
    } else {
      this.binding.titleAndContentSeparator.setVisibility(View.GONE);
    } 
    if (this.mTextViewTextSize != 0)
      this.binding.text.setTextSize(this.TEXT_VIEW_SCALE_TYPE, this.mTextViewTextSize); 
    if (this.mTitleTextSize != 0)
      this.binding.titleTextView.setTextSize(this.TITLE_TEXT_VIEW_SCALE_TYPE, this.mTitleTextSize); 
    if (this.mTextBackgroundDrawable != null)
      if (Build.VERSION.SDK_INT >= 16) {
        this.binding.text.setBackground(this.mTextBackgroundDrawable);
      } else {
        this.binding.text.setBackgroundDrawable(this.mTextBackgroundDrawable);
      }  
    if (this.mSeparatorDrawable != null)
      if (Build.VERSION.SDK_INT >= 16) {
        this.binding.titleAndContentSeparator.setBackground(this.mSeparatorDrawable);
      } else {
        this.binding.titleAndContentSeparator.setBackgroundDrawable(this.mSeparatorDrawable);
      }  
    boolean indeterminate = this.binding.progressBar.isIndeterminate();
    try {
      if (this.mProgressBarColorResource != 0)
        if (Build.VERSION.SDK_INT >= 23) {
          if (indeterminate) {
            this.binding.progressBar.setIndeterminateTintList(ColorStateList.valueOf(this.mContext.getResources().getColor(this.mProgressBarColorResource, null)));
          } else {
            this.binding.progressBar.setProgressTintList(ColorStateList.valueOf(this.mContext.getResources().getColor(this.mProgressBarColorResource, null)));
          } 
        } else if (Build.VERSION.SDK_INT >= 21) {
          if (indeterminate) {
            this.binding.progressBar.setIndeterminateTintList(ColorStateList.valueOf(this.mContext.getResources().getColor(this.mProgressBarColorResource)));
          } else {
            this.binding.progressBar.setProgressTintList(ColorStateList.valueOf(this.mContext.getResources().getColor(this.mProgressBarColorResource)));
          } 
        } else if (indeterminate) {
          this.binding.progressBar.getIndeterminateDrawable().setColorFilter(this.mContext.getResources().getColor(this.mProgressBarColorResource), PorterDuff.Mode.SRC_IN);
        } else {
          this.binding.progressBar.getProgressDrawable().setColorFilter(this.mContext.getResources().getColor(this.mProgressBarColorResource), PorterDuff.Mode.SRC_IN);
        }  
      if (this.mProgressBarDrawable != null)
        if (this.mProgressBarDrawable instanceof ColorDrawable) {
          if (indeterminate) {
            this.binding.progressBar.getIndeterminateDrawable().setColorFilter(((ColorDrawable)this.mProgressBarDrawable).getColor(), PorterDuff.Mode.SRC_IN);
          } else {
            this.binding.progressBar.getProgressDrawable().setColorFilter(((ColorDrawable)this.mProgressBarDrawable).getColor(), PorterDuff.Mode.SRC_IN);
          } 
        } else if (indeterminate) {
          this.binding.progressBar.setIndeterminateDrawable(this.mProgressBarDrawable);
        } else {
          this.binding.progressBar.setProgressDrawable(this.mProgressBarDrawable);
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public static ColoredSimpleProgressDialog getIntstantProgressDialog(Context context) {
    ColoredSimpleProgressDialog coloredSimpleProgressDialog = new ColoredSimpleProgressDialog(context);
    coloredSimpleProgressDialog.showTitle(true);
    coloredSimpleProgressDialog.showTitleSeparator(true);
    coloredSimpleProgressDialog.setTitleText(R.string.progress_dialog_title_text);
    coloredSimpleProgressDialog.setText(R.string.progress_dialog_content_text);
    coloredSimpleProgressDialog.setCanceledOnTouchOutside(true);
    coloredSimpleProgressDialog.setCancelable(true);
    return coloredSimpleProgressDialog;
  }
}
