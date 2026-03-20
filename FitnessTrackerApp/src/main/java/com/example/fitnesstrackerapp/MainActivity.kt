package com.example.fitnesstrackerapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var steps = 0
    private val goal = 10000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dateText = findViewById<TextView>(R.id.date)

        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val currentDate = sdf.format(Date())
        dateText.text = currentDate

        val stepsText = findViewById<TextView>(R.id.stepsText)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val progressPercent = findViewById<TextView>(R.id.progressPercent)
        val button = findViewById<Button>(R.id.updateBtn)

        button.setOnClickListener {

            val input = EditText(this)
            input.hint = "Enter steps"
            input.textSize = 20f


            val dialog = AlertDialog.Builder(this)
                .setTitle("Enter Steps ")
                .setView(input)
                .setPositiveButton("Update") { _, _ ->

                    steps = input.text.toString().toIntOrNull() ?: 0
                    stepsText.text = "$steps Steps"

                    val progress = ((steps.toFloat() / goal) * 100).toInt()
                    progressBar.progress = progress
                    progressPercent.text = "$progress%"

                    if (progress >= 100) {
                        Toast.makeText(this, "Goal Achieved! ", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancel", null)
                .create()

            dialog.show()


            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(resources.getColor(android.R.color.holo_green_dark))

            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(resources.getColor(android.R.color.holo_red_dark))
        }
    }
}