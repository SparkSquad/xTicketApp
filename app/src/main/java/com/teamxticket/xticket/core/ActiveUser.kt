package com.teamxticket.xticket.core

import com.teamxticket.xticket.data.model.User

class ActiveUser private constructor() {
    private var activeUser: User? = null
    private var darkMode: Boolean = false

    companion object {
        private val instance = ActiveUser()

        fun getInstance(): ActiveUser {
            return instance
        }
    }

    fun setUser(user: User) {
        activeUser = user
    }

    fun getUser(): User? {
        return activeUser
    }

    fun getDarkMode(): Boolean {
        return darkMode
    }

    fun setDarkMode(darkMode: Boolean) {
        this.darkMode = darkMode
    }
}
