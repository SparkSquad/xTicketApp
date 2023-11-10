package com.teamxticket.xticket.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamxticket.xticket.data.model.SaleDate
import com.teamxticket.xticket.databinding.ActivityManageSaleDateBinding
import com.teamxticket.xticket.ui.view.adapter.SaleDateAdapter
import com.teamxticket.xticket.ui.viewModel.SaleDateViewModel
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogAnimation
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType

class ManageSaleDateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityManageSaleDateBinding
    private val saleDateViewModel : SaleDateViewModel by viewModels()
    private var eventId: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageSaleDateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvSalesDates.layoutManager = LinearLayoutManager(this)

        eventId = intent.getIntExtra("eventId", 1)
        saleDateViewModel.loadSaleDates(eventId)
        initObservables()
        initListeners()
    }

    override fun onResume() {
        super.onResume()
        saleDateViewModel.loadSaleDates(eventId)
    }

    private fun initListeners() {
        binding.btnAddSaleDate.setOnClickListener {
            val intent = Intent(this, RegisterSaleDateActivity::class.java)
            intent.putExtra("eventId", eventId)
            startActivity(intent)
        }
    }

    private fun initObservables() {
        saleDateViewModel.saleDateModel.observe(this) { saleDateList ->
            val dateList : List<SaleDate> = saleDateList ?: emptyList()
            val adapter = SaleDateAdapter(dateList) { saleDate -> onItemSelected(saleDate) }
            binding.rvSalesDates.adapter = adapter
            if(saleDateList.isNullOrEmpty()) {
                AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.ERROR)
                    .setTitle("Atencion")
                    .setMessage("No se encontraron datos para mostrar")
                    .setCancelable(true)
                    .setGravity(Gravity.CENTER)
                    .setAnimation(DialogAnimation.SHRINK)
                    .show()
            }
        }
        saleDateViewModel.showLoader.observe(this) { visible ->
            binding.progressBar.isVisible = visible
            binding.overlayView.isVisible = visible
        }

        saleDateViewModel.errorCode.observe(this) { errorCode ->
            AestheticDialog.Builder(this, DialogStyle.TOASTER, DialogType.ERROR)
                .setTitle("Atencion")
                .setMessage(errorCode)
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .setAnimation(DialogAnimation.SHRINK)
                .show()
        }
    }

    private fun onItemSelected(saleDate: SaleDate) {
        Intent(this, UpdateDeleteSaleDateActivity::class.java).apply {
            putExtra("saleDate", saleDate)
            startActivity(this)
        }
    }
}