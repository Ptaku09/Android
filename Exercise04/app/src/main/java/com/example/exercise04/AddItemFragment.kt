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
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

        addPlayerName.setText(arguments?.getString("name") ?: "", TextView.BufferType.EDITABLE)
        addPlayerSurname.setText(
            arguments?.getString("surname") ?: "",
            TextView.BufferType.EDITABLE
        )
        addPlayerDesc.setText(arguments?.getString("desc") ?: "", TextView.BufferType.EDITABLE)
        addPlayerNumber.progress = arguments?.getString("number")?.toInt() ?: 0
        addIsGood.isChecked = arguments?.getBoolean("good") ?: false

        when (arguments?.getString("position")) {
            "Striker" -> addPlayerPosition.check(binding.addPositionStriker.id)
            "Middle fielder" -> addPlayerPosition.check(binding.addPositionMiddleFielder.id)
            "Defender" -> addPlayerPosition.check(binding.addPositionDefender.id)
            "Goalkeeper" -> addPlayerPosition.check(binding.addPositionGoalkeeper.id)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var playerPosition = arguments?.getString("position") ?: "Striker"

        addPlayerPosition.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.addPositionStriker.id -> playerPosition = "Striker"
                binding.addPositionMiddleFielder.id -> playerPosition = "Middle fielder"
                binding.addPositionDefender.id -> playerPosition = "Defender"
                binding.addPositionGoalkeeper.id -> playerPosition = "Goalkeeper"
            }
        }

        cancelButton.setOnClickListener {
            if (arguments?.getBoolean("toEdit") == true) {
                parentFragmentManager.setFragmentResult(
                    "msgtochild", bundleOf(
                        "id" to arguments?.getInt("id")!!,
                        "name" to arguments?.getString("name"),
                        "surname" to arguments?.getString("surname"),
                        "desc" to arguments?.getString("desc"),
                        "number" to arguments?.getString("number"),
                        "position" to arguments?.getString("position"),
                        "good" to arguments?.getBoolean("good")
                    )
                )
            } else {
                parentFragmentManager.setFragmentResult(
                    "addNewItem", bundleOf(
                        "toAdd" to false
                    )
                )
            }

            requireActivity().onBackPressed()
        }

        saveButton.setOnClickListener {
            val playerName: String = addPlayerName.text.toString()
            val playerSurname: String = addPlayerSurname.text.toString()
            val playerDesc: String = addPlayerDesc.text.toString()
            val playerNumber: String =
                if (addPlayerNumber.progress == 0) "1" else if (addPlayerNumber.progress == 100) "99" else (addPlayerNumber.progress + 1).toString()

            if (arguments?.getBoolean("toEdit") == true) {
                PlayerRepository.getInstance(requireContext())?.addPlayer(
                    DBPlayer(
                        arguments?.getInt("id")!!,
                        playerName,
                        playerSurname,
                        playerDesc,
                        playerNumber,
                        playerPosition,
                        addIsGood.isChecked
                    )
                )
            } else {
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
            }

            findNavController().navigate(R.id.action_add_item_frag_to_list_frag)
        }
    }

    companion object
}