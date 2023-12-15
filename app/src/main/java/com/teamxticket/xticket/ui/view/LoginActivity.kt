package com.teamxticket.xticket.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.teamxticket.xticket.R
import com.teamxticket.xticket.core.ActiveUser
import com.teamxticket.xticket.data.model.User
import com.teamxticket.xticket.databinding.ActivityLoginBinding
import com.teamxticket.xticket.ui.viewModel.UserViewModel
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogAnimation
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType


class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private val userViewModel : UserViewModel by viewModels()
    private var activeUser = ActiveUser.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObservables()
        initListeners()
        binding.btnSignInTicketTaker.setOnClickListener {
            val intent = Intent(this@LoginActivity, LoginTicketTaker::class.java)
            startActivity(intent)
        }
    }

    private fun initListeners () {
        binding.btnSignInFr.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()


            if (email.isNotEmpty() && password.isNotEmpty()) {
                val user : User = User(0,email, "", "", password, "")
                userViewModel.searchUser(user)
            } else {
                AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.ERROR)
                    .setTitle(getString(R.string.emptyField))
                    .setMessage(getString(R.string.invalidUserData))
                    .setCancelable(true)
                    .setDarkMode(activeUser.getDarkMode())
                    .setGravity(Gravity.CENTER)
                    .setAnimation(DialogAnimation.SHRINK).show()
            }
        }
        binding.tvForgotPassword.setOnClickListener(){
            Intent (this, RecoverPasswordActivity::class.java).apply {
                finish()
                startActivity(this)
            }
        }
    }

    private fun initObservables () {
         userViewModel.receivedUser.observe(this) {
             if (it.token?.isNotEmpty() == true) {
                 if (it.user?.email?.isEmpty() == false) {
                     activeUser.saveSession(this)
                     if(it.user?.type == "assistant" || it.user?.type == "admin") {
                         Intent (this, AssistantMenuActivity::class.java).apply {
                             this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                             startActivity(this)
                         }
                     } else if (it.user?.type == "eventPlanner") {
                         Intent (this, EventPlannerMenuActivity::class.java).apply {
                             this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                             startActivity(this)
                         }
                     }
                 }
             } else {
                 AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.ERROR)
                     .setTitle(getString(R.string.notFound))
                     .setMessage(getString(R.string.invalidUser))
                     .setCancelable(true)
                     .setDarkMode(activeUser.getDarkMode())
                     .setGravity(Gravity.CENTER)
                     .setAnimation(DialogAnimation.SHRINK).show()
             }
         }

        userViewModel.showLoader.observe(this) {
            binding.overlayView.isVisible = it
            binding.progressBar.isVisible = it
        }
    }

}