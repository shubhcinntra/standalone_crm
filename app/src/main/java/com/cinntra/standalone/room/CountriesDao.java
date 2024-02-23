package com.cinntra.standalone.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cinntra.standalone.model.Countries;
import com.cinntra.standalone.model.CountryData;

import java.util.List;

@Dao
public interface CountriesDao {
    @Query("SELECT * FROM data_country")
    List<CountryData> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CountryData> data);



}
