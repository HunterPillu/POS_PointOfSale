package com.prinkal.pos.app.db.dao;

import com.prinkal.pos.app.db.entity.Customer;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CustomerDao {
    @Query("SELECT * FROM Customer")
    List<Customer> getAll();

    @Query("SELECT * FROM Customer WHERE customerId IN (:CustomerIds)")
    List<Customer> loadAllByIds(int[] CustomerIds);

    @Query("SELECT * FROM Customer WHERE email IN (:email)")
    Customer checkEmailExist(String email);

    @Query("SELECT * FROM Customer WHERE contact_number IN (:contactNumber)")
    Customer checkNumberExist(String contactNumber);

    @Query("UPDATE Customer SET customer_first_name = :firstName, customer_last_name = :lastName, email = :email, contact_number = :contactNumber, address_line = :addressLine, city = :city, postal_code = :postalCode, state = :state, country = :country WHERE customerId = :customerId")
    void updateCustomerById(String firstName, String lastName, String email, String contactNumber, String addressLine, String city, String postalCode, String state, String country, int customerId);

    @Insert
    void insertAll(Customer... Customers);

    @Delete
    void delete(Customer Customer);

    @Query("DELETE FROM Customer")
    void delete();

}
