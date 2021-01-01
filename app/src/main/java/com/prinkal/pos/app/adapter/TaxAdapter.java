package com.prinkal.pos.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.databinding.ItemTaxBinding;
import com.prinkal.pos.app.db.entity.Tax;
import com.prinkal.pos.app.handlers.TaxActivityHandler;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class TaxAdapter extends RecyclerView.Adapter<TaxAdapter.ViewHolder> {
    private Context context;
    private List<Tax> taxes;

    public TaxAdapter(Context context, List<Tax> taxes) {
        this.context = context;
        this.taxes = taxes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.item_tax, null, false);
        return new TaxAdapter.ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setData(taxes.get(position));
        holder.binding.setHandler(new TaxActivityHandler(context));
    }

    @Override
    public int getItemCount() {
        return taxes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemTaxBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = ItemTaxBinding.bind(itemView);

        }
    }
}
