package com.example.exercise04

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.example.exercise04.databinding.StylesFragmentBinding


class StylesFragment : Fragment() {
    private lateinit var binding: StylesFragmentBinding
    private lateinit var radioGroup: RadioGroup
    private lateinit var styleSmall: RadioButton
    private lateinit var styleMedium: RadioButton
    private lateinit var styleLarge: RadioButton
    private lateinit var saveButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StylesFragmentBinding.inflate(inflater, container, false)

        radioGroup = binding.stylesRadioGroup
        styleSmall = binding.styleSmall
        styleMedium = binding.styleMedium
        styleLarge = binding.styleLarge

        saveButton = binding.saveButtonStylesTab
        saveButton.setOnClickListener { _ ->
            val data = requireActivity().getSharedPreferences("styles", Context.MODE_PRIVATE)
            val editor = data.edit()
            when (radioGroup.checkedRadioButtonId) {
                styleSmall.id -> editor.putString("font_size", "small")
                styleMedium.id -> editor.putString("font_size", "medium")
                styleLarge.id -> editor.putString("font_size", "large")
            }
            editor.apply()
        }

        return binding.root
    }

    companion object
}