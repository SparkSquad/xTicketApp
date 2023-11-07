package com.teamxticket.xticket.data.model

data class TicketResponse(
    val message: String,
    val tickets: List<TicketData>
)