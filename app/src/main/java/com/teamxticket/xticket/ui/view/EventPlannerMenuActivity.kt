package com.teamxticket.xticket.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.teamxticket.xticket.R
import com.teamxticket.xticket.databinding.ActivityEventPlannerMenuBinding

class EventPlannerMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventPlannerMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventPlannerMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(ManageEventFragment())
        initListener()
    }

    private fun initListener() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.myEvents -> {
                    replaceFragment(ManageEventFragment())
                    true
                }
                R.id.eProfile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager
        val fragmentTransaction = transaction.beginTransaction()
        fragmentTransaction.replace(R.id.flEventPlannerMenu, fragment)
        fragmentTransaction.commit()
    }
}