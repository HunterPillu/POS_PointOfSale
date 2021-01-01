package com.prinkal.pos.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.databinding.ItemOptionBinding;
import com.prinkal.pos.app.db.entity.Options;
import com.prinkal.pos.app.handlers.OptionHandler;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.ViewHolder> {
    private Context context;
    private List<Options> options;

    public OptionsAdapter(Context context, List<Options> options) {
        this.context = context;
        this.options = options;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.item_option, null, false);
        return new OptionsAdapter.ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setData(options.get(position));
        holder.binding.setHandler(new OptionHandler(context));
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemOptionBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = ItemOptionBinding.bind(itemView);
        }
    }
}
