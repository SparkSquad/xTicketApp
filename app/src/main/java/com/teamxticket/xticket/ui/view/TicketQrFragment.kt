package com.teamxticket.xticket.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.teamxticket.xticket.data.model.TicketData
import com.teamxticket.xticket.databinding.FragmentTicketQrBinding

class TicketQrFragment(ticketData: TicketData) : DialogFragment() {

    private lateinit var binding: FragmentTicketQrBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTicketQrBinding.inflate(inflater, container, false)
        return binding.root



    }

}