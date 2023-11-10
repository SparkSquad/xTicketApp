package com.teamxticket.xticket.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.teamxticket.xticket.R
import androidx.fragment.app.Fragment
import com.teamxticket.xticket.databinding.ActivityAssistantMenuBinding

class AssistantMenuActivity : AppCompatActivity() {

    private val exploreTickets = TicketListFragment()
    private var activeFragment: Fragment = exploreTickets

    private lateinit var binding: ActivityAssistantMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssistantMenuBinding.inflate(layoutInflater)
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
        supportFragmentManager.beginTransaction().replace(R.id.flAssistantMenu, activeFragment).show(fragment).commit()
        activeFragment = fragment
    }
}
