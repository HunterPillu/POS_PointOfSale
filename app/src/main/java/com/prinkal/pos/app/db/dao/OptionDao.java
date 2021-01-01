package com.prinkal.pos.app.db.dao;

import com.prinkal.pos.app.db.entity.Options;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


@Dao
public interface OptionDao {

    @Query("SELECT * FROM Options ORDER BY option_id DESC")
    List<Options> getAll();

    @Query("SELECT * FROM Options WHERE option_id IN (:optionsIds)")
    List<Options> loadAllByIds(int[] optionsIds);

    @Query("UPDATE Options SET option_name = :optionName, option_type = :optionType, option_values = :optionValues WHERE option_id = :oId")
    void updateOptionsById(String optionName
            , String optionType
            , String optionValues
            , int oId);

    @Insert
    Long[] insertAll(Options... Optionss);

    @Delete
    void delete(Options Options);

    @Query("DELETE FROM Options")
    void delete();

}
