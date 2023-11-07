package com.teamxticket.xticket.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.teamxticket.xticket.R
import com.teamxticket.xticket.databinding.ActivityMainBinding
import androidx.fragment.app.Fragment

class AssistantMenuActivity : AppCompatActivity() {

    private val exploreTickets = TicketListFragment()
    private var activeFragment: Fragment = exploreTickets

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ticket -> {
                    replaceFragment(exploreTickets)
                    true
                }
                else -> false
            }
        }


    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, activeFragment).show(fragment).commit()
        activeFragment = fragment
    }
}
