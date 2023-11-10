package com.teamxticket.xticket.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.teamxticket.xticket.data.model.User
import com.teamxticket.xticket.databinding.ActivitySignupBinding
import com.teamxticket.xticket.ui.viewModel.UserViewModel
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogAnimation
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val userViewModel : UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
        initObservables()
    }

    private fun initObservables() {
        userViewModel.successfulRegister.observe(this) { it ->
            if (it == 200) {
                AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.SUCCESS)
                    .setTitle("Atencion")
                    .setMessage("Usuario registrado correctamente")
                    .setCancelable(true)
                    .setGravity(Gravity.CENTER)
                    .setAnimation(DialogAnimation.SHRINK)
                    .show()
            }
        }

        userViewModel.showLoader.observe(this) { showLoader ->
            binding.progressBar.isVisible = showLoader
            binding.overlayView.isVisible = showLoader
        }
    }
    private fun initListeners() {
        binding.btnSignUp.setOnClickListener{
            //(validateForm()){
                val name = binding.etName.text.toString()
                val surnames = binding.etLastName.text.toString()
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                val type = if(binding.switchEvent.isChecked) "eventPlanner" else "assistant"

                val user: User = User (
                    0,
                    email,
                    name,
                    type,
                    password,
                    surnames
                )
                    userViewModel.registerUser(user)
           // } else {
              //  Toast.makeText(this, "Error al registrar la fecha de venta", Toast.LENGTH_SHORT).show()
                }
            }
    }


