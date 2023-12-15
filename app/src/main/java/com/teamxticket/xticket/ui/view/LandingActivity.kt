package com.teamxticket.xticket.ui.view

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.gson.Gson
import com.teamxticket.xticket.core.ActiveUser
import com.teamxticket.xticket.databinding.ActivityLandingBinding


class LandingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLandingBinding
    private var activeUser = ActiveUser.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkSession()
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())
        delegate.applyDayNight()
        val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            activeUser.setDarkMode(true)
        }
    }

    private fun initListeners() {
        binding.btnSignIn.setOnClickListener {
            Intent(this, LoginActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding.btnSignUp.setOnClickListener {
            Intent(this, SignUpActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun checkSession() {
        val preferences = getSharedPreferences("xticketprefs", MODE_PRIVATE)
        val token = preferences.getString("token", null)
        val activeUserData = preferences.getString("user", null)
        if (token != null && activeUserData != null) {
            val user = Gson().fromJson(activeUserData, com.teamxticket.xticket.data.model.User::class.java)
            activeUser.setToken(token)
            activeUser.setUser(user)
            if(user?.type == "assistant" || user?.type == "admin") {
                Intent (this, AssistantMenuActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            } else if (user?.type == "eventPlanner") {
                Intent (this, EventPlannerMenuActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            }
        }
    }
}