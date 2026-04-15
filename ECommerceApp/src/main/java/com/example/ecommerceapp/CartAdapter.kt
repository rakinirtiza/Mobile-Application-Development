package com.example.ecommerceapp

import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(private val list: List<Product>) :
    RecyclerView.Adapter<CartAdapter.VH>() {

    inner class VH(v: View) : RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(p: ViewGroup, v: Int): VH {
        val view = LayoutInflater.from(p.context)
            .inflate(R.layout.item_cart, p, false)
        return VH(view)
    }

    override fun onBindViewHolder(h: VH, pos: Int) {
        val p = list[pos]
        h.itemView.findViewById<TextView>(R.id.txtName).text = p.name
        h.itemView.findViewById<TextView>(R.id.txtPrice).text = "$${p.price}"
    }

    override fun getItemCount() = list.size
}