package com.prinkal.pos.app.db.entity;

import com.prinkal.pos.app.db.converters.DataConverter;
import com.prinkal.pos.app.model.CartModel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
public class HoldCart {
    @PrimaryKey(autoGenerate = true)
    private long holdCartId;

    @ColumnInfo(name = "time")
    private String time;
    @ColumnInfo(name = "date")
    private String date;

    @TypeConverters(DataConverter.class)
    @ColumnInfo(name = "cart_data")
    private CartModel cartData;

    @ColumnInfo(name = "qty")
    private String qty;

    @ColumnInfo(name = "is_synced")
    private String isSynced;

    @Ignore //@ColumnInfo(name = "u_id")
    private String uId;

    public String getUId() {
        return uId;
    }

    public void setUId(String uId) {
        this.uId = uId;
    }

    public String getIsSynced() {
        return isSynced;
    }

    public void setIsSynced(String isSynced) {
        this.isSynced = isSynced;
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

    public long getHoldCartId() {
        return holdCartId;
    }

    public void setHoldCartId(long holdCartId) {
        holdCartId = holdCartId + 12000;
        this.holdCartId = holdCartId;
    }
}
