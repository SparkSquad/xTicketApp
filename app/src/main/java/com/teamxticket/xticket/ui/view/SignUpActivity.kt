package com.teamxticket.xticket.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import com.teamxticket.xticket.data.model.User
import com.teamxticket.xticket.databinding.ActivitySignupBinding
import com.teamxticket.xticket.ui.viewModel.UserViewModel

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

    private fun initObservables() {}
    private fun initListeners() {
        binding.btnSignUp.setOnClickListener{
            if (validateForm()){
                val name = binding.etName.text.toString()
                val surnames = binding.etLastName.text.toString()
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                val type = if(binding.switchEvent.isChecked) "eventPlanner" else "assistant"

                val user = User (
                    0,
                    email,
                    name,
                    type,
                    password,
                    surnames
                )
                    userViewModel.registerUser(user)
           } else {
            Toast.makeText(this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show()
                }
        }
            }

    private fun isValidEmail(email: CharSequence): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun validateForm(): Boolean {
        if (binding.etName.text.isEmpty()) {
            Toast.makeText(this, "Debe ingresar un nombre", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.etLastName.text.isEmpty()){
            Toast.makeText(this, "Debe ingresar un apellido", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.etEmail.text.isEmpty()){
            Toast.makeText(this, "Debe ingresar un correo electronico", Toast.LENGTH_SHORT).show()
            return false
        }

        if(!isValidEmail(binding.etEmail.text)){
            Toast.makeText(this, "Debe ingresar un correo electronico valido", Toast.LENGTH_SHORT).show()
        }
        if (binding.etPassword.text.isEmpty()) {
            Toast.makeText(this, "Debe ingresar una contraseña", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.etCPassword.text.isEmpty()) {
            Toast.makeText(this, "Debe confirmar la contraseña", Toast.LENGTH_SHORT).show()
            return false
        }

        if(binding.etPassword.text.toString() != binding.etCPassword.text.toString()){
            Toast.makeText(this,"No coinciden las contraseñas ingresadas", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etPassword.text.length < 8) {
            Toast.makeText(this, "La contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.etPassword.text.contains(" ")){
            Toast.makeText(this, "La contraseña no puede contener espacios", Toast.LENGTH_SHORT).show()
            return false
        }
            return true
        }

    }




