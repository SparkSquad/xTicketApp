package com.teamxticket.xticket.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamxticket.xticket.R
import com.teamxticket.xticket.databinding.FragmentExploreEventsBinding

class ExploreEventsFragment : Fragment() {

    private var _binding: FragmentExploreEventsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExploreEventsBinding.inflate(inflater, container, false)
        val rootView = binding.root

        //TODO aqui va el codigo onCreate de un activity normal
        return rootView
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}