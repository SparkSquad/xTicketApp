package com.teamxticket.xticket.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.teamxticket.xticket.R
import com.teamxticket.xticket.databinding.ActivityManageEventPlannerBinding
import com.teamxticket.xticket.ui.view.adapter.EventPlannerAdapter

class ManageEventPlannerActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EventPlannerAdapter
    private lateinit var binding: ActivityManageEventPlannerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageEventPlannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}