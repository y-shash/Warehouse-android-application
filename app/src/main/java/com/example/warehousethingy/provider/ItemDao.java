package com.example.warehousethingy.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao {

    @Query("select * from items")
    LiveData<List<ArrayForListItem>> getAllItem();

    @Query("select * from items where item=:name")
    List<ArrayForListItem> getItem(String name);

    @Insert
    void addItem(ArrayForListItem item);

    //@Query("delete from items where itemName= :name")
    //void deleteItem(String name);

    @Query("delete FROM items")
    void deleteAllItem();
}
