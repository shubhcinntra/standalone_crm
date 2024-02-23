package com.cinntra.standalone.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cinntra.standalone.model.TaxItem;
import com.cinntra.standalone.model.UTypeData;

import java.util.List;

@Dao
public interface TaxItemDao {
    @Query("SELECT * FROM table_Tax")
    List<TaxItem> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TaxItem> data);



}
