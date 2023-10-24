package com.teamxticket.xticket.ui.view

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
    private val saleDateviewModel : SaleDateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityManageSaleSateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dateList : List<SaleDate> = createDates()
        val adapter2 = SaleDateAdapter(dateList) { saleDate -> onItemSelected(saleDate) }


        binding.rvSalesDates.layoutManager = LinearLayoutManager(this)
        binding.rvSalesDates.adapter = adapter2

        var eventId = intent.getIntExtra("eventId", 0)
        var event = 1

    }

    private fun onItemSelected(saleDate: SaleDate) {
        Toast.makeText(this, saleDate.eventDate, Toast.LENGTH_SHORT).show()
    }

    private fun initObservables() {
        saleDateviewModel.saleDateModel.observe(this) { saleDateList ->
            

        }

        saleDateviewModel.showLoader.observe(this) {
            binding.progressBar.isVisible = it
        }
    }

    private fun createDates(): List<SaleDate> {
        return listOf(
            SaleDate("Octubre 15", 100, 1600.22, 4, 0, "12:00", "14:00"),
            SaleDate("Octubre 15", 100, 1600.22, 4, 0, "12:00", "14:00"),
            SaleDate("Octubre 15", 100, 1600.22, 4, 0, "12:00", "14:00"),
            SaleDate("Octubre 15", 100, 1600.22, 4, 0, "12:00", "14:00"))
    }
}