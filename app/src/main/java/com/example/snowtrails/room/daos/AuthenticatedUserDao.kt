package com.example.snowtrails.room.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.example.snowtrails.room.entities.AuthenticatedUser

@Dao
interface AuthenticatedUserDao {
    @Insert
    fun setAuthUser(AuthUser: AuthenticatedUser)

    @Delete
    fun deleteAuthUser(AuthUser: AuthenticatedUser)
}