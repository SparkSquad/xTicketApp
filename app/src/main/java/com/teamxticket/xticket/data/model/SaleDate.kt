package com.teamxticket.xticket.data.model

data class SaleDate(
    val adults: Int,
    val end_time: String,
    val event_id: Int,
    val max_tickets: Int,
    val price: Int,
    val sale_date: String,
    val sale_date_id: Int,
    val start_time: String,
    val tickets: Int
)