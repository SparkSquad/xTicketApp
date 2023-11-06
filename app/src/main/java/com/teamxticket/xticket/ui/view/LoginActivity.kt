package com.teamxticket.xticket.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.teamxticket.xticket.R
import com.teamxticket.xticket.core.RetrofitHelper
import com.teamxticket.xticket.data.UserRepository
import com.teamxticket.xticket.data.model.User
import com.teamxticket.xticket.databinding.ActivityLoginBinding
import com.teamxticket.xticket.ui.viewModel.UserViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private val userViewModel : UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObservables()
        initListeners()
    }

    private fun initListeners () {
        binding.btnSignInFr.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()


            if (email.length>0 && password.length>0) {
                val user : User = User(0,email, "", "", "", password)
                userViewModel.searchUser(user)
            } else {
                Toast.makeText(this, "El email o contraseña ingresados no son correctos, por favor intente de nuevo.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initObservables () {
         userViewModel.receivedUser.observe(this) {
             if (!it.message.isNullOrEmpty()) {
                 Toast.makeText(
                     this,
                     "Has ingresado de manera exitosa!",
                     Toast.LENGTH_SHORT
                 ).show()
                 if (it.user?.email?.isEmpty() == false) {
                     Intent (this, ManageEventActivity::class.java).apply {
                         startActivity(this)
                     }
                 }
             } else {
                 Toast.makeText(
                     this,
                     "El email o contraseña ingresados no son correctos, por favor intente de nuevo.",
                     Toast.LENGTH_LONG
                 ).show()
             }
         }
    }
}