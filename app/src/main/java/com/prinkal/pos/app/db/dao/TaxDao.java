package com.prinkal.pos.app.db.dao;

import com.prinkal.pos.app.db.entity.Tax;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface TaxDao {
    @Query("SELECT * FROM Tax ORDER BY taxId DESC")
    List<Tax> getAll();

    @Query("SELECT * FROM Tax WHERE taxId IN (:TaxIds)")
    List<Tax> loadAllByIds(int[] TaxIds);

    @Query("SELECT * FROM Tax WHERE is_enabled = :isEnabled")
    List<Tax> getEnabledTax(boolean isEnabled);

    @Query("UPDATE Tax SET tax_name = :TaxName, is_enabled = :isActive, tax_rate = :taxRate WHERE taxId = :taxId")
    void updateTaxById(String TaxName, boolean isActive, String taxRate, int taxId);

    @Insert
    Long[] insertAll(Tax... Taxs);

    @Delete
    void delete(Tax Tax);

    @Query("DELETE FROM Tax")
    void delete();

}
