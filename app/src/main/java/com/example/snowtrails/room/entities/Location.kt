package com.example.snowtrails.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Location(
    @PrimaryKey val id : String,
    val name: String,
    val country : String,
    val city : String,
    val state: String,
    val zipcode : String
) {

}