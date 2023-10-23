package com.teamxticket.xticket.data.model

data class SaleDate(
    val eventDate: String,
    val numberOfTickets: Int,
    val price: Float,
    val maxTickets: Int,
    val onlyAdults: Boolean,
    val startTime: String,
    val endTime: String
)