package com.example.universityeventapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EventDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val title = findViewById<TextView>(R.id.title)
        val date = findViewById<TextView>(R.id.date)
        val venue = findViewById<TextView>(R.id.venue)
        val btn = findViewById<Button>(R.id.btnRegister)

        val event = intent.getSerializableExtra("event") as Event

        title.text = event.title
        date.text = event.date
        venue.text = event.venue

        btn.setOnClickListener {
            startActivity(Intent(this, SeatBookingActivity::class.java))
        }
    }
}