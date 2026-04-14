package com.example.photogalleryapp

import com.example.photogalleryapp.Photo
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView

class PhotoAdapter(
    private val context: Context,
    private var photoList: MutableList<Photo>
) : BaseAdapter() {

    var isSelectionMode = false

    override fun getCount(): Int {
        return photoList.size
    }

    override fun getItem(position: Int): Any {
        return photoList[position]
    }

    override fun getItemId(position: Int): Long {
        return photoList[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_photo, parent, false)

        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val titleText = view.findViewById<TextView>(R.id.titleText)
        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)

        val photo = photoList[position]

        imageView.setImageResource(photo.resourceId)
        titleText.text = photo.title

        // Checkbox logic
        if (isSelectionMode) {
            checkBox.visibility = View.VISIBLE
            checkBox.isChecked = photo.isSelected
        } else {
            checkBox.visibility = View.GONE
        }

        return view
    }

    // Update list (for filter/delete)
    fun updateList(newList: MutableList<Photo>) {
        photoList = newList
        notifyDataSetChanged()
    }
}