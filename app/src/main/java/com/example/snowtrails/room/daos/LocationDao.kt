package com.example.snowtrails.room.daos

import androidx.room.*
import com.example.snowtrails.room.entities.Location

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insertAll ( vararg locations: Location)

    @Delete
     fun delete (location : Location)

    @Query("SELECT * FROM location")
     fun getAll(): List<Location>

     @Query("SELECT * FROM location WHERE id LIKE :id")
     fun getByID(id: String) : Location
}