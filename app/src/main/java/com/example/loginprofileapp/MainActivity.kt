package com.example.loginprofileapp

import android.os.Bundle
import android.os.Handler
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val logoutBtn = findViewById<Button>(R.id.logoutBtn)
        val progress = findViewById<ProgressBar>(R.id.progress)
        val profileCard = findViewById<LinearLayout>(R.id.profileCard)
        val forgot = findViewById<TextView>(R.id.forgot)

        loginBtn.setOnClickListener {
            val user = username.text.toString()
            val pass = password.text.toString()

            if (user == "admin" && pass == "1234") {
                progress.visibility = ProgressBar.VISIBLE

                Handler().postDelayed({
                    progress.visibility = ProgressBar.GONE

                    // Hide login form
                    username.visibility = EditText.GONE
                    password.visibility = EditText.GONE
                    loginBtn.visibility = Button.GONE
                    forgot.visibility = TextView.GONE

                    // Show profile
                    profileCard.visibility = LinearLayout.VISIBLE

                }, 2000)

            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            }
        }

        logoutBtn.setOnClickListener {
            profileCard.visibility = LinearLayout.GONE

            username.visibility = EditText.VISIBLE
            password.visibility = EditText.VISIBLE
            loginBtn.visibility = Button.VISIBLE
            forgot.visibility = TextView.VISIBLE

            username.text.clear()
            password.text.clear()
        }

        forgot.setOnClickListener {
            Toast.makeText(this, "Password reset link sent to your email", Toast.LENGTH_SHORT).show()
        }
    }
}