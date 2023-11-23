package com.teamxticket.xticket.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.teamxticket.xticket.R
import com.teamxticket.xticket.databinding.ActivityPurchaseDetailBinding
import com.teamxticket.xticket.databinding.ActivityTicketDetailsQrBinding
import com.teamxticket.xticket.ui.viewModel.TicketsViewModel
import kotlinx.coroutines.launch

class TicketDetailsQr : AppCompatActivity() {

    private lateinit var binding: ActivityTicketDetailsQrBinding
    private val ticketsViewModel: TicketsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketDetailsQrBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}