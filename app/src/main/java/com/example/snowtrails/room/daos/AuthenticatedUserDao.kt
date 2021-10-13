package com.example.snowtrails.room.daos

import androidx.room.*
import com.example.snowtrails.room.entities.AuthenticatedUser
import com.example.snowtrails.room.entities.User

@Dao
interface AuthenticatedUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun setAuthUser(AuthUser: AuthenticatedUser)

    @Delete
    suspend fun deleteAuthUser(AuthUser: AuthenticatedUser)

    @Query("SELECT * FROM authenticateduser")
    suspend fun getAll(): List<AuthenticatedUser>
}