package com.prinkal.pos.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.prinkal.pos.app.databinding.ProductCategoriesBinding;
import com.prinkal.pos.app.model.ProductCategoryModel;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.ViewHolder> {
    private Context context;
    private List<ProductCategoryModel> productCategories;

    public ProductCategoryAdapter(Context context, List<ProductCategoryModel> productCategories) {
        this.context = context;
        this.productCategories = productCategories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =
                LayoutInflater.from(parent.getContext());
        ProductCategoriesBinding binding = ProductCategoriesBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setData(productCategories.get(position));
    }

    @Override
    public int getItemCount() {
        return productCategories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ProductCategoriesBinding binding;

        public ViewHolder(ProductCategoriesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
