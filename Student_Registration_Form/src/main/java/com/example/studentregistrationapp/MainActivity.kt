package com.example.studentregistrationapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    var selectedDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etId = findViewById<EditText>(R.id.stId)
        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etAge = findViewById<EditText>(R.id.etAge)

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        val cbFootball = findViewById<CheckBox>(R.id.cbFootball)
        val cbCricket = findViewById<CheckBox>(R.id.cbCricket)
        val cbBasketball = findViewById<CheckBox>(R.id.cbBasketball)
        val cbBadminton = findViewById<CheckBox>(R.id.cbBadminton)

        val spinner = findViewById<Spinner>(R.id.spinnerCountry)
        val btnDate = findViewById<Button>(R.id.btnDate)
        val tvDate = findViewById<TextView>(R.id.tvDate)

        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val btnReset = findViewById<Button>(R.id.btnReset)

        // Spinner data
        val countries = arrayOf("Bangladesh", "India", "USA", "UK", "Canada")
        spinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, countries)

        // Date picker
        btnDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    selectedDate = "$day/${month + 1}/$year"
                    tvDate.text = selectedDate
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Submit button
        btnSubmit.setOnClickListener {

            val id = etId.text.toString()
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val age = etAge.text.toString()

            val gender = when (radioGroup.checkedRadioButtonId) {
                R.id.rbMale -> "Male"
                R.id.rbFemale -> "Female"
                R.id.rbOther -> "Other"
                else -> ""
            }

            val sports = mutableListOf<String>()
            if (cbFootball.isChecked) sports.add("Football")
            if (cbCricket.isChecked) sports.add("Cricket")
            if (cbBasketball.isChecked) sports.add("Basketball")
            if (cbBadminton.isChecked) sports.add("Badminton")

            val country = spinner.selectedItem.toString()

            // Validation
            if (id.isEmpty() || name.isEmpty() || email.isEmpty() || age.isEmpty() || gender.isEmpty() || sports.isEmpty() || selectedDate.isEmpty()) {
                Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!email.contains("@")) {
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Show Toast
            val message = """
ID: $id
Name: $name
Gender: $gender
Sports: ${sports.joinToString()}
Country: $country
DOB: $selectedDate
""".trimIndent()

            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
            // Reset button
            btnReset.setOnClickListener {
                etId.text.clear()
                etName.text.clear()
                etEmail.text.clear()
                etPassword.text.clear()
                etAge.text.clear()

                radioGroup.clearCheck()

                cbFootball.isChecked = false
                cbCricket.isChecked = false
                cbBasketball.isChecked = false
                cbBadminton.isChecked = false

                spinner.setSelection(0)
                tvDate.text = "No Date Selected"
                selectedDate = ""
            }
        }
    }

