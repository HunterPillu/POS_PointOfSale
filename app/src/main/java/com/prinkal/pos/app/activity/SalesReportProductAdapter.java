package com.prinkal.pos.app.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.databinding.ItemProductSalesReportBinding;
import com.prinkal.pos.app.model.SalesProductReportModel;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class SalesReportProductAdapter extends RecyclerView.Adapter<SalesReportProductAdapter.ViewHolder> {
    private Context context;
    private List<SalesProductReportModel> soldProducts;

    public SalesReportProductAdapter(Context context, List<SalesProductReportModel> soldProducts) {
        this.context = context;
        this.soldProducts = soldProducts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.item_product_sales_report, null, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setData(soldProducts.get(position));
    }

    @Override
    public int getItemCount() {
        return soldProducts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemProductSalesReportBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }


}
