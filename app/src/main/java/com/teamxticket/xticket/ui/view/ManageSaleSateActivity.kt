package com.teamxticket.xticket.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamxticket.xticket.data.model.SaleDate
import com.teamxticket.xticket.databinding.ActivityManageSaleSateBinding
import com.teamxticket.xticket.ui.view.adapter.SaleDateAdapter
import com.teamxticket.xticket.ui.viewModel.SaleDateViewModel

class ManageSaleSateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityManageSaleSateBinding
    private val saleDateViewModel : SaleDateViewModel by viewModels()
    private var eventId: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityManageSaleSateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvSalesDates.layoutManager = LinearLayoutManager(this)

        //eventId = intent.getIntExtra("eventId", 0)
        saleDateViewModel.loadSaleDates(eventId)
        initListeners()
    }

    private fun initListeners() {
        binding.btnAddSaleDate.setOnClickListener {
    val intent = Intent(this, RegisterSaleDateActivity::class.java)
                intent.putExtra("eventId", eventId)
                startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        saleDateViewModel.loadSaleDates(eventId)
        initObservables()
    }

    private fun initObservables() {
        saleDateViewModel.saleDateModel.observe(this) { saleDateList ->
            val dateList : List<SaleDate> = saleDateList ?: emptyList()
            val adapter = SaleDateAdapter(dateList) { saleDate -> onItemSelected(saleDate) }
            binding.rvSalesDates.adapter = adapter
        }
        saleDateViewModel.showLoader.observe(this) { visible ->
            binding.progressBar.isVisible = visible
            binding.overlayView.isVisible = visible
        }
    }

    private fun onItemSelected(saleDate: SaleDate) {
        val idString = saleDate.sale_date_id.toString()
        Toast.makeText(this, idString, Toast.LENGTH_SHORT).show()
    }

}