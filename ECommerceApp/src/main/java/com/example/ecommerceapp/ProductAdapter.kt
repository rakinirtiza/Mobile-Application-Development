package com.example.ecommerceapp

import android.view.*
import android.widget.*
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(
    private val onCartClick: (Product) -> Unit
) : ListAdapter<Product, RecyclerView.ViewHolder>(DiffCallback()) {

    var isGrid = false

    override fun getItemViewType(position: Int): Int {
        return if (isGrid) 2 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return if (viewType == 1) {
            val view = inflater.inflate(R.layout.item_product_list, parent, false)
            ListVH(view)
        } else {
            val view = inflater.inflate(R.layout.item_product_grid, parent, false)
            GridVH(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val p = getItem(position)

        when (holder) {
            is ListVH -> holder.bind(p)
            is GridVH -> holder.bind(p)
        }
    }

    inner class ListVH(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(p: Product) {
            itemView.findViewById<TextView>(R.id.txtName).text = p.name
            itemView.findViewById<TextView>(R.id.txtPrice).text = "$${p.price}"
            itemView.findViewById<RatingBar>(R.id.ratingBar).rating = p.rating

            itemView.findViewById<Button>(R.id.btnCart).setOnClickListener {
                onCartClick(p)
            }
        }
    }

    inner class GridVH(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(p: Product) {
            itemView.findViewById<TextView>(R.id.txtName).text = p.name
            itemView.findViewById<TextView>(R.id.txtPrice).text = "$${p.price}"

            itemView.findViewById<ImageButton>(R.id.btnCart).setOnClickListener {
                onCartClick(p)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(old: Product, new: Product) = old.id == new.id
        override fun areContentsTheSame(old: Product, new: Product) = old == new
    }
}