package com.prinkal.pos.app.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.databinding.ItemsCategoryBinding;
import com.prinkal.pos.app.db.entity.Category;
import com.prinkal.pos.app.handlers.CategoryHandler;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private Context context;
    private List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.items_category, null, false);
        return new CategoryAdapter.ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setData(categories.get(position));
        holder.binding.setHandler(new CategoryHandler(context));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemsCategoryBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = ItemsCategoryBinding.bind(itemView);
        }
    }
}
