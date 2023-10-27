package com.teamxticket.xticket.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.teamxticket.xticket.R

class LoginActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSignInFr: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnSignInFr = findViewById(R.id.btnSignInFr)

        btnSignInFr.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (isValidEmail(email) && isValidPassword(password)) {
                //función de login aquí
                Toast.makeText(applicationContext, "Has ingresado de manera exitosa!", Toast.LENGTH_SHORT)
            } else {
                Toast.makeText(applicationContext, "El email o contraseña ingresados no son correctos, por favor intente de nuevo.", Toast.LENGTH_LONG)
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }
}