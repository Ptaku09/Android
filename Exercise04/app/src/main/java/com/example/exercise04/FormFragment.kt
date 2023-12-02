package com.example.exercise04

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.exercise04.databinding.FormFragmentBinding

class FormFragment : Fragment() {
    private lateinit var _binding: FormFragmentBinding
    private lateinit var invitation: EditText
    private lateinit var authorName: EditText
    private lateinit var authorSurname: EditText
    private lateinit var saveButton: Button
    private lateinit var data: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FormFragmentBinding.inflate(inflater, container, false)
        invitation = _binding.invitationEdit
        authorSurname = _binding.authorSurnameEdit
        authorName = _binding.authorNameEdit
        saveButton = _binding.applyButton1

        return _binding.root
    }

    private fun setData() {
        val invitationVar = invitation.text.toString()
        val authorNameVar = authorName.text.toString()
        val authorSurnameVar = authorSurname.text.toString()
        val editor = data.edit()

        editor.putString("invitation", invitationVar)
        editor.putString("authorName", authorNameVar)
        editor.putString("authorSurname", authorSurnameVar)
        editor.apply()

        requireActivity().onBackPressed()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        data = requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE)
        invitation.setText(data.getString("invitation", "Welcome!"))
        authorName.setText(data.getString("authorName", ""))
        authorSurname.setText(data.getString("authorSurname", ""))

        saveButton.setOnClickListener { _ ->
            setData()
        }
    }

    companion object
}