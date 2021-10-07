package com.example.snowtrails.utils

import android.content.Context
import androidx.room.Room
import com.example.snowtrails.api.Api
import com.example.snowtrails.room.TrailsDatabase

class getDatabase() {
    fun returnDB(context: Context): TrailsDatabase {
        return Room.databaseBuilder(context, TrailsDatabase::class.java, "trailsDB").allowMainThreadQueries().build()
    }
}