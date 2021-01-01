package com.prinkal.pos.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prinkal.pos.app.databinding.ManageCategoryItemBinding;
import com.prinkal.pos.app.db.entity.Category;
import com.prinkal.pos.app.db.entity.Product;
import com.prinkal.pos.app.handlers.ManageCategoriesFragmentHandler;
import com.prinkal.pos.app.model.ProductCategoryModel;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ManageCategoryAdapter extends RecyclerView.Adapter<ManageCategoryAdapter.ViewHolder> {
    private Context context;
    private List<Category> categories;
    private Product product;

    public ManageCategoryAdapter(Context context, List<Category> categories, Product product) {
        this.context = context;
        this.categories = categories;
        this.product = product;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =
                LayoutInflater.from(parent.getContext());
        ManageCategoryItemBinding binding = ManageCategoryItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(categories.get(position));
        if (product != null && !product.getProductCategories().isEmpty())
            for (ProductCategoryModel pro : product.getProductCategories()) {
                if (categories.get(position).getCId() == Integer.parseInt(pro.getcId())) {
                    holder.binding.selectedCategory.setVisibility(View.VISIBLE);
                    ManageCategoriesFragmentHandler.categoryHashMap.put(pro.getcId(), categories.get(position));
                    break;
                } else {
                    holder.binding.selectedCategory.setVisibility(View.GONE);
                }
            }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ManageCategoryItemBinding binding;

        public ViewHolder(ManageCategoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Category item) {
            binding.setData(item);
            binding.setHandler(new ManageCategoriesFragmentHandler(context, binding));
            binding.executePendingBindings();
        }
    }
}
