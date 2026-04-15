package com.example.universityeventapp

import java.io.Serializable

data class Event(
    val title: String,
    val date: String,
    val venue: String,
    val price: Int
) : Serializable