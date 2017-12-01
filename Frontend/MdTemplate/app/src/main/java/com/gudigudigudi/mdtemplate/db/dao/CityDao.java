package com.gudigudigudi.mdtemplate.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.gudigudigudi.mdtemplate.db.model.City;

import java.util.List;

/**
 * Created by gu on 10/10/17.
 */

@Dao
public interface CityDao {

    @Query("SELECT * FROM city")
    List<City> getAll();

    @Query("SELECT * FROM city WHERE provinceId = :provinceId")
    List<City> getCityInProvince(int provinceId);

    @Insert
    void insert(City city);

    @Insert
    void insertAll(List<City> cities);
}