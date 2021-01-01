package com.prinkal.pos.app.customviews;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import android.widget.RelativeLayout;

import com.prinkal.pos.app.R;

import java.util.Timer;
import java.util.TimerTask;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class AutoScrollableView extends RelativeLayout {
  private Context mContext;
  
  public MyCustomViewPager myCustomViewPager;
  
  public LinearLayoutCompat linearLayout;
  
  private View[] dotList;
  
  private DetailOnPageChangeListener listener;
  
  private int seconds = 1;
  
  private boolean autoSrcoll = false;
  
  private RemindTask swipeAtInterval;
  
  private Timer timer;
  
  private int page;
  
  private String height;
  
  public AutoScrollableView(Context context) {
    this(context, (AttributeSet)null);
  }
  
  public AutoScrollableView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }
  
  public AutoScrollableView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initializeCustomView(context);
    initViewPager(attrs);
  }
  
  private void initializeCustomView(Context context) {
    this.mContext = context;
    this.myCustomViewPager = new MyCustomViewPager(this.mContext);
    this.myCustomViewPager.setId(View.generateViewId());
    this.linearLayout = new LinearLayoutCompat(this.mContext);
    this.linearLayout.setVisibility(View.GONE);
    setBackgroundColor(ContextCompat.getColor(this.mContext, R.color.colorPrimary));
    addView((View)this.myCustomViewPager);
  }
  
  void initViewPager(AttributeSet attrs) {
    TypedArray a = this.mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.AutoScrollableView, 0, 0);
    try {
      this.autoSrcoll = a.getBoolean(R.styleable.AutoScrollableView_autoSrcoll, false);
      this.seconds = a.getInt(R.styleable.AutoScrollableView_pageSwitchTime, 1);
      this.height = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_height");
    } finally {
      a.recycle();
    } 
  }
  
  public void setPageSwitchTime(int seconds) {
    this.seconds = seconds;
  }
  
  public int getPageSwitchTime() {
    return this.seconds;
  }
  
  public void setAutoSrcollEnable(boolean isAutoScrollEnable) {
    this.autoSrcoll = isAutoScrollEnable;
    if (this.autoSrcoll)
      pageSwitcher(); 
  }
  
  private void pageSwitcher() {
    if (this.myCustomViewPager.getAdapter() != null) {
      this.swipeAtInterval = new RemindTask(this.myCustomViewPager.getAdapter().getCount());
      this.timer = new Timer();
      this.timer.scheduleAtFixedRate(this.swipeAtInterval, 0L, (this.seconds * 1000));
    } 
  }
  
  public boolean isAutoSrcollEnable() {
    return this.autoSrcoll;
  }
  
  class RemindTask extends TimerTask {
    int noOfBanners;
    
    RemindTask(int noOfBanners) {
      this.noOfBanners = noOfBanners;
    }
    
    public void run() {
      ((Activity) AutoScrollableView.this.mContext).runOnUiThread(new Runnable() {
            public void run() {
              if (AutoScrollableView.this.page > AutoScrollableView.RemindTask.this.noOfBanners) {
                AutoScrollableView.this.timer.cancel();
              } else if (AutoScrollableView.this.page == AutoScrollableView.RemindTask.this.noOfBanners - 1) {
                AutoScrollableView.this.myCustomViewPager.setCurrentItem(0);
                AutoScrollableView.this.page = 0;
              } else {
                AutoScrollableView.this.myCustomViewPager.setCurrentItem(++AutoScrollableView.this.page);
              } 
            }
          });
    }
  }
  
  public void setAdapter(PagerAdapter pagerAdapter) {
    if (pagerAdapter != null) {
      this.myCustomViewPager.setAdapter(pagerAdapter);
      this.dotList = (View[])new AppCompatImageView[this.myCustomViewPager.getAdapter().getCount()];
      for (int i = 0; i < this.myCustomViewPager.getAdapter().getCount(); i++) {
        AppCompatImageView dotImage = new AppCompatImageView(this.mContext);
        this.dotList[i] = (View)dotImage;
        if (i == 0) {
          this.dotList[i].setBackgroundResource(R.drawable.selecteditem_dot);
        } else {
          this.dotList[i].setBackgroundResource(R.drawable.nonselecteditem_dot);
        } 
        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(10, 10);
        layoutParams.setMargins(10, 0, 10, 20);
        this.linearLayout.addView((View)dotImage, (ViewGroup.LayoutParams)layoutParams);
      } 
      RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2, -2);
      params.addRule(14);
      params.addRule(12);
      if (!this.height.contains("dip") && Integer.parseInt(this.height) != -1) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        (getLayoutParams()).height = metrics.widthPixels / 2;
        invalidate();
        Log.d("height", this.height + "");
      } 
      addView((View)this.linearLayout, (ViewGroup.LayoutParams)params);
      if (this.autoSrcoll) {
        pageSwitcher();
        this.autoSrcoll = false;
      } 
      this.listener = new DetailOnPageChangeListener();
      this.myCustomViewPager.addOnPageChangeListener(this.listener);
    } 
  }
  
  public MyCustomViewPager getMyCustomViewPager() {
    return this.myCustomViewPager;
  }
  
  public void showBullets() {
    this.linearLayout.setVisibility(VISIBLE);
  }
  
  public class DetailOnPageChangeListener implements ViewPager.OnPageChangeListener {
    private int currentPage;
    
    public void onPageSelected(int position) {
      this.currentPage = position;
      for (int i = 0; i < AutoScrollableView.this.myCustomViewPager.getAdapter().getCount(); i++) {
        if (i == position) {
          AutoScrollableView.this.dotList[i].setBackgroundResource(R.drawable.selecteditem_dot);
        } else {
          AutoScrollableView.this.dotList[i].setBackgroundResource(R.drawable.nonselecteditem_dot);
        } 
      } 
    }
    
    public int getCurrentPage() {
      return this.currentPage;
    }
    
    public void onPageScrollStateChanged(int arg0) {}
    
    public void onPageScrolled(int arg0, float Offset, int positionOffsetPixels) {
      if (Offset > 0.5F);
    }
  }
}
