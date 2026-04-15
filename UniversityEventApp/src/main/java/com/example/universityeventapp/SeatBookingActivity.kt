package com.example.universityeventapp

import android.os.Bundle
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SeatBookingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat)

        val grid = findViewById<GridView>(R.id.gridView)
        val summary = findViewById<TextView>(R.id.txtSummary)
        val btnConfirm = findViewById<Button>(R.id.btnConfirm)


        val seats = MutableList(48) { "Available" }


        seats.indices.shuffled().take(15).forEach {
            seats[it] = "Booked"
        }


        val adapter = SeatAdapter(this, seats)
        grid.numColumns = 6
        grid.adapter = adapter


        grid.setOnItemClickListener { _, _, position, _ ->

            if (seats[position] == "Booked") return@setOnItemClickListener

            if (seats[position] == "Available") {
                seats[position] = "Selected"
            } else {
                seats[position] = "Available"
            }

            adapter.notifyDataSetChanged()

            val count = seats.count { it == "Selected" }
            summary.text = "$count seats selected"
        }


        btnConfirm.setOnClickListener {
            val count = seats.count { it == "Selected" }

            if (count == 0) {
                Toast.makeText(this, "No seats selected!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Booking Confirmed for $count seats!", Toast.LENGTH_LONG).show()
            }
        }
    }
}