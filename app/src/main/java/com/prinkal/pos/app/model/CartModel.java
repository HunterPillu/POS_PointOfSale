package com.prinkal.pos.app.model;

import com.prinkal.pos.app.BR;
import com.prinkal.pos.app.db.entity.Customer;
import com.prinkal.pos.app.db.entity.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class CartModel extends BaseObservable implements Serializable {
    private List<Product> products;
    private TotalModel totals;
    private Customer customer;

    public List<Product> getProducts() {
        if (products == null)
            products = new ArrayList<>();
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public TotalModel getTotals() {
        if (totals == null)
            totals = new TotalModel();
        return totals;
    }

    public void setTotals(TotalModel totals) {
        this.totals = totals;
    }

    @Bindable
    public Customer getCustomer() {
        if (customer == null)
            customer = new Customer();
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        notifyPropertyChanged(BR.customer);
    }
}
