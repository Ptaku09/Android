package com.example.exercise04

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.exercise04.databinding.FragmentMainBinding
import java.io.File


class MainFragment : Fragment() {
    private lateinit var invitation: TextView
    private lateinit var authorName: TextView
    private lateinit var authorSurname: TextView
    private lateinit var mainImage: ImageView

    private lateinit var binding: FragmentMainBinding

    private var basePhotoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    private val externalStorageDirectory = Environment.getExternalStorageDirectory()
    private val externalStoragePublicDirectory =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
    private var externalFilesDirPictures: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        invitation = binding.mainText
        authorName = binding.authorName
        authorSurname = binding.authorSurname
        mainImage = binding.mainImage

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun setData() {
        val data: SharedPreferences =
            requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE)
        invitation.text = "Welcome to the Exercise 06!"
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

        val image = data.getString("image2", "none")

        if (image != "none") {
            mainImage.setImageURI(Uri.parse(image))
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        setStyle()
        setImage()

        externalFilesDirPictures =
            requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        binding.basePhotoUriTextView.text =
            basePhotoUri.scheme + ":/" + MediaStore.Images.Media.EXTERNAL_CONTENT_URI.path

        binding.externalStorageTextView.text = externalStorageDirectory.absolutePath

        binding.externalStoragePublicDirectoryTextView.text =
            externalStoragePublicDirectory.absolutePath

        binding.externalStoragePublicDirectoryPicturesTextView.text =
            externalFilesDirPictures?.absolutePath ?: "nothing"

        externalFilesDirPictures?.let {
            val uri = FileProvider.getUriForFile(
                requireContext(),
                "com.example.exercise04.provider", it
            )

            binding.other.text = uri.scheme + ":/" + uri.path
        }
    }

    override fun onResume() {
        super.onResume()
        setData()
        setStyle()
        setImage()
    }

    companion object
}