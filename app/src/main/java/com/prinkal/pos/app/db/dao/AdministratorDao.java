package com.prinkal.pos.app.db.dao;

import com.prinkal.pos.app.db.entity.Administrator;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AdministratorDao {
    @Query("SELECT * FROM Administrator")
    Administrator getAll();

    @Query("SELECT * FROM Administrator WHERE uid IN (:AdministratorIds)")
    List<Administrator> loadAllByIds(int[] AdministratorIds);

    @Query("SELECT * FROM Administrator WHERE email LIKE :email " +
            "AND password LIKE :password")
    Administrator findByEmail(String email, String password);

    @Query("UPDATE Administrator SET first_name = :firstName, last_name = :lastName, username = :username WHERE uid = :uId")
    void updateAdminById(String firstName, String lastName, String username, int uId);

    @Insert
    void insertAll(Administrator... Administrators);

    @Delete
    void delete(Administrator Administrator);
}