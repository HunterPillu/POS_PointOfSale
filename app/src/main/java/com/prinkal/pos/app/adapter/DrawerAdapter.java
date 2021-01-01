package com.prinkal.pos.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.prinkal.pos.app.databinding.NavDrawerItemsBinding;
import com.prinkal.pos.app.db.entity.Category;
import com.prinkal.pos.app.handlers.MainActivityHandler;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {

    private Context context;
    private List<Category> categories;

    public DrawerAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        NavDrawerItemsBinding binding = NavDrawerItemsBinding.inflate(inflater, parent, false);
        return new DrawerAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(DrawerAdapter.ViewHolder holder, int position) {
        holder.binding.setData(categories.get(position));
//        holder.binding.label.se`tOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "aasss", Toast.LENGTH_SHORT).show();
//            }
//        });
        holder.binding.setMainActivityHandler(new MainActivityHandler(context));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final NavDrawerItemsBinding binding;

        public ViewHolder(NavDrawerItemsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}