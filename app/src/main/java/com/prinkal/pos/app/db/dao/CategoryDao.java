package com.prinkal.pos.app.db.dao;

import com.prinkal.pos.app.db.entity.Category;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM Category")
    List<Category> getAll();

    @Query("SELECT * FROM Category WHERE cId IN (:CategoryIds)")
    List<Category> loadAllByIds(int[] CategoryIds);

    @Query("SELECT * FROM Category WHERE is_include_in_drawer_menu = :isIncludeInDrawerMenu AND is_active = :isActive")
    List<Category> getCategoryIncludedInDrawerMenu(boolean isIncludeInDrawerMenu, boolean isActive);

    @Query("UPDATE Category SET category_name = :categoryName, is_active = :isActive, is_include_in_drawer_menu = :isIncludeInDrawerMenu WHERE cId = :cId")
    void updateCategoryById(String categoryName, boolean isActive, boolean isIncludeInDrawerMenu, int cId);

    @Insert
    void insertAll(Category... Categorys);

    @Delete
    void delete(Category Category);

    @Query("DELETE FROM Category")
    void delete();


}
