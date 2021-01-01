package com.prinkal.pos.app.db.dao;

import com.prinkal.pos.app.db.entity.OptionValues;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface OptionValuesDao {

    @Query("SELECT * FROM OptionValues")
    List<OptionValues> getAll();

    @Query("SELECT * FROM OptionValues WHERE option_id IN (:optionsIds)")
    List<OptionValues> loadAllByIds(int[] optionsIds);

//    @Query("UPDATE OptionValues SET category_name = :categoryName, is_active = :isActive, is_include_in_drawer_menu = :isIncludeInDrawerMenu WHERE cId = :cId")
//    void updateOptionValuesById(String categoryName, boolean isActive, boolean isIncludeInDrawerMenu, int cId);

    @Insert
    Long[] insertAll(OptionValues... OptionValuess);

    @Delete
    void delete(OptionValues OptionValues);

    @Query("DELETE FROM OptionValues")
    void delete();
}
