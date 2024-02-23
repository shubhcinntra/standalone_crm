package com.cinntra.standalone.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cinntra.standalone.model.ContactPersonData;
import com.cinntra.standalone.model.CountryData;

import java.util.List;

@Dao
public interface DaoBusinessPartnerEmployeeAll {
    @Query("SELECT * FROM data_business_partner_employee_all")
    List<ContactPersonData> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ContactPersonData> data);


}
