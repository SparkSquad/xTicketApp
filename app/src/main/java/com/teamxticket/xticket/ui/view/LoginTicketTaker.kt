package com.teamxticket.xticket.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.activity.viewModels
import com.teamxticket.xticket.R
import com.teamxticket.xticket.data.model.User
import com.teamxticket.xticket.databinding.ActivityLoginBinding
import com.teamxticket.xticket.databinding.ActivityLoginTicketTakerBinding
import com.teamxticket.xticket.ui.viewModel.EventViewModel
import com.teamxticket.xticket.ui.viewModel.UserViewModel
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogAnimation
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType


class LoginTicketTaker : AppCompatActivity() {

    private lateinit var binding : ActivityLoginTicketTakerBinding
    private val eventViewModel : EventViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginTicketTakerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObservables()
        initListeners()
    }

    private fun initListeners() {
        binding.btnSignInTicketTaker.setOnClickListener {
            val codeTT = binding.etCodeEvent.text.toString()

            if (codeTT.isNotEmpty()) {
                eventViewModel.searchTicketTaker(codeTT)
            } else {
                AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.ERROR)
                    .setTitle(getString(R.string.emptyField))
                    .setMessage(getString(R.string.invalidTicketTakerData))
                    .setCancelable(true)
                    .setGravity(Gravity.CENTER)
                    .setAnimation(DialogAnimation.SHRINK)
                    .show()
            }
        }
    }

    private fun initObservables () {
            eventViewModel.receivedTicketTakerCode.observe(this) { ticketTakerCode ->
                if (ticketTakerCode?.isNotEmpty() == true) {
                    val intent = Intent(this, ScanQrTicket::class.java)
                    startActivity(intent)
                } else {
                    AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.ERROR)
                        .setTitle(getString(R.string.notFound))
                        .setMessage(getString(R.string.invalidTicketTakerData))
                        .setCancelable(true)
                        .setGravity(Gravity.CENTER)
                        .setAnimation(DialogAnimation.SHRINK).show()
                }


        }
    }
}