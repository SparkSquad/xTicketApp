package com.teamxticket.xticket.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
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
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogAnimation
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import com.thecode.aestheticdialogs.OnDialogClickListener

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


            if (email.isNotEmpty() && password.isNotEmpty()) {
                val user : User = User(0,email, "", "", password, "")
                userViewModel.searchUser(user)
            } else {
                Toast.makeText(this, "El email o contraseña ingresados no son correctos, por favor intente de nuevo.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initObservables () {
         userViewModel.receivedUser.observe(this) {
             if (it.token?.isNotEmpty() == true) {
                 Toast.makeText(this, "Has ingresado de manera exitosa!", Toast.LENGTH_SHORT).show()
                 if (it.user?.email?.isEmpty() == false) {
                     if(it.user?.type == "assistant" || it.user?.type == "admin") {
                         Intent (this, AssistantMenuActivity::class.java).apply {
                             startActivity(this)
                         }
                     } else if (it.user?.type == "eventPlanner") {
                         Intent (this, EventPlannerMenuActivity::class.java).apply {
                             startActivity(this)
                         }
                     }
                 }
             } else {
                 AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.ERROR)
                     .setTitle("No encontrado")
                     .setMessage("El correo o la contraseña no son correctos, por favor verifique e intente de nuevo. ")
                     .setCancelable(true)
                     .setGravity(Gravity.CENTER)
                     .setAnimation(DialogAnimation.SHRINK).show()
             }
         }
    }
}