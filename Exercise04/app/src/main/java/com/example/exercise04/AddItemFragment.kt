package com.example.exercise04

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.SeekBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.exercise04.databinding.FragmentAddItemBinding

class AddItemFragment : Fragment() {
    private lateinit var binding: FragmentAddItemBinding
    private lateinit var addPlayerName: EditText
    private lateinit var addPlayerSurname: EditText
    private lateinit var addPlayerDesc: EditText
    private lateinit var addPlayerNumber: SeekBar
    private lateinit var addPlayerPosition: RadioGroup
    private lateinit var addIsGood: CheckBox
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddItemBinding.inflate(inflater, container, false)
        
        addPlayerName = binding.addPlayerName
        addPlayerSurname = binding.addPlayerSurname
        addPlayerDesc = binding.addPlayerDesc
        addPlayerNumber = binding.addPlayerNumberBar
        addPlayerPosition = binding.addPlayerPosition
        addIsGood = binding.addIsGood
        saveButton = binding.addSaveButton
        cancelButton = binding.addCancelButton

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var playerPosition = "Striker"

        addPlayerPosition.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.addPositionStriker.id -> playerPosition = "Striker"
                binding.addPositionMiddleFielder.id -> playerPosition = "Middle Fielder"
                binding.addPositionDefender.id -> playerPosition = "Defender"
                binding.addPositionGoalkeeper.id -> playerPosition = "Goalkeeper"
            }
        }

        cancelButton.setOnClickListener {
            parentFragmentManager.setFragmentResult(
                "addNewItem", bundleOf(
                    "toAdd" to false
                )
            )

            requireActivity().onBackPressed()
        }

        saveButton.setOnClickListener {
            val playerName: String = addPlayerName.text.toString()
            val playerSurname: String = addPlayerSurname.text.toString()
            val playerDesc: String = addPlayerDesc.text.toString()
            val playerNumber: Number =
                if (addPlayerNumber.progress == 0) 1 else if (addPlayerNumber.progress == 100) 99 else addPlayerNumber.progress + 1

            parentFragmentManager.setFragmentResult(
                "addNewItem", bundleOf(
                    "name" to playerName,
                    "surname" to playerSurname,
                    "desc" to playerDesc,
                    "number" to playerNumber,
                    "position" to playerPosition,
                    "good" to addIsGood.isChecked,
                    "toAdd" to true
                )
            )

            requireActivity().onBackPressed()
        }
    }

    companion object
}