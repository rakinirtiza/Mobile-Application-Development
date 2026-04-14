package com.example.photogalleryapp

import android.graphics.Matrix
import android.os.Bundle
import android.view.ScaleGestureDetector
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class FullscreenActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor = 1.0f
    private val matrix = Matrix()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)

        imageView = findViewById(R.id.fullImage)

        val imageRes = intent.getIntExtra("image", 0)
        imageView.setImageResource(imageRes)

        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener())

        imageView.setOnTouchListener { _, event ->
            scaleGestureDetector.onTouchEvent(event)
            true
        }
    }

    private inner class ScaleListener :
        ScaleGestureDetector.SimpleOnScaleGestureListener() {

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= detector.scaleFactor
            scaleFactor = scaleFactor.coerceIn(0.5f, 3.0f)

            matrix.setScale(scaleFactor, scaleFactor)
            imageView.imageMatrix = matrix

            return true
        }
    }
}