package com.teamxticket.xticket.data.network

import android.content.res.Resources
import com.teamxticket.xticket.R
import com.teamxticket.xticket.core.RetrofitHelper
import com.teamxticket.xticket.data.model.EventPlannerData
import com.teamxticket.xticket.data.model.EventPlannerDataResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class EventPlannerDataService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getEventPlannerById(eventPlannerId: Int) : EventPlannerDataResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(EventPlannerDataApiClient::class.java).getByUserId(eventPlannerId)
                if (response.code() >= 500) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_500))
                } else if (response.code() >= 400) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_400))
                }
                response.body() ?: EventPlannerDataResponse("", EventPlannerData(0, "", "", "",""))
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException(Resources.getSystem().getString(R.string.message_can_not_connect_with_server))
            }
        }
    }
}