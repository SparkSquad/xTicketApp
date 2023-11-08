package com.teamxticket.xticket.data.model

data class UserResponse(
    val expires: String,
    val message: String?,
    val token: String,
    val user: User?
)