package com.example.snowtrails.room.daos

import androidx.room.*
import com.example.snowtrails.room.entities.AuthenticatedUser
import com.example.snowtrails.room.entities.User

@Dao
interface AuthenticatedUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun setAuthUser(AuthUser: AuthenticatedUser)

    @Delete
    fun deleteAuthUser(AuthUser: AuthenticatedUser)

    @Query("SELECT * FROM authenticateduser")
    fun getAll(): List<AuthenticatedUser>
}