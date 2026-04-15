package com.example.universityeventapp

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class SeatAdapter(
    private val context: Context,
    private val seats: MutableList<String>
) : BaseAdapter() {

    override fun getCount(): Int = seats.size

    override fun getItem(position: Int): Any = seats[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val tv = TextView(context)


        val params = ViewGroup.MarginLayoutParams(120, 120)
        params.setMargins(8, 8, 8, 8)
        tv.layoutParams = params


        tv.text = "S${position + 1}"
        tv.textSize = 14f
        tv.setTypeface(null, Typeface.BOLD)
        tv.setTextColor(Color.WHITE)
        tv.textAlignment = View.TEXT_ALIGNMENT_CENTER


        when (seats[position]) {
            "Available" -> tv.setBackgroundColor(Color.parseColor("#4CAF50"))
            "Booked" -> tv.setBackgroundColor(Color.parseColor("#F44336"))
            "Selected" -> tv.setBackgroundColor(Color.parseColor("#2196F3"))
        }

        return tv
    }
}