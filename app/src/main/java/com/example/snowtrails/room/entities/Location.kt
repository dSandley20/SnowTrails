package com.example.snowtrails.room.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Location(
    @PrimaryKey val id : Int,
    val name: String,
    val country : String,
    val city : String,
    val state: String,
    val zipcode : String,
    val locationImage: Int
) : Parcelable {

}