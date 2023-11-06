package com.teamxticket.xticket.data.model

data class TicketData(
    val eventName: String,
    val saleDate: SaleDate,
    val ticket: Ticket
)