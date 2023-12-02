package com.example.exercise04

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.exercise04.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var invitation: TextView
    private lateinit var authorName: TextView
    private lateinit var authorSurname: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentMainBinding.inflate(inflater, container, false)
        invitation = view.mainText
        authorName = view.authorName
        authorSurname = view.authorSurname

        return view.root
    }

    private fun setData() {
        val data: SharedPreferences =
            requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE)
        invitation.text = data.getString("invitation", "Welcome to the Exercise 04!")
        authorName.text = data.getString("authorName", "Mateusz")
        authorSurname.text = data.getString("authorSurname", "Ptak")
    }

    private fun setStyle() {
        val data: SharedPreferences =
            requireActivity().getSharedPreferences("styles", Context.MODE_PRIVATE)

        when (data.getString("font_size", "medium")) {
            "small" -> {
                invitation.textSize = 12f
                authorName.textSize = 12f
                authorSurname.textSize = 12f
            }

            "medium" -> {
                invitation.textSize = 18f
                authorName.textSize = 18f
                authorSurname.textSize = 18f
            }

            "large" -> {
                invitation.textSize = 24f
                authorName.textSize = 24f
                authorSurname.textSize = 24f
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        setStyle()
    }

    override fun onResume() {
        super.onResume()
        setData()
        setStyle()
    }

    companion object
}