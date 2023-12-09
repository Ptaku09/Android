package com.example.exercise04

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.exercise04.databinding.DetailsFragmentBinding

class DetailsFragment : Fragment() {
    private lateinit var binding: DetailsFragmentBinding
    private var id: Int = 0
    private lateinit var detailPlayerName: TextView
    private lateinit var detailPlayerSurname: TextView
    private lateinit var detailPlayerDesc: TextView
    private lateinit var detailPlayerNumber: TextView
    private lateinit var detailPlayerPosition: TextView
    private lateinit var detailIsGood: CheckBox
    private lateinit var detailPlayerImg: ImageView
    private lateinit var backButton: Button
    private lateinit var editButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailsFragmentBinding.inflate(inflater, container, false)
        detailPlayerName = binding.detailPlayerName
        detailPlayerSurname = binding.detailPlayerSurname
        detailPlayerDesc = binding.detailPlayerDesc
        detailPlayerNumber = binding.detailPlayerNumber
        detailPlayerPosition = binding.detailPlayerPosition
        detailPlayerImg = binding.detailPlayerImg
        detailIsGood = binding.detailPlayerIsGood
        backButton = binding.detailBackButton
        editButton = binding.editButton

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backButton.setOnClickListener { requireActivity().onBackPressed() }
        parentFragmentManager.setFragmentResultListener(
            "msgtochild",
            viewLifecycleOwner
        ) { _, bundle ->
            run {
                id = bundle.getInt("id")
                detailPlayerName.text = bundle.getString("name")
                detailPlayerSurname.text = bundle.getString("surname")
                detailPlayerDesc.text = bundle.getString("desc")
                detailPlayerNumber.text = bundle.getString("number").toString()
                detailPlayerPosition.text = bundle.getString("position")
                detailIsGood.isChecked = bundle.getBoolean("good")

                when (bundle.getString("position")) {
                    "Striker" -> detailPlayerImg.setImageResource(R.drawable.striker)
                    "Middle fielder" -> detailPlayerImg.setImageResource(R.drawable.middle)
                    "Defender" -> detailPlayerImg.setImageResource(R.drawable.defender)
                    "Goalkeeper" -> detailPlayerImg.setImageResource(R.drawable.goalkeeper)
                    else -> detailPlayerImg.setImageResource(R.drawable.striker)
                }
            }
        }

        editButton.setOnClickListener {
            val bundle = Bundle()

            bundle.putInt("id", id)
            bundle.putString("name", detailPlayerName.text.toString())
            bundle.putString("surname", detailPlayerSurname.text.toString())
            bundle.putString("desc", detailPlayerDesc.text.toString())
            bundle.putString("number", detailPlayerNumber.text.toString())
            bundle.putString("position", detailPlayerPosition.text.toString())
            bundle.putBoolean("good", detailIsGood.isChecked)
            bundle.putBoolean("toEdit", true)

            findNavController().navigate(R.id.action_details_frag_to_add_item_frag, bundle)
        }
    }

    companion object
}