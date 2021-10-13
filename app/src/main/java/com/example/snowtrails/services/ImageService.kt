package com.example.snowtrails.services

import com.example.snowtrails.room.entities.Image
import com.example.snowtrails.room.entities.Location
import com.google.gson.JsonArray
import org.json.JSONArray
import java.util.*

class ImageService {

    fun createImageEntry(dataArray: JSONArray, locationId: Int): MutableList<Image> {
        val newImageArray: MutableList<Image> = mutableListOf()
        for (i in 0 until dataArray.length()) {
            val tempImage = dataArray.getJSONObject(i)
            newImageArray.add(Image(tempImage.get("id").toString().toInt(), locationId ))
        }

        return newImageArray
    }
}