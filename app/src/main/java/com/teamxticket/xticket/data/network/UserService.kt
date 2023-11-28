package com.teamxticket.xticket.data.network

import UsersApiClient
import android.content.res.Resources
import com.teamxticket.xticket.R
import com.teamxticket.xticket.core.RetrofitHelper
import com.teamxticket.xticket.data.model.CodeResponse
import com.teamxticket.xticket.data.model.EventFollow
import com.teamxticket.xticket.data.model.OneTimeUseCode
import com.teamxticket.xticket.data.model.OneTimeUseCodeResponse
import com.teamxticket.xticket.data.model.User
import com.teamxticket.xticket.data.model.UserEventFollowsResponse
import com.teamxticket.xticket.data.model.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class UserService{
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun login(user: User): UserResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(UsersApiClient::class.java).login(user)
                if (response.code() >= 500) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_500))
                } else if (response.code() >= 400) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_400))
                }
                response.body() ?: UserResponse("", "", "", null)
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException(Resources.getSystem().getString(R.string.message_can_not_connect_with_server))
            }
        }
    }

    suspend fun codeLogin(code: OneTimeUseCode): UserResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(UsersApiClient::class.java).codeLogin(code)
                if (response.code() >= 500) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_500))
                } else if (response.code() >= 400) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_400))
                }
                response.body() ?: UserResponse("", "", "", null)
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException(Resources.getSystem().getString(R.string.message_can_not_connect_with_server))
            }
        }
    }
    
    suspend fun postUser(user: User): Int {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(UsersApiClient::class.java).postUser(user)
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

    suspend fun putUser(user: User): CodeResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(UsersApiClient::class.java).putUser(user)
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

    suspend fun getUserEventFollows(userId: Int): UserEventFollowsResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(UsersApiClient::class.java).getUserEventFollows(userId)
                if (response.code() >= 500) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_500))
                } else if (response.code() >= 400) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_400))
                }
                response.body() ?: UserEventFollowsResponse("", listOf())

            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException(Resources.getSystem().getString(R.string.message_can_not_connect_with_server))
            }
        }
    }

    suspend fun followEvent(userId: Int, eventId: Int) {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(UsersApiClient::class.java).followEvent(userId, EventFollow(eventId, userId))
                if (response.code() >= 500) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_500))
                } else if (response.code() >= 400) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_400))
                }
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException(Resources.getSystem().getString(R.string.message_can_not_connect_with_server))
            }
        }
    }

    suspend fun requestOTUCode(email: OneTimeUseCode): OneTimeUseCodeResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(UsersApiClient::class.java).requestOTUCode(email)
                if (response.code() >= 500) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_500))
                } else if (response.code() >= 400) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_400))
                }
                response.body() ?: OneTimeUseCodeResponse("", -1)
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException(Resources.getSystem().getString(R.string.message_can_not_connect_with_server))
            }
        }
    }

}