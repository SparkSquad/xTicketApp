package com.teamxticket.xticket.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.teamxticket.xticket.R
import com.teamxticket.xticket.databinding.ActivityEventPlannerMenuBinding

class EventPlannerMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventPlannerMenuBinding
    private val exploreEvents = ManageEventFragment()
    private var activeFragment: Fragment = exploreEvents
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventPlannerMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
    }

    private fun initListener() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.myEvents -> {
                    replaceFragment(exploreEvents)
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.flEventPlannerMenu, activeFragment).show(fragment).commit()
        activeFragment = fragment
    }
}