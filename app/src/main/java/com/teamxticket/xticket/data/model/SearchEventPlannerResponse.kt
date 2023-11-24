package com.teamxticket.xticket.data.model

data class SearchEventPlannerResponse (
    val results: List<User>?,
    val page: Int?,
    val totalElems: Int?
)