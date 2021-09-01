package com.example.snowtrails.room.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.snowtrails.room.entities.User

@Dao
interface UserDao {
    @Insert
    fun insertAll (vararg users : User)

    @Delete
    fun delete (user : User)

    @Query("SELECT * FROM user")
    fun getAll(): List<User>
}