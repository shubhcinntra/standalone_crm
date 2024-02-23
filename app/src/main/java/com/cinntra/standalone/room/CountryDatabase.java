package com.cinntra.standalone.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.cinntra.standalone.model.Countries;
import com.cinntra.standalone.model.CountryData;

@Database(entities = {Countries.class},version = 1)
public abstract class CountryDatabase extends RoomDatabase {
    public abstract CountryDao myDataDao();
}
