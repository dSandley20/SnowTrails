package com.example.snowtrails.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.snowtrails.room.daos.AuthenticatedUserDao
import com.example.snowtrails.room.entities.User
import com.example.snowtrails.room.daos.UserDao

@Database(version = 1, entities = [User::class])
abstract class  TrailsDatabase : RoomDatabase() {
    abstract  fun getUserDao() : UserDao
    abstract  fun getAuthUserDao(): AuthenticatedUserDao
}