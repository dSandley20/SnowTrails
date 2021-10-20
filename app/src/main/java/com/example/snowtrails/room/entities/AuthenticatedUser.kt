package com.example.snowtrails.room.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class AuthenticatedUser(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    val email: String,
    @ColumnInfo(name = "user_name") val userName: String,
    val jwt: String
) : Parcelable {
}