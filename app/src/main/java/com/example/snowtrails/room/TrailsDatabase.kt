package com.example.snowtrails.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.snowtrails.room.daos.AuthenticatedUserDao
import com.example.snowtrails.room.entities.User
import com.example.snowtrails.room.daos.UserDao
import com.example.snowtrails.room.entities.AuthenticatedUser

@Database(version = 1, entities = [User::class , AuthenticatedUser::class])
abstract class  TrailsDatabase : RoomDatabase() {
    abstract  fun getUserDao() : UserDao
    abstract  fun getAuthUserDao(): AuthenticatedUserDao
}