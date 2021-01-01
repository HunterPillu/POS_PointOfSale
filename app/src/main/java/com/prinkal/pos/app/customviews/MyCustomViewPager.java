package com.prinkal.pos.app.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import static android.view.View.MeasureSpec.UNSPECIFIED;

public class MyCustomViewPager extends ViewPager {
  Context context;
  
  private boolean isAutoMeasure = false;
  
  public MyCustomViewPager(Context context) {
    super(context);
    this.context = context;
  }
  
  public MyCustomViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
  }
  
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    if (this.isAutoMeasure) {
      View child = getChildAt(getCurrentItem());
      if (child != null) {
        child.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, UNSPECIFIED));
        int h = child.getMeasuredHeight();
        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY);
      } 
    } 
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }
  
  public void autoMeasureEnabled(boolean isAutoMeasure) {
    this.isAutoMeasure = isAutoMeasure;
  }
}
