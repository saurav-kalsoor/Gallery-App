package com.example.galleryapp.viewPager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.example.galleryapp.R
import com.example.galleryapp.model.Image
import com.example.galleryapp.stringToBitMap
import java.util.*

class ViewPagerAdapter(private val context: Context) : PagerAdapter() {

    var imagesList =  listOf<Image>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val layoutInflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as ImageView
    }

    override fun getCount() = imagesList.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView : View = layoutInflater.inflate(R.layout.image_display_view_pager, container, false)
        val imageView : ImageView = itemView.findViewById(R.id.imageViewDisplay)
        val item : Image = imagesList[position]
        val imgBitmap = stringToBitMap(item.imageUrl)
        imageView.setImageBitmap(imgBitmap)
        Objects.requireNonNull(container).addView(itemView)
        return itemView
    }



    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ImageView)
    }


}