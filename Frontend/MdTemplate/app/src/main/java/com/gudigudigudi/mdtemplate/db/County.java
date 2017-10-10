package com.gudigudigudi.mdtemplate.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by gu on 10/9/17.
 */

/**
 * CREATE TABLE county (
 * id INTEGER PRIMARY KEY AUTOINCREMENT,
 * name TEXT,
 * weatherId TEXT,
 * cityId INTEGER );
 */

//@Entity(foreignKeys = {
//        @ForeignKey(entity = City.class,
//                parentColumns = "id",
//                childColumns = "cityId",
//                onDelete = ForeignKey.CASCADE)
//})
@Entity
public class County {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "weatherId")
    private String weatherId;
    @ColumnInfo(name = "cityId")
    private int cityId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
