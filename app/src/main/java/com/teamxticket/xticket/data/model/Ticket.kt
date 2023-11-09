package com.teamxticket.xticket.data.model

data class Ticket(
    val price: Double,
    val purchaseDate: String,
    val saleDateId: Int,
    val ticketId: Int,
    val totalTickets: Int,
    val userId: Int,
    val uuid: String
)