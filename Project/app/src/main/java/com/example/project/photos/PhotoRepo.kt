package com.example.project.photos

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.example.project.BuildConfig
import java.io.File


class PhotoRepo {
    private lateinit var uri: Uri

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var INSTANCE: PhotoRepo? = null

        @SuppressLint("StaticFieldLeak")
        private lateinit var ctx: Context
        var sharedStoreList: MutableList<Photo>? = null

        fun getInstance(ctx: Context): PhotoRepo {
            if (INSTANCE == null) {
                INSTANCE = PhotoRepo()
                sharedStoreList = mutableListOf()
                this.ctx = ctx
            }

            return INSTANCE as PhotoRepo
        }
    }

    fun getSharedList(external: Boolean): List<Photo>? {
        return if (external) {
            getExternalImages()
        } else {
            getInternalImages()
        }
    }

    @SuppressLint("Recycle")
    private fun getExternalImages(): MutableList<Photo>? {
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        sharedStoreList?.clear()

        val contentResolver: ContentResolver = ctx.contentResolver
        val cursor = contentResolver.query(uri, null, null, null, null)

        if (cursor == null) {
            throw NullPointerException("Unknown URI: $uri")
        } else if (!cursor.moveToFirst()) {
            println("No photos")
        } else {
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)

            do {
                val thisId = cursor.getLong(idColumn)
                val thisName = cursor.getString(nameColumn)
                val thisContentUri = ContentUris.withAppendedId(uri, thisId)
                val thisUriPath = thisContentUri.toString()

                sharedStoreList?.add(Photo(thisName, thisUriPath, "No path yet", thisContentUri))
            } while (cursor.moveToNext())
        }

        return sharedStoreList
    }

    private fun getInternalImages(): MutableList<Photo>? {
        val dir: File? = ctx.getExternalFilesDir(Environment.DIRECTORY_DCIM)

        dir?.listFiles()
        sharedStoreList?.clear()

        if (dir?.isDirectory == true) {
            val fileList = dir.listFiles()

            if (fileList != null) {
                for (value in fileList) {
                    val fileName = value.name

                    if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") ||
                        fileName.endsWith(".png") || fileName.endsWith(".gif")
                    ) {
                        val tmpUri = FileProvider.getUriForFile(
                            ctx,
                            "${BuildConfig.APPLICATION_ID}.provider", value
                        )
                        sharedStoreList?.add(
                            Photo(
                                fileName, value.toURI().path,
                                value.absolutePath, tmpUri
                            )
                        )
                    }
                }
            }
        }

        return sharedStoreList
    }
}
