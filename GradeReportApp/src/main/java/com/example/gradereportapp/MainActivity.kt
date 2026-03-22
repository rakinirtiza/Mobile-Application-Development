package com.example.gradereportapp

import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

class MainActivity : AppCompatActivity() {

    private lateinit var tableLayout: TableLayout
    private lateinit var gpaText: TextView

    private var totalPoints = 0.0
    private var subjectCount = 0
    private var passCount = 0
    private var failCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tableLayout = findViewById(R.id.tableLayout)
        gpaText = findViewById(R.id.gpaText)

        val addBtn = findViewById<Button>(R.id.addBtn)
        val shareBtn = findViewById<Button>(R.id.shareBtn)


        addInitialData()
        addBtn.setOnClickListener {

            val subject = findViewById<EditText>(R.id.subjectName).text.toString()
            val obtained = findViewById<EditText>(R.id.obtainedMarks).text.toString().toIntOrNull()
            val total = findViewById<EditText>(R.id.totalMarks).text.toString().toIntOrNull()

            if (subject.isEmpty() || obtained == null || total == null) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val percentage = (obtained * 100) / total
            val grade = getGrade(percentage)
            val point = getPoint(grade)

            addRow(subject, obtained, total, grade)

            totalPoints += point
            subjectCount++

            if (grade == "F") failCount++ else passCount++

            updateSummary()

            findViewById<EditText>(R.id.subjectName).text.clear()
            findViewById<EditText>(R.id.obtainedMarks).text.clear()
            findViewById<EditText>(R.id.totalMarks).text.clear()

            Toast.makeText(this, "Subject Added!", Toast.LENGTH_SHORT).show()
        }


        shareBtn.setOnClickListener {

            val report = "Student Grade Report\n\n" + gpaText.text.toString()

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, report)

            startActivity(Intent.createChooser(intent, "Share via"))
        }
    }

    private fun addInitialData() {
        val data = listOf(
            Triple("SOFTWARE ENGINEERING", 85, 100),
            Triple("MOBILE APPLICATION", 90, 100),
            Triple("OOP2", 40, 100),
            Triple("MICROPROCESSOR", 65, 100),
            Triple("SQT", 90, 100),
            Triple("COA", 35, 100)
        )

        for (item in data) {
            val percentage = (item.second * 100) / item.third
            val grade = getGrade(percentage)
            val point = getPoint(grade)

            addRow(item.first, item.second, item.third, grade)

            totalPoints += point
            subjectCount++

            if (grade == "F") failCount++ else passCount++
        }

        updateSummary()
    }

    private fun updateSummary() {
        val gpa = totalPoints / subjectCount
        gpaText.text = "GPA: %.2f".format(gpa)

        if (tableLayout.childCount > 7) {
            tableLayout.removeViewAt(tableLayout.childCount - 1)
        }

        val summaryRow = TableRow(this)

        val tv = TextView(this)
        tv.text = "Total: $subjectCount | Passed: $passCount | Failed: $failCount"
        tv.setPadding(10, 10, 10, 10)
        tv.setBackgroundColor(Color.LTGRAY)

        summaryRow.addView(tv)
        tableLayout.addView(summaryRow)
    }

    private fun getGrade(p: Int): String {
        return when {
            p >= 90 -> "A+"
            p >= 80 -> "A"
            p >= 70 -> "B+"
            p >= 60 -> "B"
            p >= 50 -> "C"
            p >= 40 -> "D"
            else -> "F"
        }
    }


    private fun getPoint(g: String): Double {
        return when (g) {
            "A+" -> 4.0
            "A" -> 3.7
            "B+" -> 3.3
            "B" -> 3.0
            "C" -> 2.0
            "D" -> 1.0
            else -> 0.0
        }
    }

    private fun addRow(subject: String, obtained: Int, total: Int, grade: String) {

        val row = TableRow(this)

        val index = tableLayout.childCount

        val color = if (grade == "F")
            Color.parseColor("#FFCDD2")   // Red for fail
        else if (index % 2 == 0)
            Color.parseColor("#E3F2FD")   // Light blue alternate
        else
            Color.parseColor("#C8E6C9")   // Green pass

        row.addView(createCell(subject, color))
        row.addView(createCell(obtained.toString(), color))
        row.addView(createCell(total.toString(), color))
        row.addView(createCell(grade, color))

        tableLayout.addView(row)
    }
    
    private fun createCell(text: String, color: Int): TextView {
        val tv = TextView(this)
        tv.text = text
        tv.setPadding(10, 10, 10, 10)
        tv.setBackgroundColor(color)
        return tv
    }
}