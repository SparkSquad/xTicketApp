package com.teamxticket.xticket.data.model


data class Event (
    val eventId: Int,
    val name: String,
    val genre: String,
    val description: String,
    val location: String,
    val userId: Int,
    val ticketTakerCode: String,
    val bandsAndArtists: MutableList<String>? = mutableListOf(),
    val artists: List<Artist>?,
    val saleDates: List<SaleDate>?,
    val eventPlanner: User?
)
