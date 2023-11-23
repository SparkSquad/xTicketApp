package com.teamxticket.xticket.domain

import com.teamxticket.xticket.data.EventPlannerRepository
import com.teamxticket.xticket.data.model.EventPlannerData

class EventPlannerUseCase {
    private val repository = EventPlannerRepository()
    suspend fun getEventPlannerById(eventPlannerDataId: Int): EventPlannerData = repository.getEventPlannerById(eventPlannerDataId)
}