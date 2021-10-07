package com.example.snowtrails.room.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.snowtrails.room.entities.AuthenticatedUser
import com.example.snowtrails.room.entities.User

@Dao
interface AuthenticatedUserDao {
    @Insert
    fun setAuthUser(AuthUser: AuthenticatedUser)

    @Delete
    fun deleteAuthUser(AuthUser: AuthenticatedUser)

    @Query("SELECT * FROM authenticateduser")
    fun getAll(): List<AuthenticatedUser>
}