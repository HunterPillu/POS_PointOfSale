package com.prinkal.pos.app.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.databinding.ItemCashDrawerBinding;
import com.prinkal.pos.app.db.entity.CashDrawerModel;
import com.prinkal.pos.app.handlers.CashDrawerHandler;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

class CashDrawerAdapter extends RecyclerView.Adapter<CashDrawerAdapter.ViewHolder> {
    private Context context;
    private List<CashDrawerModel> cashDrawerModelsList;

    public CashDrawerAdapter(Context context, List<CashDrawerModel> cashDrawerModelsList) {
        this.context = context;
        this.cashDrawerModelsList = cashDrawerModelsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.item_cash_drawer, null, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setData(cashDrawerModelsList.get(position));
        holder.binding.setHandler(new CashDrawerHandler(context));
    }

    @Override
    public int getItemCount() {
        return cashDrawerModelsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemCashDrawerBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = ItemCashDrawerBinding.bind(itemView);
        }
    }
}
