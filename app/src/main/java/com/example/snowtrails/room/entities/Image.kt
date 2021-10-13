package com.example.snowtrails.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Image(
    @PrimaryKey val id: Int,
    val locationId : Int
) {
}