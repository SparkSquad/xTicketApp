package com.teamxticket.xticket.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.teamxticket.xticket.R
import androidx.fragment.app.Fragment
import com.teamxticket.xticket.databinding.ActivityAssistantMenuBinding

class AssistantMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAssistantMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssistantMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        replaceFragment(ExploreEventsFragment())
    }

    private fun initListener() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.aExplore -> {
                    replaceFragment(ExploreEventsFragment())
                    true
                }
                R.id.aTicket -> {
                    replaceFragment(TicketListFragment())
                    true
                }
                R.id.aProfile -> {
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
        fragmentTransaction.replace(R.id.flAssistantMenu, fragment)
        fragmentTransaction.commit()
    }
}
