package com.prinkal.pos.app.db.entity;

import com.prinkal.pos.app.db.converters.DataConverter;
import com.prinkal.pos.app.model.CartModel;
import com.prinkal.pos.app.model.CashModel;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "OrderEntity")
public class OrderEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long orderId;

    @ColumnInfo(name = "time")
    private String time;
    @ColumnInfo(name = "date")
    private String date;

    @TypeConverters(DataConverter.class)
    @ColumnInfo(name = "cart_data")
    private CartModel cartData;

    @ColumnInfo(name = "qty")
    private String qty;

    @TypeConverters(DataConverter.class)
    @ColumnInfo(name = "cash_data")
    private CashModel cashData;

    @ColumnInfo(name = "is_synced")
    private String isSynced;

    @ColumnInfo(name = "is_return")
    private boolean isReturn;

    @ColumnInfo(name = "refunded_order_id")
    private String refundedOrderId;

    public String getIsSynced() {
        return isSynced;
    }

    public void setIsSynced(String isSynced) {
        this.isSynced = isSynced;
    }

    public CashModel getCashData() {
        return cashData;
    }

    public void setCashData(CashModel cashData) {
        this.cashData = cashData;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public CartModel getCartData() {
        return cartData;
    }

    public void setCartData(CartModel cartData) {
        this.cartData = cartData;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        orderId = orderId + 10000;
        this.orderId = orderId;
    }

    public boolean getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(boolean isReturn) {
        this.isReturn = isReturn;
    }

    public String getRefundedOrderId() {
        if (refundedOrderId == null)
            return "";
        return refundedOrderId;
    }

    public void setRefundedOrderId(String refundedOrderId) {
        this.refundedOrderId = refundedOrderId;
    }
}
