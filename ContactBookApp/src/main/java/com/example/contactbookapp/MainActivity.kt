package com.example.contactbookapp

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ContactAdapter
    private lateinit var listView: ListView
    private lateinit var emptyView: TextView

    private val contacts = mutableListOf<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        val searchView = findViewById<SearchView>(R.id.searchView)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        emptyView = findViewById(R.id.emptyView)


        loadContacts()

        adapter = ContactAdapter(this, contacts)
        listView.adapter = adapter

        updateEmptyView()

        fab.setOnClickListener {
            showAddDialog()
        }


        listView.setOnItemClickListener { _, _, position, _ ->
            val contact = adapter.getItem(position)

            Toast.makeText(
                this,
                "${contact.name}\n${contact.phone}\n${contact.email}",
                Toast.LENGTH_LONG
            ).show()
        }


        listView.setOnItemLongClickListener { _, _, position, _ ->
            AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes") { _, _ ->
                    adapter.removeContact(position)
                    saveContacts()
                    updateEmptyView()
                }
                .setNegativeButton("No", null)
                .show()
            true
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText ?: "")
                updateEmptyView()
                return true
            }
        })
    }

    private fun showAddDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_add, null)

        val name = view.findViewById<EditText>(R.id.etName)
        val phone = view.findViewById<EditText>(R.id.etPhone)
        val email = view.findViewById<EditText>(R.id.etEmail)

        AlertDialog.Builder(this)
            .setTitle("Add Contact")
            .setView(view)
            .setPositiveButton("Add") { _, _ ->
                val contact = Contact(
                    name.text.toString(),
                    phone.text.toString(),
                    email.text.toString(),
                    name.text.toString().first().uppercase()
                )
                adapter.addContact(contact)
                saveContacts()
                updateEmptyView()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }


    private fun saveContacts() {
        val sharedPref = getSharedPreferences("contacts_pref", MODE_PRIVATE)
        val editor = sharedPref.edit()

        val gson = Gson()
        val json = gson.toJson(contacts)

        editor.putString("contacts", json)
        editor.apply()
    }

    private fun loadContacts() {
        val sharedPref = getSharedPreferences("contacts_pref", MODE_PRIVATE)

        val gson = Gson()
        val json = sharedPref.getString("contacts", null)

        val type = object : TypeToken<MutableList<Contact>>() {}.type
        val savedList: MutableList<Contact>? = gson.fromJson(json, type)

        if (savedList != null) {
            contacts.clear()
            contacts.addAll(savedList)
        }
    }

    private fun updateEmptyView() {
        emptyView.visibility =
            if (adapter.count == 0) View.VISIBLE else View.GONE
    }
}