package com.teamxticket.xticket.data.network

import android.content.res.Resources
import com.teamxticket.xticket.R
import com.teamxticket.xticket.core.RetrofitHelper
import com.teamxticket.xticket.data.model.CodeResponse
import com.teamxticket.xticket.data.model.Event
import com.teamxticket.xticket.data.model.EventResponse
import com.teamxticket.xticket.data.model.GenreResponse
import com.teamxticket.xticket.data.model.SearchEventsResponse
import com.teamxticket.xticket.data.model.TicketTakerRequest
import com.teamxticket.xticket.data.model.TicketTakerResponse
import com.teamxticket.xticket.data.model.User
import com.teamxticket.xticket.data.model.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class EventService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getAllEvents(userId: Int): EventResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(EventApiClient::class.java).getAllEvents(userId)
                if(response.code() >= 500) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_500))
                } else if(response.code() >= 400) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_400))
                }
                response.body() ?: EventResponse("", emptyList())
            } catch (e: SocketTimeoutException) {
                throw Exception(Resources.getSystem().getString(R.string.message_can_not_connect_with_server))
            }
        }
    }

    suspend fun postEvent(newEvent: Event): CodeResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(EventApiClient::class.java).postEvent(newEvent)
                if (response.code() >= 500) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_500))
                } else if (response.code() >= 400) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_400))
                }
                response.body() ?: CodeResponse(-1)
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException(Resources.getSystem().getString(R.string.message_can_not_connect_with_server))
            }
        }
    }

    suspend fun getGenres(): GenreResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(EventApiClient::class.java).getGenres()
                if (response.code() >= 500) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_500))
                } else if (response.code() >= 400) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_400))
                }
                response.body() ?: GenreResponse("", emptyList())
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException(Resources.getSystem().getString(R.string.message_can_not_connect_with_server))
            }
        }
    }

    suspend fun putEvent(newEvent: Event): CodeResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(EventApiClient::class.java).putEvent(newEvent)
                if (response.code() >= 500) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_500))
                } else if (response.code() >= 400) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_400))
                }
                response.body() ?: CodeResponse(-1)
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException(Resources.getSystem().getString(R.string.message_can_not_connect_with_server))
            }
        }
    }

    suspend fun getEvent(eventId: Int): Event {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(EventApiClient::class.java).getEvent(eventId)
                if (response.code() >= 500) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_500))
                } else if (response.code() >= 400) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_400))
                }
                response.body() ?: Event(-1, "", "", "", "", -1, "",mutableListOf(), null, null)
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException(Resources.getSystem().getString(R.string.message_can_not_connect_with_server))
            }
        }
    }

    suspend fun deleteEvent(eventId: Int): Int {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(EventApiClient::class.java).deleteEvent(eventId)
                if (response.code() >= 500) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_500))
                } else if (response.code() >= 400) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_400))
                }
                response.code()
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException(Resources.getSystem().getString(R.string.message_can_not_connect_with_server))
            }
        }
    }

    suspend fun searchEvents(query: String, genre: String?, limit: Int, page: Int): SearchEventsResponse {
        val params = mutableMapOf(
            "limit" to limit.toString(),
            "page" to page.toString()
        )
        if(genre != null) {
            params["genre"] = genre
        }
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(EventApiClient::class.java).searchEvents(query, params.toMap())
            response.body() ?: SearchEventsResponse(null, null, null)
        }
    }

    suspend fun loginTickerTaker(ticketTakerCode: String): TicketTakerResponse {
        return withContext(Dispatchers.IO) {
            try {
                val ticketTakerRequest = TicketTakerRequest(ticketTakerCode)
                val response = retrofit.create(EventApiClient::class.java).loginTickerTaker(ticketTakerRequest)

                if (response.code() >= 500) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_500))
                } else if (response.code() >= 400) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_400))
                }
                response.body() ?: TicketTakerResponse("") // Aquí puedes ajustar según la inicialización de TicketTakerResponse
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException(Resources.getSystem().getString(R.string.message_can_not_connect_with_server))
            }
        }
    }
}