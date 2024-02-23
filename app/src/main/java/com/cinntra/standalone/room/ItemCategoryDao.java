package com.cinntra.standalone.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cinntra.standalone.model.ItemCategoryData;
import com.cinntra.standalone.model.LeadTypeData;

import java.util.List;

@Dao
public interface ItemCategoryDao {
    @Query("SELECT * FROM table_item_category")
    List<ItemCategoryData> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ItemCategoryData> data);



}
