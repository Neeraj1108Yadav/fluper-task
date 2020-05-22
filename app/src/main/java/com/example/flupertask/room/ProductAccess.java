package com.example.flupertask.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductAccess {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertProducts(Product products); //insert multiple list record

    @Query("DELETE FROM Product WHERE id = :id")
    int deleteByProductId(int id);

    @Query("SELECT * FROM Product")
    List<Product> getAllProducts();

    @Update
    int updateProduct(Product products);

    @Delete
    int deleteProduct(Product products);
}
