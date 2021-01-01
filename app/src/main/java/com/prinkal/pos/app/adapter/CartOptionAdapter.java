package com.prinkal.pos.app.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.databinding.ProductOptionsLayoutBinding;
import com.prinkal.pos.app.db.entity.Options;
import com.prinkal.pos.app.helper.Helper;

import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

class CartOptionAdapter extends RecyclerView.Adapter<CartOptionAdapter.ViewHolder> {
    private Context context;
    private List<Options> options;

    public CartOptionAdapter(Context context, List<Options> options) {
        this.context = context;
        this.options = options;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.product_options_layout, null, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setData(options.get(position));
        holder.binding.setPosition(position);
        for (int i = 0; i < options.get(position).getOptionValues().size(); i++) {
            if (options.get(position).getOptionValues().get(i).isAddToCart()) {
                TextView tv = new TextView(context);
                if (!options.get(position).getType().equalsIgnoreCase("text") && !options.get(position).getOptionValues().get(i).getOptionValuePrice().equalsIgnoreCase("")) {
                    tv.setText(options.get(position).getOptionValues().get(i).getOptionValueName() + " (" + Helper.currencyFormater(Helper.currencyConverter(Double.parseDouble(options.get(position).getOptionValues().get(i).getOptionValuePrice()), context), context)+ ")");
                } else
                    tv.setText(options.get(position).getOptionValues().get(i).getOptionValueName());
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
                holder.binding.optionValueLl.addView(tv);
            }
        }
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ProductOptionsLayoutBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
