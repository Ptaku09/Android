package com.example.exercise04

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.exercise04.databinding.FragmentSwipeBinding
import com.google.android.material.tabs.TabLayoutMediator

class SwipeFragment : Fragment() {

    private var _binding: FragmentSwipeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSwipeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PagerAdapter(requireActivity())
        val viewPager = binding.viewPager
        viewPager.adapter = adapter

        val tabLayoutMediator = TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "FCB"
                1 -> tab.text = "Badge"
                2 -> tab.text = "Stadium"
            }
        }

        tabLayoutMediator.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object
}