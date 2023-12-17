package com.example.exercise04

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.exercise04.databinding.FragmentPhotoSliderBinding

class PhotoSliderFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var photoUrls: MutableList<ImageItem>
    private lateinit var photoRepo: ImageRepository
    private lateinit var binding: FragmentPhotoSliderBinding
    private lateinit var pickButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoRepo = ImageRepository.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoSliderBinding.inflate(inflater, container, false)
        pickButton = binding.button4
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = view.findViewById(R.id.viewPager)
        photoUrls = photoRepo.getSharedList()!!

        val adapter = PhotoSliderAdapter(photoUrls)
        viewPager.adapter = adapter

        val data = requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE)

        pickButton.setOnClickListener {
            val editor = data.edit()

            editor.putString("image2", arguments?.getString("path"))
            editor.apply()

            requireActivity().onBackPressed()
        }

        // Retrieve the photo path from the arguments
        val photoPath = arguments?.getString("path")
        // Find the position of the photo in the list
        val position = photoUrls.indexOfFirst { it.uripath == photoPath }
        // Set the current item of the ViewPager to this position
        if (position != -1) {
            viewPager.setCurrentItem(position, false)
        }
    }

}