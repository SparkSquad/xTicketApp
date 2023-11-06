package com.teamxticket.xticket.data.model

data class EventResponse (
    val message: String,
    val allEvents: List<Event>
)