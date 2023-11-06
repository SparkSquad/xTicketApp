package com.teamxticket.xticket.data.model

data class User(
    val userId: Int,
    val email: String,
    val name: String,
    val type: String,
    val surnames: String,
    val password: String
)