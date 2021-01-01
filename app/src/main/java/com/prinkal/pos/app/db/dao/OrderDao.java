package com.prinkal.pos.app.db.dao;

import com.prinkal.pos.app.db.entity.OrderEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface OrderDao {
    @Query("SELECT * FROM OrderEntity ORDER BY orderId DESC")
    List<OrderEntity> getAll();

    @Query("SELECT * FROM OrderEntity WHERE orderId IN (:OrderId)")
    OrderEntity loadByIds(int OrderId);

    @Query("SELECT * FROM OrderEntity WHERE orderId LIKE (:searchText)")
    List<OrderEntity> getSearchOrders(String searchText);

    @Query("UPDATE OrderEntity SET refunded_order_id = :currentOrderId WHERE orderId = :returnedOrderId")
    void updateRefundedOrderId(String currentOrderId, int returnedOrderId);

    @Insert
    long[] insertAll(OrderEntity... orderEntities);

//    @Delete
//    void delete(OrderEntity OrderEntity);

    @Query("DELETE FROM OrderEntity")
    void delete();

}
