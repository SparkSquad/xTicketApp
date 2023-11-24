package com.teamxticket.xticket.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.teamxticket.xticket.databinding.ActivityRecoverPasswordBinding
import com.teamxticket.xticket.ui.viewModel.UserViewModel

class RecoverPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecoverPasswordBinding
    private val recoverPasswordViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecoverPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObservables()
        initListeners()
    }

    private fun initObservables() {
        recoverPasswordViewModel.showLoader.observe(this) { visible ->
            binding.progressBar.isVisible = visible
            binding.overlayView.isVisible = visible
        }

        recoverPasswordViewModel.errorCode.observe(this) { errorCode ->
            Toast.makeText(this, errorCode, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initListeners() {
        binding.btnSendOTUCode.setOnClickListener() {
            val email = binding.etEmail.text.toString()
            recoverPasswordViewModel.recoverPassword(email)
            Intent(this, SendOneTimeUseCodeActivity::class.java).apply {
                startActivity(this)
            }
        }

    }

}