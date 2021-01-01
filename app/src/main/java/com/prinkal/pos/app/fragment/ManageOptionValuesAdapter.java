package com.prinkal.pos.app.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.prinkal.pos.app.databinding.ItemManageOptionValuesBinding;
import com.prinkal.pos.app.db.entity.OptionValues;
import com.prinkal.pos.app.handlers.ManageOptionFragmentHandler;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;



class ManageOptionValuesAdapter extends RecyclerView.Adapter<ManageOptionValuesAdapter.ViewHolder> {
    private Context context;
    private List<OptionValues> optionValues;

    public ManageOptionValuesAdapter(Context context, List<OptionValues> optionValues) {
        this.context = context;
        this.optionValues = optionValues;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =
                LayoutInflater.from(parent.getContext());
        ItemManageOptionValuesBinding binding = ItemManageOptionValuesBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(optionValues.get(position));
    }

    @Override
    public int getItemCount() {
        return optionValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemManageOptionValuesBinding binding;

        public ViewHolder(ItemManageOptionValuesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(OptionValues item) {
            binding.setData(item);
            binding.setHandler(new ManageOptionFragmentHandler(context));
            binding.executePendingBindings();
        }
    }
}
