package com.prinkal.pos.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.databinding.ItemOptionValueBinding;
import com.prinkal.pos.app.db.entity.OptionValues;
import com.prinkal.pos.app.handlers.OptionHandler;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


public class OptionValuesAdapter extends RecyclerView.Adapter<OptionValuesAdapter.ViewHolder> {
    private Context context;
    private List<OptionValues> optionValues;

    public OptionValuesAdapter(Context context, List<OptionValues> optionValues) {
        this.context = context;
        this.optionValues = optionValues;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.item_option_value, null, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setData(optionValues.get(position));
        holder.binding.setHandler(new OptionHandler(context));
    }

    @Override
    public int getItemCount() {
        return optionValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemOptionValueBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
