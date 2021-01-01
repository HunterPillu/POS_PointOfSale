package com.prinkal.pos.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.databinding.ItemCustomerBinding;
import com.prinkal.pos.app.db.entity.Customer;
import com.prinkal.pos.app.handlers.CustomerHandler;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {

    private Context context;
    private List<Customer> customers;
    private Customer selectedCustomer;
    private boolean isChooseCustomer;

    public CustomerAdapter(Context context, List<Customer> customers, boolean isChooseCustomer) {
        this.context = context;
        this.customers = customers;
        this.isChooseCustomer = isChooseCustomer;
    }

    public CustomerAdapter(Context context, List<Customer> customers, Customer selectedCustomer, boolean isChooseCustomer) {
        this.context = context;
        this.customers = customers;
        this.selectedCustomer = selectedCustomer;
        this.isChooseCustomer = isChooseCustomer;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.item_customer, null, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setData(customers.get(position));
        holder.binding.setHandler(new CustomerHandler(context));
        holder.binding.setChooseCustomer(isChooseCustomer);
        if (selectedCustomer != null && selectedCustomer.getCustomerId() == customers.get(position).getCustomerId()) {
            holder.binding.selected.setVisibility(View.VISIBLE);
        } else
            holder.binding.selected.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemCustomerBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = ItemCustomerBinding.bind(itemView);
        }
    }
}
