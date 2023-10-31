package com.teamxticket.xticket.data.model

data class User(
    val email: String,
    val name: String,
    val surnames: String,
    val type: String,
    val password: String
)