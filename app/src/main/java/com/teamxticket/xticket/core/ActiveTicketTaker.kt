package com.teamxticket.xticket.core

import com.teamxticket.xticket.data.model.Event
import com.teamxticket.xticket.data.model.TicketTakerResponse

class ActiveTicketTaker private constructor() {
    private var activeTicketTaker: TicketTakerResponse? = null

    companion object {
        private val instance = ActiveTicketTaker()

        fun getInstance(): ActiveTicketTaker {
            return instance
        }
    }

    fun setTicketTaker(ticketTaker: String?) {
        activeTicketTaker = ticketTaker?.let { TicketTakerResponse(it) }
    }

    fun getTicketTaker(): TicketTakerResponse? {
        return activeTicketTaker
    }
}