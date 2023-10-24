package com.teamxticket.xticket.data.model

import com.teamxticket.xticket.ui.view.BandArtist

data class Event (
    val eventName: String,
    val musicalGenre: Int,
    val eventDescription: String,
    val eventLocation: String,
    val bandAndArtists: ArrayList<BandArtist>
)