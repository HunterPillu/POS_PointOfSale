package com.prinkal.pos.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.databinding.ItemCashDrawerHistoryBinding;
import com.prinkal.pos.app.model.CashDrawerItems;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class CashDrawerHistoryAdapter extends RecyclerView.Adapter<CashDrawerHistoryAdapter.ViewHolder> {
    private Context context;
    private List<CashDrawerItems> cashDrawerItems;

    public CashDrawerHistoryAdapter(Context context, List<CashDrawerItems> cashDrawerItems) {
        this.context = context;
        this.cashDrawerItems = cashDrawerItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.item_cash_drawer_history, null, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setData(cashDrawerItems.get(position));
    }

    @Override
    public int getItemCount() {
        return cashDrawerItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemCashDrawerHistoryBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = ItemCashDrawerHistoryBinding.bind(itemView);

        }
    }
}
