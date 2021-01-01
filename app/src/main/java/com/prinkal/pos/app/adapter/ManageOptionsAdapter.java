package com.prinkal.pos.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.prinkal.pos.app.databinding.ItemManageOptionBinding;
import com.prinkal.pos.app.db.entity.Options;
import com.prinkal.pos.app.db.entity.Product;
import com.prinkal.pos.app.handlers.ManageOptionFragmentHandler;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ManageOptionsAdapter extends RecyclerView.Adapter<ManageOptionsAdapter.ViewHolder> {
    private static String TAG = "ManageOptionsAdapter";
    private Context context;
    private List<Options> options;
    private Product product;

    public ManageOptionsAdapter(Context context, List<Options> options, Product product) {
        this.context = context;
        this.options = options;
        this.product = product;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =
                LayoutInflater.from(parent.getContext());
        ItemManageOptionBinding binding = ItemManageOptionBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        if (product.getOptions().size() > 0 && product.getOptions().get(position).getOptionId() == options.get(position).getOptionId()) {
//            if (product.getOptions().get(position).isSelected())
//                options.get(position).setSelected(true);
//        }
        holder.bind(options.get(position));
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemManageOptionBinding binding;

        public ViewHolder(ItemManageOptionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Options item) {
            binding.setData(item);
            binding.setProduct(product);
            binding.setHandler(new ManageOptionFragmentHandler(context));
            binding.executePendingBindings();
        }
    }
}
