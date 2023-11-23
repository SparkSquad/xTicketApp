package com.teamxticket.xticket.data

import com.teamxticket.xticket.data.model.EventPlannerData
import com.teamxticket.xticket.data.model.EventPlannerDataResponse
import com.teamxticket.xticket.data.network.EventPlannerDataService
class EventPlannerRepository {
    private val api = EventPlannerDataService()

    suspend fun getEventPlannerById(eventPlannerDataId: Int) : EventPlannerData {
        return api.getEventPlannerById(eventPlannerDataId).result
    }
}