package com.teamxticket.xticket.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.teamxticket.xticket.R
import com.teamxticket.xticket.data.UserRepository
import com.teamxticket.xticket.data.model.User
import com.teamxticket.xticket.databinding.ActivityManageEventPlannerBinding
import com.teamxticket.xticket.ui.view.adapter.EventPlannerAdapter
import com.teamxticket.xticket.ui.viewModel.UserViewModel

class ManageEventPlannerActivity : AppCompatActivity() {

    private lateinit var adapter: EventPlannerAdapter
    private lateinit var binding: ActivityManageEventPlannerBinding
    private val eventPlannerViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageEventPlannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        eventPlannerViewModel.searchEventPlanners("", 100, 1)
        initObservables()
        initListeners()
    }

    private fun initObservables() {
        eventPlannerViewModel.eventPlannerSearchResult.observe(this) { eventPlannerList ->
            val eventPlannerData : MutableList<User> = eventPlannerList?.toMutableList() ?: arrayListOf()
            adapter = EventPlannerAdapter(eventPlannerData) { eventPlannerData -> onItemSelected(eventPlannerData) }
            binding.rvEventPlanner.adapter = adapter
        }
    }

    private fun initListeners() {
        binding.etSearchEventPlanner.addTextChangedListener(object : RecyclerView.OnScrollListener(),
            TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                eventPlannerViewModel.searchEventPlanners(s.toString(), 100, 1)
            }
        })
    }

    private fun onItemSelected(eventPlannerData: User) {
        if (eventPlannerData.disabled) {
            AlertDialog.Builder(this)
                .setTitle("¿Desea habilitar al organizador de eventos?")
                .setMessage("El organizador de eventos podrá volver a crear eventos y vender tickets")
                .setCancelable(true)
                .setPositiveButton(getString(R.string.dialog_accept_button)) { dialog, which ->
                    dialog.dismiss()
                    eventPlannerViewModel.updateUser(eventPlannerData.copy(disabled = false))
                }
                .setNegativeButton(getString(R.string.dialog_cancel_button)) { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        } else {
            AlertDialog.Builder(this)
                .setTitle("¿Desea deshabilitar al organizador de eventos?")
                .setMessage("El organizador de eventos no podrá crear eventos ni vender tickets")
                .setCancelable(true)
                .setPositiveButton(getString(R.string.dialog_accept_button)) { dialog, which ->
                    dialog.dismiss()
                    eventPlannerViewModel.updateUser(eventPlannerData.copy(disabled = true))
                }
                .setNegativeButton(getString(R.string.dialog_cancel_button)) { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }
        eventPlannerViewModel.searchEventPlanners(binding.etSearchEventPlanner.text.toString(), 100, 1)
    }

}