package com.example.photogalleryapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var gridView: GridView
    private lateinit var adapter: PhotoAdapter
    private lateinit var photoList: MutableList<Photo>
    private lateinit var originalList: MutableList<Photo>

    private lateinit var selectionToolbar: LinearLayout
    private lateinit var selectedCount: TextView


    private val imageList = listOf(
        R.drawable.image1, R.drawable.image2, R.drawable.image3,
        R.drawable.image4, R.drawable.image5, R.drawable.image6,
        R.drawable.image7, R.drawable.image8, R.drawable.image9,
        R.drawable.image10, R.drawable.image11, R.drawable.image12
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridView = findViewById(R.id.gridView)
        selectionToolbar = findViewById(R.id.selectionToolbar)
        selectedCount = findViewById(R.id.selectedCount)

        val deleteBtn = findViewById<Button>(R.id.deleteBtn)
        val shareBtn = findViewById<Button>(R.id.shareBtn)
        val fab = findViewById<FloatingActionButton>(R.id.fab)

        val btnAll = findViewById<Button>(R.id.btnAll)
        val btnNature = findViewById<Button>(R.id.btnNature)
        val btnCity = findViewById<Button>(R.id.btnCity)
        val btnAnimals = findViewById<Button>(R.id.btnAnimals)
        val btnFood = findViewById<Button>(R.id.btnFood)
        val btnTravel = findViewById<Button>(R.id.btnTravel)

        loadPhotos()
        originalList = photoList.toMutableList()

        adapter = PhotoAdapter(this, photoList)
        gridView.adapter = adapter


        gridView.setOnItemClickListener { _, _, position, _ ->
            val photo = adapter.getItem(position) as Photo

            if (!adapter.isSelectionMode) {
                val intent = Intent(this, FullscreenActivity::class.java)
                intent.putExtra("image", photo.resourceId)
                startActivity(intent)
            } else {
                toggleSelection(photo)
            }
        }


        gridView.setOnItemLongClickListener { _, _, position, _ ->
            adapter.isSelectionMode = true
            selectionToolbar.visibility = LinearLayout.VISIBLE
            val photo = adapter.getItem(position) as Photo
            toggleSelection(photo)
            true
        }


        deleteBtn.setOnClickListener {
            val toRemove = originalList.filter { it.isSelected }
            originalList.removeAll(toRemove)

            Toast.makeText(this, "${toRemove.size} photos deleted", Toast.LENGTH_SHORT).show()

            filter("All")
            adapter.isSelectionMode = false
            selectionToolbar.visibility = LinearLayout.GONE
        }


        shareBtn.setOnClickListener {
            val selected = originalList.filter { it.isSelected }

            if (selected.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, "Sharing ${selected.size} photos from my gallery!")
                startActivity(Intent.createChooser(intent, "Share via"))
            } else {
                Toast.makeText(this, "No photo selected", Toast.LENGTH_SHORT).show()
            }
        }


        fab.setOnClickListener {
            val randomImage = imageList.random()

            val newPhoto = Photo(
                id = originalList.size + 1,
                resourceId = randomImage,
                title = "New Photo",
                category = "Nature"
            )

            originalList.add(newPhoto)
            filter("All")
        }


        btnAll.setOnClickListener {
            resetTabs(btnAll, btnNature, btnCity, btnAnimals, btnFood, btnTravel)
            btnAll.setBackgroundColor(getColor(android.R.color.holo_red_dark))
            filter("All")
        }

        btnNature.setOnClickListener {
            resetTabs(btnAll, btnNature, btnCity, btnAnimals, btnFood, btnTravel)
            btnNature.setBackgroundColor(getColor(android.R.color.holo_red_dark))
            filter("Nature")
        }

        btnCity.setOnClickListener {
            resetTabs(btnAll, btnNature, btnCity, btnAnimals, btnFood, btnTravel)
            btnCity.setBackgroundColor(getColor(android.R.color.holo_red_dark))
            filter("City")
        }

        btnAnimals.setOnClickListener {
            resetTabs(btnAll, btnNature, btnCity, btnAnimals, btnFood, btnTravel)
            btnAnimals.setBackgroundColor(getColor(android.R.color.holo_red_dark))
            filter("Animals")
        }

        btnFood.setOnClickListener {
            resetTabs(btnAll, btnNature, btnCity, btnAnimals, btnFood, btnTravel)
            btnFood.setBackgroundColor(getColor(android.R.color.holo_red_dark))
            filter("Food")
        }

        btnTravel.setOnClickListener {
            resetTabs(btnAll, btnNature, btnCity, btnAnimals, btnFood, btnTravel)
            btnTravel.setBackgroundColor(getColor(android.R.color.holo_red_dark))
            filter("Travel")
        }
    }


    private fun resetTabs(vararg buttons: Button) {
        for (btn in buttons) {
            btn.setBackgroundColor(getColor(android.R.color.darker_gray))
        }
    }

    private fun loadPhotos() {
        photoList = mutableListOf(
            Photo(1, R.drawable.image1, "Nature 1", "Nature"),
            Photo(2, R.drawable.image2, "City 1", "City"),
            Photo(3, R.drawable.image3, "Animal 1", "Animals"),
            Photo(4, R.drawable.image4, "Food 1", "Food"),
            Photo(5, R.drawable.image5, "Travel 1", "Travel"),
            Photo(6, R.drawable.image6, "Nature 2", "Nature"),
            Photo(7, R.drawable.image7, "City 2", "City"),
            Photo(8, R.drawable.image8, "Animal 2", "Animals"),
            Photo(9, R.drawable.image9, "Food 2", "Food"),
            Photo(10, R.drawable.image10, "Travel 2", "Travel"),
            Photo(11, R.drawable.image11, "Nature 3", "Nature"),
            Photo(12, R.drawable.image12, "City 3", "City")
        )
    }

    private fun toggleSelection(photo: Photo) {
        photo.isSelected = !photo.isSelected
        updateSelectionCount()
        adapter.notifyDataSetChanged()
    }

    private fun updateSelectionCount() {
        val count = originalList.count { it.isSelected }
        selectedCount.text = "$count selected"

        if (count == 0) {
            adapter.isSelectionMode = false
            selectionToolbar.visibility = LinearLayout.GONE
        }
    }

    private fun filter(category: String) {
        val filtered = if (category == "All") {
            originalList
        } else {
            originalList.filter { it.category == category }.toMutableList()
        }
        adapter.updateList(filtered)
    }
}