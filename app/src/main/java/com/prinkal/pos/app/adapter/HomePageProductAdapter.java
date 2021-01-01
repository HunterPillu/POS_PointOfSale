package com.prinkal.pos.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.databinding.HomePageProductItemBinding;
import com.prinkal.pos.app.db.entity.Product;
import com.prinkal.pos.app.handlers.HomeFragmentHandler;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class HomePageProductAdapter extends RecyclerView.Adapter<HomePageProductAdapter.ViewHolder> {

    private Context context;
    private List<Product> products;
    private boolean isLowStock;

    public HomePageProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    public HomePageProductAdapter(Context context, List<Product> products, boolean isLowStock) {
        this.context = context;
        this.products = products;
        this.isLowStock = isLowStock;
    }

    @Override
    public HomePageProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.home_page_product_item, null, false);
        return new HomePageProductAdapter.ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(HomePageProductAdapter.ViewHolder holder, int position) {
        holder.binding.setData(products.get(position));
        if (isLowStock)
            holder.binding.lowTag.setVisibility(View.VISIBLE);
        else {
            holder.binding.lowTag.setVisibility(View.GONE);
            holder.binding.setHandler(new HomeFragmentHandler(context));
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final HomePageProductItemBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = HomePageProductItemBinding.bind(itemView);
        }
    }
}
