package com.example.exercise04

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.exercise04.databinding.TshirtFragmentBinding


class TshirtFragment : Fragment() {
    private lateinit var binding: TshirtFragmentBinding
    private lateinit var mainImage: ImageView
    private lateinit var pickButton: Button
    private lateinit var data: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TshirtFragmentBinding.inflate(inflater, container, false)
        mainImage = binding.imageView2
        pickButton = binding.button2

        return binding.root
    }

    private fun setData() {
        val editor = data.edit()

        editor.putString("image", "fcbkoszulka")
        editor.apply()

        requireActivity().onBackPressed()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        data = requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE)

        pickButton.setOnClickListener {
            setData()
        }
    }

    companion object
}