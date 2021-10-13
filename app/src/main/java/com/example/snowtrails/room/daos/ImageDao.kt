package com.example.snowtrails.room.daos

import androidx.room.*
import com.example.snowtrails.room.entities.Image
import com.example.snowtrails.room.entities.Location

@Dao
interface ImageDao {

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        fun insertAll ( vararg images: Image)

        @Delete
        fun delete (image : Image)

        @Query("SELECT * FROM image")
        fun getAll(): List<Image>

        @Query("SELECT * FROM image WHERE id LIKE :id")
        fun getByID(id: Int) : Image

        @Query("SELECT * FROM image WHERE locationId = :id")
        fun getByLocationID(id: Int) : Image

}