package com.teamxticket.xticket.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.teamxticket.xticket.R
import com.teamxticket.xticket.databinding.ActivityLandingBinding

class LandingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLandingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()

        val button = findViewById<Button>(R.id.btnSignIn)
        button.setOnClickListener {
            val intent = Intent(this, LandingActivity::class.java)
            startActivity(intent)
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
}