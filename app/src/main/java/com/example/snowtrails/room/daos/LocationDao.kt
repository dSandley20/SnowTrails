package com.example.snowtrails.room.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.snowtrails.room.entities.Location

@Dao
interface LocationDao {
    @Insert
     fun insertAll ( vararg locations: List<Location>)

    @Delete
     fun delete (location : Location)

    @Query("SELECT * FROM location")
     fun getAll(): List<Location>
}