package com.example.exercise04

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.exercise04.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var invitation: TextView
    private lateinit var authorName: TextView
    private lateinit var authorSurname: TextView
    private lateinit var mainImage: ImageView

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
        mainImage = view.mainImage

        return view.root
    }

    @SuppressLint("SetTextI18n")
    private fun setData() {
        val data: SharedPreferences =
            requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE)
        invitation.text = "Welcome to the Exercise 05!"
        authorName.text = "Mateusz"
        authorSurname.text = "Ptak"
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

    private fun setImage() {
        val data: SharedPreferences =
            requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE)

        when (data.getString("image", "fcb")) {
            "fcb" -> {
                mainImage.setImageResource(R.drawable.fcb)
            }

            "fcbkoszulka" -> {
                mainImage.setImageResource(R.drawable.fcbkoszulka)
            }

            "campnou" -> {
                mainImage.setImageResource(R.drawable.campnou)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        setStyle()
        setImage()
    }

    override fun onResume() {
        super.onResume()
        setData()
        setStyle()
        setImage()
    }

    companion object
}