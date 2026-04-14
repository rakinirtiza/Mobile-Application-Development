package com.example.contactbookapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.ImageView
import android.widget.Toast

class ContactAdapter(
    context: Context,
    private var contacts: MutableList<Contact>
) : ArrayAdapter<Contact>(context, 0, contacts) {

    private var filteredList = contacts.toMutableList()

    private class ViewHolder(view: View) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvPhone: TextView = view.findViewById(R.id.tvPhone)
        val tvInitial: TextView = view.findViewById(R.id.tvInitial)
        val callBtn: ImageView = view.findViewById(R.id.btnCall)
    }

    override fun getCount(): Int {
        return filteredList.size
    }

    override fun getItem(position: Int): Contact {
        return filteredList[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val contact = getItem(position)


        holder.tvName.text = contact.name
        holder.tvPhone.text = contact.phone
        holder.tvInitial.text = contact.initial

        val colors = listOf(
            Color.RED, Color.BLUE, Color.GREEN,
            Color.CYAN, Color.MAGENTA
        )
        holder.tvInitial.setBackgroundColor(colors[position % colors.size])


        holder.callBtn.setOnClickListener {
            Toast.makeText(context, "Calling ${contact.phone}", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            contacts.toMutableList()
        } else {
            contacts.filter {
                it.name.lowercase().contains(query.lowercase())
            }.toMutableList()
        }
        notifyDataSetChanged()
    }

    fun addContact(contact: Contact) {
        contacts.add(contact)
        filter("")
    }

    fun removeContact(position: Int) {
        contacts.remove(filteredList[position])
        filter("")
    }
}