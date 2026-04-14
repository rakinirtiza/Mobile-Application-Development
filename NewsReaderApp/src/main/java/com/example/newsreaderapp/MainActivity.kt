package com.example.newsreaderapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView

class MainActivity : AppCompatActivity() {

    lateinit var scrollView: NestedScrollView
    var isBookmarked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scrollView = findViewById(R.id.scrollView)


        val intro = findViewById<TextView>(R.id.introSection)
        val key = findViewById<TextView>(R.id.keySection)
        val analysis = findViewById<TextView>(R.id.analysisSection)
        val conclusion = findViewById<TextView>(R.id.conclusionSection)


        findViewById<Button>(R.id.btnIntro).setOnClickListener {
            scrollView.post {
                scrollView.smoothScrollTo(0, intro.top)
            }
        }

        findViewById<Button>(R.id.btnKey).setOnClickListener {
            scrollView.post {
                scrollView.smoothScrollTo(0, key.top)
            }
        }

        findViewById<Button>(R.id.btnAnalysis).setOnClickListener {
            scrollView.post {
                scrollView.smoothScrollTo(0, analysis.top)
            }
        }

        findViewById<Button>(R.id.btnConclusion).setOnClickListener {
            scrollView.post {
                scrollView.smoothScrollTo(0, conclusion.top)
            }
        }


        findViewById<Button>(R.id.topBtn).setOnClickListener {
            scrollView.smoothScrollTo(0, 0)
        }


        val bookmarkBtn = findViewById<ImageButton>(R.id.bookmarkBtn)

        bookmarkBtn.setOnClickListener {
            isBookmarked = !isBookmarked

            if (isBookmarked) {
                bookmarkBtn.setImageResource(android.R.drawable.btn_star_big_on)
                Toast.makeText(this, "Article Bookmarked", Toast.LENGTH_SHORT).show()
            } else {
                bookmarkBtn.setImageResource(android.R.drawable.btn_star_big_off)
                Toast.makeText(this, "Bookmark Removed", Toast.LENGTH_SHORT).show()
            }
        }


        findViewById<ImageButton>(R.id.shareBtn).setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out this article: The Future of Mobile Apps"
            )

            startActivity(Intent.createChooser(intent, "Share via"))
        }
    }


}
