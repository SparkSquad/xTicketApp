package com.teamxticket.xticket.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.teamxticket.xticket.R
import com.teamxticket.xticket.data.model.User
import com.teamxticket.xticket.databinding.ActivitySignupBinding
import com.teamxticket.xticket.ui.viewModel.UserViewModel
import java.io.IOException

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val userViewModel : UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
        initObservables()
    }

    private fun initObservables() {}
    private fun initListeners() {
        binding.btnSignUp.setOnClickListener{
            //(validateForm()){
                val name = binding.etName.text.toString()
                val surnames = binding.etLastName.text.toString()
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                val type = if(binding.switchEvent.isChecked) 3 else 2

                val user = User (
                    name,
                    surnames,
                    email,
                    password,
                    type
                )


                    userViewModel.registerUser(user)
           // } else {
              //  Toast.makeText(this, "Error al registrar la fecha de venta", Toast.LENGTH_SHORT).show()
                }
            }
    }


