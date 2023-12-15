package com.teamxticket.xticket.core

import android.content.Context.MODE_PRIVATE
import android.content.ContextWrapper
import com.google.gson.Gson
import com.teamxticket.xticket.data.model.User

class ActiveUser private constructor() {
    private val ACTIVE_USER_PREFS = "activeUserPrefs"
    private var activeUser: User? = null
    private var activeUserToken: String? = null
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

    fun setToken(token: String) {
        activeUserToken = token
    }

    fun getToken(): String? {
        return activeUserToken
    }

    fun getDarkMode(): Boolean {
        return darkMode
    }

    fun setDarkMode(darkMode: Boolean) {
        this.darkMode = darkMode
    }

    fun saveSession(context: ContextWrapper) {
        val preferences = context.getSharedPreferences(ACTIVE_USER_PREFS, MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("token", activeUserToken)
        editor.putString("user", Gson().toJson(activeUser))
        editor.apply()
    }

    fun restoreSession(context: ContextWrapper) {
        val preferences = context.getSharedPreferences(ACTIVE_USER_PREFS, MODE_PRIVATE)
        val token = preferences.getString("token", null)
        val activeUserData = preferences.getString("user", null)
        if (token != null && activeUserData != null) {
            val user = Gson().fromJson(activeUserData, User::class.java)
            activeUserToken = token
            activeUser = user
        }
    }

    fun sessionExists(context: ContextWrapper): Boolean {
        val preferences = context.getSharedPreferences(ACTIVE_USER_PREFS, MODE_PRIVATE)
        val token = preferences.getString("token", null)
        val activeUserData = preferences.getString("user", null)
        return token != null && activeUserData != null
    }

    fun clearSession(context: ContextWrapper) {
        val preferences = context.getSharedPreferences(ACTIVE_USER_PREFS, MODE_PRIVATE)
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
}
