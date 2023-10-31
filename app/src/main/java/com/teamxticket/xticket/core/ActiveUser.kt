package com.teamxticket.xticket.core

import com.teamxticket.xticket.data.model.User

class ActiveUser private constructor() {
    private var activeUser: User? = null

    companion object {
        private val instance = ActiveUser()

        fun getInstance(): ActiveUser {
            return instance
        }
    }

    fun setUser(user: User) {
        activeUser = user
    }

    fun obtenerUsuario(): User? {
        return activeUser
    }
}
