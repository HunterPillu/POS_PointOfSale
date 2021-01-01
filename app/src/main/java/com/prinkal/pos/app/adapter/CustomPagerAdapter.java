package com.prinkal.pos.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.activity.MainActivity;
import com.prinkal.pos.app.activity.WalkthroughActivity;
import com.prinkal.pos.app.customviews.AutoScrollableView;
import com.prinkal.pos.app.helper.AppSharedPref;

import androidx.viewpager.widget.PagerAdapter;

public class CustomPagerAdapter extends PagerAdapter {

    Context context;
    LayoutInflater mLayoutInflater;
    private int[] mResources;
    private int[] heading;
    private int[] subheading;
    AutoScrollableView autoScrollableView;

    public CustomPagerAdapter(Context context, int[] mResources, int[] heading, int[] subheading) {
        this.heading = heading;
        this.subheading = subheading;
        this.context = context;
        this.mResources = mResources;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        autoScrollableView = ((WalkthroughActivity) context).findViewById(R.id.viewpager);
    }

    public CustomPagerAdapter(Context context, int[] mResources, int[] heading, int[] subheading, AutoScrollableView autoScrollableView) {
        this.heading = heading;
        this.subheading = subheading;
        this.context = context;
        this.mResources = mResources;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.autoScrollableView = autoScrollableView;
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.page_item, container, false);
        AppCompatImageView imageView = itemView.findViewById(R.id.imageView);
        imageView.setImageResource(mResources[position]);

        ((TextView) itemView.findViewById(R.id.heading)).setText(context.getString(heading[position]));
        ((TextView) itemView.findViewById(R.id.subheading)).setText(context.getString(subheading[position]));
        if (position == mResources.length - 1) {
            itemView.findViewById(R.id.done).setVisibility(View.VISIBLE);
        } else {
            itemView.findViewById(R.id.done).setVisibility(View.GONE);
        }

        itemView.findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSharedPref.setShowWalkThrough(context, true);
                Intent i = new Intent(context, MainActivity.class);
                context.startActivity(i);
                ((WalkthroughActivity) context).finish();
            }
        });

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}