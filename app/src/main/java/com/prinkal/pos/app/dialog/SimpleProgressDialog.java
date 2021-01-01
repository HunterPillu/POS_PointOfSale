package com.prinkal.pos.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;

import com.prinkal.pos.app.databinding.DialogSimpleProgressBinding;

import androidx.annotation.NonNull;

public class SimpleProgressDialog extends Dialog {
  private Context mContext;
  
  private DialogSimpleProgressBinding binding;
  
  public SimpleProgressDialog(@NonNull Context context) {
    super(context);
    requestWindowFeature(1);
    this.binding = DialogSimpleProgressBinding.inflate(LayoutInflater.from(context), null, false);
    setContentView(this.binding.getRoot());
  }
  
  public static SimpleProgressDialog show(Context context, String title, String subTitle) {
    SimpleProgressDialog dialog = new SimpleProgressDialog(context);
    dialog.binding.heading.setText(title);
    dialog.binding.subheading.setText(subTitle);
    dialog.show();
    return dialog;
  }
}