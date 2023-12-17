package com.example.exercise04

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise04.databinding.FragmentPhotoListBinding

class PhotoListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PhotoListAdapter
    private var _binding: FragmentPhotoListBinding? = null
    private lateinit var photoRepo: ImageRepository


    private var isSharedMemory = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isSharedMemory = it.getBoolean("isSharedMemory")
        }
        photoRepo = ImageRepository.getInstance(requireContext())
        adapter = PhotoListAdapter(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoListBinding.inflate(inflater, container, false)
        recyclerView = _binding!!.listView
        recyclerView.layoutManager =
            androidx.recyclerview.widget.GridLayoutManager(requireContext(), 2)

        recyclerView.adapter = adapter
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataRepo = ImageRepository.getInstance(requireContext())
        val photoList = dataRepo.getSharedList()

        if (photoList == null) {
            Toast.makeText(requireContext(), "Invalid Data", Toast.LENGTH_LONG).show()
            requireActivity().onBackPressed()
        } else {
            adapter.submitList(photoList)
            recyclerView.adapter = adapter
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_photo, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_photo_add -> {
                findNavController().navigate(R.id.takePhotoFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}