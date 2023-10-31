package com.teamxticket.xticket.data.model;

data class SearchEventsResponse(
    val results: List<Event>?,
    val page: Int?,
    val totalElems: Int?
)
