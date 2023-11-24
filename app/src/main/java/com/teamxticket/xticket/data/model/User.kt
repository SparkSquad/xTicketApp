package com.teamxticket.xticket.data.model

data class User(
    val userId: Int,
    val email: String,
    val name: String,
    val type: String,
    val password: String,
    val surnames: String,
    val disabled: Boolean
)