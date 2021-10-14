package com.example.snowtrails.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.example.snowtrails.R
import com.example.snowtrails.room.entities.Image
import com.squareup.picasso.Picasso

class LocationImageAdapter(var list: List<Image>, var ctx: Context) : PagerAdapter() {
    private lateinit var ImgList: List<String>
    lateinit var layoutInflater: LayoutInflater
    lateinit var context: Context

    override fun getCount(): Int {
        return list.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(ctx)
        var view = layoutInflater.inflate(R.layout.location_image_item,container,false)
        val imageView = view.findViewById(R.id.ItemImage) as ImageView
        val imageId = list[position].id
        Picasso.get().invalidate("http://10.0.2.2:8080/images/$imageId")
        Picasso.get().load("http://10.0.2.2:8080/images/$imageId").placeholder(R.drawable.ic_launcher_background).into(imageView)
        container.addView(view,0)
        return view


    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view.equals(`object`)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}