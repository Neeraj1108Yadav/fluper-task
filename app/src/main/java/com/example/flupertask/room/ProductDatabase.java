package com.example.flupertask.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Product.class},version = 1,exportSchema = false)
@TypeConverters(Converters.class)
public abstract class ProductDatabase extends RoomDatabase {

    public abstract ProductAccess productAccess();
}
