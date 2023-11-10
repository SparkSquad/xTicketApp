package com.teamxticket.xticket.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.teamxticket.xticket.R
import com.teamxticket.xticket.databinding.ActivityAdministratorMenuBinding

class AdministratorMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdministratorMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administrator_menu)
    }
}