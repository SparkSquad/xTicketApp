package com.teamxticket.xticket.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.teamxticket.xticket.R
import com.teamxticket.xticket.databinding.ActivityEventPlannerDataBinding
import com.teamxticket.xticket.ui.viewModel.EventPlannerViewModel

class EventPlannerDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventPlannerDataBinding
    private val eventPlannerViewModel : EventPlannerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventPlannerDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObservables()
    }

    private fun initObservables() {
        eventPlannerViewModel.eventPlannerData.observe(this) { eventPlannerData ->
            if (eventPlannerData != null) {
                binding.tvName.text = eventPlannerData.name
                binding.tvEmail.text = eventPlannerData.email
                binding.tvPhoneNumber.text = eventPlannerData.phone
            }
        }
    }


}