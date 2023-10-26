package com.teamxticket.xticket.data.model

import java.util.Date

data class NewSaleDate(
    val sale_date: Date,
    val price: Double,
    val tickets: Int,
    val max_tickets: Int,
    val adults: Int,
    val start_time: String,
    val end_time: String,
    val event_id: Int,
)