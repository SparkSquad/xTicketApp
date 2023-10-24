package com.teamxticket.xticket.data.model

data class SaleDate(
    val eventDate: String,
    val numberOfTickets: Int,
    val price: Double,
    val maxTickets: Int,
    val onlyAdults: Int,
    val startTime: String,
    val endTime: String
)