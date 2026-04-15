package com.example.ecommerceapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

class CartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val recycler = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerCart)
        val totalTxt = findViewById<TextView>(R.id.txtTotal)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = CartAdapter(CartManager.cartList)

        val total = CartManager.cartList.sumOf { it.price }
        totalTxt.text = "Total: $$total"
    }
}