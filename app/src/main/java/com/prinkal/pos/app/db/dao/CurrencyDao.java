package com.prinkal.pos.app.db.dao;

import com.prinkal.pos.app.db.entity.Currency;

import java.util.List;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

public interface CurrencyDao {

    @Query("SELECT * FROM Currency")
    List<Currency> getAll();

    @Query("SELECT * FROM Currency WHERE code IN (:currencyCode)")
    List<Currency> loadAllByIds(int[] currencyCode);

    @Query("UPDATE Currency SET rate = :rate WHERE code = :currencyCode")
    void updateCurrencyById(String rate, int currencyCode);

    @Insert
    void insertAll(Currency... Currencys);

    @Delete
    void delete(Currency Currency);

    @Query("DELETE FROM Currency")
    void delete();

}
