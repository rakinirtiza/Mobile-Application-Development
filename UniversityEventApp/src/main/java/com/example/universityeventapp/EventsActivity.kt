package com.example.universityeventapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EventsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val events = listOf(
            Event("Tech Summit","20 May","Hall A",100),
            Event("Sports Fest","22 May","Ground",50),
            Event("Cultural Night","25 May","Auditorium",80),
            Event("Seminar","28 May","Room 101",30),
            Event("Music Fest","30 May","Stage",120),
            Event("Hackathon","2 June","Lab",150),
            Event("Gaming Night","7 June","Room 202",60),
            Event("Debate","5 June","Hall B",20)
        )

        val adapter = EventAdapter(events) {
            val intent = Intent(this, EventDetailActivity::class.java)
            intent.putExtra("event", it)
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}