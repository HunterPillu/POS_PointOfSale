package com.prinkal.pos.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.databinding.ItemHoldCartBinding;
import com.prinkal.pos.app.db.entity.HoldCart;
import com.prinkal.pos.app.handlers.HoldCartItemHandler;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class HoldCartAdapter extends RecyclerView.Adapter<HoldCartAdapter.ViewHolder> {
    private Context context;
    private List<HoldCart> holdCartList;

    public HoldCartAdapter(Context context, List<HoldCart> holdCartList) {
        this.context = context;
        this.holdCartList = holdCartList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.item_hold_cart, null, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setData(holdCartList.get(position));
        holder.binding.setHandler(new HoldCartItemHandler(context));
    }

    @Override
    public int getItemCount() {
        return holdCartList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemHoldCartBinding binding;
        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView.getRootView());
        }
    }
}