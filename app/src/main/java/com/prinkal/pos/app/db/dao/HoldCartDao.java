package com.prinkal.pos.app.db.dao;

import com.prinkal.pos.app.db.entity.HoldCart;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface HoldCartDao {
    @Query("SELECT * FROM HoldCart ORDER BY holdCartId DESC")
    List<HoldCart> getAll();

    @Query("SELECT * FROM HoldCart WHERE holdCartId IN (:holdCartIds)")
    List<HoldCart> loadAllByIds(int[] holdCartIds);

    @Query("SELECT * FROM HoldCart WHERE holdCartId LIKE (:searchText)")
    List<HoldCart> getSearchHoldCart(String searchText);

    @Insert
    long[] insertAll(HoldCart... holdCarts);

    //    @Delete
//    void delete(HoldCart HoldCart);
//
    @Query("DELETE FROM HoldCart")
    void delete();

    @Query("DELETE FROM HoldCart WHERE holdCartId IN (:holdCartId)")
    void delete(long holdCartId);


}
