package com.teamxticket.xticket.data.model

data class Event (
    val eventId: Int?,
    val name: String,
    val genre: String,
    val description: String,
    val location: String,
    val userId: Int,
    val bandsAndArtists: MutableList<String>? = mutableListOf()
)