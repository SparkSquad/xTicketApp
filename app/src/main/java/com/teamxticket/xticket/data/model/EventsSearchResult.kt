package com.teamxticket.xticket.data.model

data class EventsSearchResult(
    val results: List<Event>,
    val page: Int,
    val totalElems: Int
)
