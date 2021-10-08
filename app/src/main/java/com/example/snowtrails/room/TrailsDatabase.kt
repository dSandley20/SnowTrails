package com.example.snowtrails.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.snowtrails.room.daos.AuthenticatedUserDao
import com.example.snowtrails.room.daos.LocationDao
import com.example.snowtrails.room.entities.User
import com.example.snowtrails.room.daos.UserDao
import com.example.snowtrails.room.entities.AuthenticatedUser
import com.example.snowtrails.room.entities.Location

@Database(version = 1, entities = [User::class , AuthenticatedUser::class, Location::class])
abstract class  TrailsDatabase : RoomDatabase() {
    abstract  fun getUserDao() : UserDao
    abstract  fun getAuthUserDao(): AuthenticatedUserDao
    abstract  fun getLocationDao() : LocationDao
}