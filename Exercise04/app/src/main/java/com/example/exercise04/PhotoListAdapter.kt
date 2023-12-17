package com.example.exercise04

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Size
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise04.databinding.ImageItemBinding
import java.io.FileNotFoundException
import java.io.InputStream


class PhotoListAdapter(private var appContext: Context) :
    ListAdapter<ImageItem, PhotoListAdapter.MyViewHolder>(PhotoDiffCallback()) {

    inner class MyViewHolder(viewBinding: ImageItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        val img: ImageView = viewBinding.itemImg
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val viewBinding =
            ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val photo = getItem(position)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            photo.curi?.let {
                holder.img.setImageBitmap(
                    appContext.contentResolver.loadThumbnail(
                        it,
                        Size(150, 150),
                        null
                    )
                )
            }
        } else {
            holder.img.setImageBitmap(getBitmapFromUri(appContext, photo.curi))
        }

        holder.img.setOnClickListener {
            findNavController(holder.itemView).navigate(
                R.id.action_photoListFragment_to_photoSlider,
                Bundle().apply {
                    putString("path", photo.uripath)
                })
        }

    }

    private fun getBitmapFromUri(mContext: Context, uri: Uri?): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val imageStream: InputStream
            try {
                imageStream = uri?.let {
                    mContext.contentResolver.openInputStream(it)
                }!!
                bitmap = BitmapFactory.decodeStream(imageStream)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }

    private class PhotoDiffCallback : DiffUtil.ItemCallback<ImageItem>() {
        override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem.path == newItem.path
        }

        override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem.path == newItem.path
        }
    }
}


