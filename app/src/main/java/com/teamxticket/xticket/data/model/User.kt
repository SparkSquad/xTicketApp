package com.teamxticket.xticket.data.model


data class User (
    val name: String,
    val surnames: String,
    val email: String,
    val password: String,
    val type: Int
)